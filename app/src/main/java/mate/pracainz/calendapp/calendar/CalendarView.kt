package mate.pracainz.calendapp.calendar

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.calendar.model.CalendarViewModel
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarView(
    viewModel: CalendarViewModel, // Pass the CalendarViewModel instance
) {
    val calendarUiState by viewModel.calendarUiState.collectAsState()

    Column {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { viewModel.onPrevClick() }) {
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

            IconButton(onClick = { viewModel.onNextClick() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next month"
                )
            }

            IconButton(onClick = { viewModel.onResetClick() }) {
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
                        calendarViewModel = viewModel, // Pass the CalendarViewModel instance
                        isCurrentMonth = date.date.month == calendarUiState.selectedDate.date.month
                    )
                }
            }
        }
    }
}