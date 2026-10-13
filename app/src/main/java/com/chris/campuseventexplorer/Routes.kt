package com.chris.campuseventexplorer

import kotlinx.serialization.Serializable

//This is a data object because there is no argument to be passed
@Serializable
data object EventList

//This is a data class because we need the event id to be passed when navigating
@Serializable
data class EventDetail(
    //passing the identity, not the object
    val eventId: Long
)