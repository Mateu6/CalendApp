package mate.pracainz.calendapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarView(
    selectedDateTime: LocalDateTime,
) {
    val datePickerState = rememberDatePickerState()
        datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        DatePicker(
            title = { Text(text = "")},
            state = datePickerState,
            modifier = Modifier.fillMaxWidth(),
            dateValidator = { true },
            showModeToggle = false,
            colors = DatePickerDefaults.colors(),

        )
    }
}
private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}