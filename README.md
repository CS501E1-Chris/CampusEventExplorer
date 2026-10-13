## Campus Event Explorer

Campus Event Explorer is a small Android app built with Jetpack Compose that lets students browse upcoming campus events. Users can search events by title or category, tap an event to see its full details, and share the event’s title, time, and location with other apps through the Android share sheet. The app uses type-safe Navigation Compose routes that pass only an event’s ID between screens, keeps the search text across screen rotation, and shows a friendly “Event unavailable” message if an invalid event is requested.

## Questions

1. Why does the route contain an event ID instead of the complete event object?

- The route contains only the event ID because it identifies the event without copying its data. The detail screen uses the ID to look up the event from the app’s event collection, so it always shows up-to-date details, whereas passing the whole object could show outdated data.
  
2.	Which composable owns the search stat
- In my application, EventNavDetails() owns the search state. It declares Query under rememberSaveable which is defined above the NavHost. It is passed down to EventListScreen, which only displays it and reports changes through onQueryChange. Keeping the state above the NavHost means it survives navigating to the detail screen and back, as well as rotation.

3.	Why does the search value use rememberSaveable?
- Remembersaveable is used because we want the search value to survive rotation, during which the activity is destroyed and recreated, and state value can be lost. Hence, we use Saveable to persist the state through configuration changes.

4.	What happens to the back stack when an event is selected?
- When an event is selected, the event id is passed to the navController which adds that particular event entry to the back stack (on top of the event list screen). When the user navigates away from the event detail screen, that entry is popped from the stack, and returns to the event list screen.

5.	How does the UI request navigation without directly accessing the NavController?
- The UI requests navigation through callbacks without directly having to access the NavController. In my application, some of the callbacks, for example, are onEventSelected and onBack.

6.	What makes the Share action an implicit intent?
- The Share action is an implicit intent because it does not specify the application to open. It just sends the 'ACTION_SEND' along with the type of data to be shared, and the Android system finds the rest of the applications to be shared to.

## Screenshots

# List screen + event detail:

<img width="387" height="858" alt="image" src="https://github.com/user-attachments/assets/ffa8ac95-47e2-4bde-8104-a6fa61a54ae6" />

<img width="382" height="846" alt="image" src="https://github.com/user-attachments/assets/73603460-6463-4a3d-b48e-60722b0a55a3" />

# Filtering with search query:

<img width="377" height="843" alt="image" src="https://github.com/user-attachments/assets/7de7aae1-3dd3-45b8-ac7b-dc86c4f73148" />

<img width="374" height="860" alt="image" src="https://github.com/user-attachments/assets/db65c08f-d0ea-4c82-a4b3-b1bacde03423" />

<img width="387" height="854" alt="image" src="https://github.com/user-attachments/assets/d9bece96-16b9-4aef-8a7f-62df6520d6c3" />

#Sharing event details:

<img width="380" height="852" alt="image" src="https://github.com/user-attachments/assets/83055fc3-9814-472c-b8be-36f7cfd9010a" />

# Rotation:

<img width="387" height="846" alt="image" src="https://github.com/user-attachments/assets/76d4df3c-ca67-4526-8148-03c73322bd77" />

<img width="1825" height="841" alt="image" src="https://github.com/user-attachments/assets/fefe2eac-705d-44b9-84bc-bd81a5a3fba3" />

## AI Disclosure Statement

I used AI assistance (Claude) while completing this assignment. Since I had no prior experience coding navigation in Jetpack Compose, I used AI to help me understand how to implement the navigation setup, including the type-safe routes, the NavHost, and passing the event ID between screens. I also used AI to generate the sample campus event data and to help with formatting and styling the application. I reviewed the generated code, asked questions to understand how each part works and tested the app rigorously.






