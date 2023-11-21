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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.data.CalendarDataSource
import mate.pracainz.calendapp.ui.layout.CalendarUiState
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarHeader(
    calendarUiState: CalendarUiState,
    dataSource: CalendarDataSource,
    onPrevClickListener: () -> Unit,
    onNextClickListener: () -> Unit,
    onDateClickListener: (LocalDate) -> Unit,
    onResetClickListener: () -> Unit
) {
    Row {
        IconButton(onClick = { onPrevClickListener() }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Previous month"
            )
        }

        Text(
            text = calendarUiState.selectedDate.date.month.getDisplayName(
                TextStyle.FULL_STANDALONE,
                Locale.getDefault()
            ),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = { onNextClickListener() }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Next month"
            )
        }
        IconButton(onClick = onResetClickListener) {
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

