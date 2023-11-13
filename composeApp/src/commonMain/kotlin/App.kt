import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import kotlinx.datetime.*
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var location by remember { mutableStateOf("Europe/Paris") }
        var timeAtLocation by remember { mutableStateOf("No location selected") }
        Column {
            Text(timeAtLocation)
            TextField(
                value = location,
                onValueChange = {
                    location = it
                }
            )
            Button(onClick = {
                timeAtLocation = currentTimeAt(location) ?: "Invalid location"
            }) {
                Text("Show Time At Location")
            }
        }
    }
}

fun currentTimeAt(location: String): String? {
    fun LocalTime.formatted() = "$hour:$minute:$second"

    return try {
        val time = Clock.System.now()
        val zone = TimeZone.of(location)
        val localTime = time.toLocalDateTime(zone).time
        "${location}의 시간은 ${localTime.formatted()}"
    } catch (e: IllegalTimeZoneException) {
        null
    }
}