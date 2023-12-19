package mate.pracainz.calendapp.calendar.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(private val dataSource: CalendarDataSource) : ViewModel() {

    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    init {
        updateCalendarUiState()
    }

    fun onDateClicked(date: LocalDate) {
        _calendarUiState.value = _calendarUiState.value.copy(
            selectedDate = dataSource.toItemUiModel(date, true),
            visibleDates = _calendarUiState.value.visibleDates.map {
                it.copy(isSelected = it.date.isEqual(date))
            }
        )
        _selectedTabIndex.value = 0
    }

    fun onLongPress(date: LocalDate) {
        _calendarUiState.value = _calendarUiState.value.copy(
            selectedDate = dataSource.toItemUiModel(date, true),
            visibleDates = _calendarUiState.value.visibleDates.map {
                it.copy(isSelected = it.date.isEqual(date))
            }
        )
        _selectedTabIndex.value = 1
    }

    fun onPrevClick() {
        updateVisibleDates(_calendarUiState.value.selectedDate.date.minusMonths(1))
    }

    fun onNextClick() {
        updateVisibleDates(_calendarUiState.value.selectedDate.date.plusMonths(1))
    }

    fun onResetClick() {
        updateCalendarUiState()
    }

    private fun updateCalendarUiState() {
        viewModelScope.launch {
            _calendarUiState.value = dataSource.getMonthData(
                lastSelectedDate = LocalDate.now()
            )
        }
    }

    private fun updateVisibleDates(startDate: LocalDate) {
        viewModelScope.launch {
            _calendarUiState.value = dataSource.getMonthData(
                lastSelectedDate = startDate
            )
        }
    }
}