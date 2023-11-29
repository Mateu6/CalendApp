package mate.pracainz.calendapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.ui.layout.CalendarUiState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEditor(
    onAddEvent: (EventItem) -> Unit,
    calendarUiState: CalendarUiState,
    ) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val selectedDate by remember { mutableStateOf(calendarUiState.selectedDate.date) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {isSheetOpen = false},
    ){
        Text(
            //if (EventList = empty){}
            text = "Events",
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            value = title,
            onValueChange = { title = it }
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it }
        )
        FloatingActionButton(
            onClick = {
                val newEvent = EventItem(
                    title = title,
                    description = description,
                    date = selectedDate,
                    time = selectedDate,
                    eventType = "Default",
                    typeIcon = Icons.Default.Info,
                    isToday = selectedDate == LocalDate.now()
                )
                onAddEvent(newEvent)
            },
            modifier = Modifier.padding(16.dp)
        ) {

        }
    }
}