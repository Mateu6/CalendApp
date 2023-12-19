package mate.pracainz.calendapp.details
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mate.pracainz.calendapp.calendar.model.CalendarUiState
import java.time.LocalDate
import java.time.LocalDateTime

class EventEditorViewModel : ViewModel() {

    private val _selectedEventType = MutableStateFlow("Basic")
    val selectedEventType: StateFlow<String> = _selectedEventType

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    // Additional properties to reference CalendarUiState and selectedDate
    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate


    // Example function to add an event
    fun onAddEvent(event: EventItem) {
        // Perform necessary actions when adding an event
    }

    // Function to handle changes in the selectedTabIndex
    fun onSelectedTabIndexChange(newTabIndex: Int) {
        _selectedTabIndex.value = newTabIndex
    }

    // Function to handle changes in the selectedEventType
    fun onSelectedEventTypeChange(newEventType: String) {
        _selectedEventType.value = newEventType
    }

    // Additional function to add an event with parameters
    fun addEvent(title: String, description: String, eventType: String) {
        val selectedDate = calendarUiState.value.selectedDate

        val newEvent = when (eventType) {
            "Basic" -> BasicEvent(
                title = title,
                description = description,
                dateOfExecution = selectedDate.date,
                isToday = selectedDate.date == LocalDate.now()
            )

            "Timer" -> TimerEvent(
                title = title,
                description = description,
                dateOfExecution = selectedDate.date,
                startTime = LocalDateTime.now(),
                endTime = LocalDateTime.now().plusHours(1), // Replace with actual end time
                isToday = selectedDate.date == LocalDate.now()
            )

            "Reminder" -> ReminderEvent(
                title = title,
                description = description,
                dateOfExecution = selectedDate.date,
                reminderTime = LocalDateTime.now(), // Replace with actual reminder time
                isToday = selectedDate.date == LocalDate.now()
            )

            else -> throw IllegalArgumentException("Unknown event type: $eventType")
        }
    }
}

// Update the UI-related variables
// eventList = eventList + newEvent
// You may want to save the event to your database or