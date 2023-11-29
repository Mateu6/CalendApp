package mate.pracainz.calendapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun CalendarView(
    dataSource: CalendarDataSource,
    calendarUiState: CalendarUiState,
    onDateClickListener: (LocalDate) -> Unit,
    onLongPressListener: () -> Unit,
    onPrevClickListener: () -> Unit,
    onNextClickListener: () -> Unit,
    onResetClickListener: () -> Unit,
) {
    Column {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onPrevClickListener() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
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
                    imageVector = Icons.Default.KeyboardArrowRight,
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

        // Calendar Content
        val datesByWeeks = calendarUiState.visibleDates.chunked(7)

        datesByWeeks.forEachIndexed { _, weekDates ->
            LazyRow {
                items(weekDates) { date ->
                    CalendarItem(
                        date = date,
                        onDateClickListener = onDateClickListener,
                        onLongPressListener = onLongPressListener,
                        isCurrentMonth = date.date.month == calendarUiState.selectedDate.date.month
                    )
                }
            }
        }
    }
}