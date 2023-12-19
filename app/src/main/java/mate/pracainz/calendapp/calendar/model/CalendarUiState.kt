package mate.pracainz.calendapp.calendar.model

import mate.pracainz.calendapp.details.EventItem
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Extension function for LocalDate
fun LocalDate.toStartOfWeek(): LocalDate {
    return this.with(DayOfWeek.MONDAY)
}

fun LocalDate.toEndOfWeek(): LocalDate {
    return this.toStartOfWeek().plusDays(6)
}

fun LocalDate.toStartOfMonth(): LocalDate {
    return this.withDayOfMonth(1)
}

fun LocalDate.toEndOfMonth(): LocalDate {
    return this.withDayOfMonth(this.lengthOfMonth())
}

data class CalendarUiState(
    var selectedDate: Date = Date(LocalDate.now(), false, false),
    var visibleDates: List<Date> = emptyList()
) {
    val startDate: Date
        get() = visibleDates.firstOrNull() ?: Date(LocalDate.now(), false, false)

    val endDate: Date
        get() = visibleDates.lastOrNull() ?: Date(LocalDate.now(), false, false)


    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean,
        val events: List<EventItem> = emptyList() // Add events property
    ) : Comparable<Date> {
        val day: String = date.format(DateTimeFormatter.ofPattern("E"))

        override fun compareTo(other: Date): Int {
            return this.date.compareTo(other.date)
        }
    }
}