package mate.pracainz.calendapp.ui.components

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate

data class EventItem(
    val id: String,
    val title: String,
    val description: String,
    val date: LocalDate,
    //val duration: Date,
    val eventType: String,
    val typeIcon: ImageVector,
    val isToday: Boolean
)
data class EventType(
    val isEvent: Boolean,
    val isBirthday: Boolean,
    val isTimer: Boolean
)