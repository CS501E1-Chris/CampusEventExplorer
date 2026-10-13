package com.chris.campuseventexplorer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun displayEventList(
    events: List<CampusEvent>,
    query: String,
    onQueryChange: (String) -> Unit,
    onEventSelected: (Long) -> Unit
)
{
    Scaffold(
        topBar = { TopAppBar(title = { Text("Campus Event Explorer") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
        {
            OutlinedTextField(
                //this is for the search field displayed to the users
                value = query,
                onValueChange = onQueryChange,
                label = { Text("Search for Events!")},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            //checking if search did not return any result
            if (events.isEmpty()){
                Text(
                    text = "No events match your query!",
                    modifier = Modifier.padding(16.dp)
                )
            }
            //else we can display the events in a lazy list
            else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    items(events, key = {it.id}) {event -> EventListItem(
                    //lambda for each event item in the list
                    //key is a stable id for each event instead of tracking the position of the item
                        event = event,
                        onClick = {onEventSelected(event.id)}
                    )}
                }

            }
        }
        }
}

//UI for each item of the list
@Composable
private fun EventListItem(event: CampusEvent, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(event.title, style = MaterialTheme.typography.titleMedium)
            Text(event.category, style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(4.dp))
            Text("${event.time} - ${event.location}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EventListScreenPreview() {
    displayEventList(
        events = sampleEvents,
        query = "",
        onQueryChange = {},
        onEventSelected = {}
    )
}


//FOR DISPLAYING UI OF EVENT DETAILS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun displayEventDetails(
    event: CampusEvent?,
    onBack: () -> Unit,
    onShare: () -> Unit
)
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(event?.title ?: "Event unavailable") },
                navigationIcon = {
                    //to navigate back to the event list screen
                    TextButton(onClick = onBack) {
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        }
    ) { innerPadding ->
        if (event == null) {
            EventUnavailable(
                onReturn = onBack,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(event.title, style = MaterialTheme.typography.headlineSmall)
                Text(event.category, style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary)
                Text("When: ${event.time}", style = MaterialTheme.typography.bodyLarge)
                Text("Where: ${event.location}", style = MaterialTheme.typography.bodyLarge)
                HorizontalDivider()
                Text(event.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))

                //button to share the event
                Button(onClick = onShare, modifier = Modifier.fillMaxWidth()) {
                    Text("Share event")
                }
            }
        }
    }
}

@Composable
private fun EventUnavailable(onReturn: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Event unavailable", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onReturn) {
            Text("Return to events")
        }
    }
}

