package mate.pracainz.calendapp.ui.components

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate
import java.util.UUID

data class EventItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val date: LocalDate,
    val time: LocalDate,
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