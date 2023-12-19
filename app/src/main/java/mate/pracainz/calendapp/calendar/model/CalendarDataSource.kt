package mate.pracainz.calendapp.calendar.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

class CalendarDataSource {
    private val today: LocalDate
        get() {
            return LocalDate.now()
        }

    fun getWeekData(
        startDate: LocalDate = today,
        lastSelectedDate: LocalDate
    ): CalendarUiState {
        val firstDayOfWeek = startDate.toStartOfWeek()
        val endDayOfWeek = startDate.toEndOfWeek()
        val visibleDatesWeek = getDatesBetween(firstDayOfWeek, endDayOfWeek)

        return toUiModel(visibleDatesWeek, lastSelectedDate)
    }

    fun getMonthData(
        lastSelectedDate: LocalDate
    ): CalendarUiState {
        val firstDayOfMonthContainingSelection = lastSelectedDate.toStartOfMonth()
        val lastDayOfMonthContainingSelection = lastSelectedDate.toEndOfMonth()

        val visibleDatesMonth = getDatesBetween(
            findFirstMonday(firstDayOfMonthContainingSelection),
            findLastSunday(lastDayOfMonthContainingSelection)
        )

        return toUiModel(visibleDatesMonth, lastSelectedDate)
    }

    private fun findFirstMonday(
        date: LocalDate
    ): LocalDate {
        var current = date
        while (current.dayOfWeek != DayOfWeek.MONDAY) {
            current = current.minusDays(1)
        }
        return current
    }

    private fun findLastSunday(
        date: LocalDate
    ): LocalDate {
        var current = date
        while (current.dayOfWeek != DayOfWeek.SUNDAY) {
            current = current.plusDays(1)
        }
        return current
    }

    private fun getDatesBetween(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiState {
        return CalendarUiState(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    fun toItemUiModel(
        date: LocalDate,
        isSelectedDate: Boolean
    ) = CalendarUiState.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}