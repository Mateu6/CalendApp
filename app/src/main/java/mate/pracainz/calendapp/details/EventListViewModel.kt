package mate.pracainz.calendapp.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EventListViewModel : ViewModel() {

    // LiveData or State variable for UI to observe
    // You may use other data structures or LiveData if you are using AndroidViewModel
    var events: List<EventItem> by mutableStateOf(emptyList())
        private set

    // Function to set the list of events

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
