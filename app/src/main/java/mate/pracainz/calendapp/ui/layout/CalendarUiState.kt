package mate.pracainz.calendapp.ui.layout

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CalendarUiState(
    var selectedDate: Date,
    var visibleDates: List<Date>,
) {
    val startDate: Date get() = visibleDates.first()
    val endDate: Date get() = visibleDates.last()

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean,
    ) : Comparable<Date> {
        val day: String = date.format(DateTimeFormatter.ofPattern("E"))

        override fun compareTo(other: Date): Int {
            return this.date.compareTo(other.date)
        }
    }
}
