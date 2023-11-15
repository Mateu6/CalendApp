package mate.pracainz.calendapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.data.CalendarDataSource
import mate.pracainz.calendapp.ui.layout.CalendarUiState
import java.time.LocalDate

@Composable
fun CalendarHeader(
    calendarUiState: CalendarUiState,
    dataSource: CalendarDataSource,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
    onDateClickListener: (LocalDate) -> Unit,
    onResetClickListener: () -> Unit,
    onMonthChanged: (String) -> Unit
) {
    Row {
        IconButton(onClick = {
            // invoke previous callback when its button clicked
            val startDate = calendarUiState.visibleDates.first().date.minusMonths(1)
            onPrevClickListener(startDate)
            onMonthChanged(startDate.month.toString())
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Previous month"
            )
        }
        Text(
            text = calendarUiState.selectedDate.date.month.toString(),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.bodySmall,
            onTextLayout = {
                // Trigger a recomposition when the text layout is calculated
                onDateClickListener(calendarUiState.selectedDate.date)
            }
        )
        IconButton(onClick = {
            val endDate = calendarUiState.visibleDates.last().date.plusMonths(1)
            onNextClickListener(endDate)
            onMonthChanged(endDate.month.toString())
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Next month"
            )
        }
        IconButton(onClick = {
            onResetClickListener()
            onMonthChanged(dataSource.today.month.toString())
        }) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reset to current date"
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(items = calendarUiState.visibleDates.chunked(7)) { weekDates ->
            LazyRow(
                modifier = Modifier
                    .fillParentMaxWidth()
            ){
                items(items = weekDates) { date ->
                    CalendarItem(
                        date = date,
                        onDateClickListener,
                        isCurrentMonth = date.date.month.equals(calendarUiState.selectedDate.date.month)
                    )
                }
            }
        }
    }
}

