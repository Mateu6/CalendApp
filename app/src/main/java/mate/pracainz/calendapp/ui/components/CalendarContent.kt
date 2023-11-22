package mate.pracainz.calendapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import mate.pracainz.calendapp.data.CalendarDataSource
import mate.pracainz.calendapp.ui.layout.CalendarUiState
import java.time.LocalDate

@Composable
fun CalendarContent(
    dataSource: CalendarDataSource,
    calendarUiState: CalendarUiState,
    onDateClickListener: (LocalDate) -> Unit,
    onPrevClickListener: () -> Unit,
    onNextClickListener: () -> Unit,
    onResetClickListener: () -> Unit,
) {
    LazyColumn {
        item {
            CalendarHeader(
                dataSource = dataSource,
                calendarUiState = calendarUiState,
                onPrevClickListener = onPrevClickListener,
                onNextClickListener = onNextClickListener,
                onDateClickListener = onDateClickListener,
                onResetClickListener = onResetClickListener
            )
        }

        val datesByWeeks = calendarUiState.visibleDates.chunked(7)

        datesByWeeks.forEachIndexed { _, weekDates ->
            item {
                LazyRow {
                    items(weekDates) { date ->
                        CalendarItem(
                            date = date,
                            onClickListener = { clickedDate ->
                                onDateClickListener(clickedDate)
                                // Determine if the clicked date is from the next/previous month
                                if (clickedDate.month != calendarUiState.selectedDate.date.month) {
                                    // Check if it's from the next month
                                    if (clickedDate.isAfter(calendarUiState.selectedDate.date)) {
                                        onNextClickListener()
                                    } else {
                                        // It's from the previous month
                                        onPrevClickListener()
                                    }
                                }},
                            //onLongClickListener = {
                                //EventEditor(isSheetOpen = true)
                            //},
                            isCurrentMonth = date.date.month.equals(calendarUiState.selectedDate.date.month)
                        )
                    }
                }
            }
        }
    }
}