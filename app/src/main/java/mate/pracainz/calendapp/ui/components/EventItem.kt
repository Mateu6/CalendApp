package mate.pracainz.calendapp.ui.components

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

sealed interface EventItem : Parcelable {
    val id: String
    val title: String
    val description: String?
    val dateOfExecution: LocalDate
    val isToday: Boolean
}

@Parcelize
data class BasicEvent(
    override val id: String = UUID.randomUUID().toString(),
    override val title: String,
    override val description: String?,
    override val dateOfExecution: LocalDate,
    override val isToday: Boolean
) : EventItem

@Parcelize
data class TimerEvent(
    override val id: String = UUID.randomUUID().toString(),
    override val title: String,
    override val description: String?,
    override val dateOfExecution: LocalDate,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    override val isToday: Boolean
) : EventItem

@Parcelize
data class ReminderEvent(
    override val id: String = UUID.randomUUID().toString(),
    override val title: String,
    override val description: String?,
    override val dateOfExecution: LocalDate,
    val reminderTime: LocalDateTime,
    override val isToday: Boolean
) : EventItem