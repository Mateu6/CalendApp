package mate.pracainz.calendapp.ui.components

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

class CalendarDataSource {
    val today: LocalDate
        get() {
            return LocalDate.now()
        }

    fun getWeekData(
        startDate: LocalDate = today,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDatesWeek = getDatesBetween(firstDayOfWeek, endDayOfWeek)

        return toUiModel(visibleDatesWeek, lastSelectedDate)
    }

    fun getMonthData(
        startDate: LocalDate = today,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        val firstDayOfMonth = startDate.withDayOfMonth(1)
        val lastDayOfMonth = startDate.withDayOfMonth(startDate.lengthOfMonth())

        val firstMonday = findFirstMonday(firstDayOfMonth)
        val lastSunday = findLastSunday(lastDayOfMonth)

        val visibleDatesMonth = getDatesBetween(firstMonday, lastSunday)

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
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemUiModel(
        date: LocalDate,
        isSelectedDate: Boolean
    ) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}