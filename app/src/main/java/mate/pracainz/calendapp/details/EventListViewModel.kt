package mate.pracainz.calendapp.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class EventListViewModel : ViewModel() {

    // LiveData or State variable for UI to observe
    // You may use other data structures or LiveData if you are using AndroidViewModel
    var events: List<EventItem> by mutableStateOf(emptyList())
        private set

    // Function to set the list of events

    init {
        // Initialize with sample data
        events = generateSampleEvents()
    }

    private fun generateSampleEvents(): List<EventItem> {
        return listOf(
            BasicEvent(title = "Sample Basic Event 1", description = "Description 1", dateOfExecution = LocalDate.now(), isToday = true),
            TimerEvent(title = "Sample Timer Event 1", description = "Description 2", dateOfExecution = LocalDate.now(), startTime = LocalDateTime.now(), endTime = LocalDateTime.now().plusHours(1), isToday = false),
            ReminderEvent(title = "Sample Reminder Event 1", description = "Description 3", dateOfExecution = LocalDate.now(), reminderTime = LocalDateTime.now().plusMinutes(30), isToday = true),
            // Add more sample events as needed
        )
    }

    // Function to handle different types of events
    fun handleEventClick(event: EventItem) {
        when (event) {
            is BasicEvent -> {
                // Handle click for BasicEvent
            }
            is TimerEvent -> {
                // Handle click for TimerEvent
            }
            is ReminderEvent -> {
                // Handle click for ReminderEvent
            }
            // Add more cases as needed for other event types
        }
    }
}
