package com.chris.campuseventexplorer

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun EventNavDetails(){
    //keeps track of the backstack
    val navController = rememberNavController()
    var query by rememberSaveable() {
        mutableStateOf("")
    }
    //logic for getting filtered events after a search
    val filteredEvents = remember(query){
        //filtering through all the events
        sampleEvents.filter {
            it.title.contains(query, ignoreCase = true) || it.category.contains(
                query,
                ignoreCase = true
            )
        }
    }

    NavHost(navController = navController, startDestination = EventList){
        //when this is at the top of the backstack, the content will be shown according to the parameters
        composable<EventList> {
            displayEventList(
                events = filteredEvents,
                query = query,
                onQueryChange = {query = it},
                onEventSelected = {id -> navController.navigate(EventDetail(id))}
            )
        }

        composable<EventDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<EventDetail>()
            val event = sampleEvents.firstOrNull {it.id == route.eventId}
            val context = LocalContext.current

            displayEventDetails(
                event = event,
                onBack = {navController.navigateUp()},
                onShare = {event?.let {shareEvent(context, it)}}
            )
        }
    }
}

fun shareEvent(context: Context, event: CampusEvent) {
    val shareText = "${event.title} - ${event.time} - ${event.location}"

    //Intent is a way to send a message to the Android systen ti ask ut to do somenhting
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, event.title)
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(sendIntent, "Share event"))
}
