package mate.pracainz.calendapp.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.calendar.model.CalendarUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarItem(
    date: CalendarUiState.Date,
    calendarViewModel: CalendarViewModel, // Pass the CalendarViewModel instance
    isCurrentMonth: Boolean
) {
    Card(
        modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 2.dp)
            .alpha(if (isCurrentMonth) 1f else 0.5f)
            .combinedClickable(
                onClick = { calendarViewModel.onDateClicked(date.date) },
                onLongClick = { calendarViewModel.onLongPress(date.date) },
            ),
        colors = CardDefaults.cardColors(
            containerColor = when {
                date.isToday -> MaterialTheme.colorScheme.tertiary
                date.isSelected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.secondary
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(54.5.dp)
                .height(55.dp)
        ) {
            Text(
                text = date.day, // day "Mon", "Tue"
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.background
            )
            Text(
                text = date.date.dayOfMonth.toString(), // date "15", "16"
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}