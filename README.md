# CS-360-Mobile-Architecture-Programming

Inventory Management Application Q&A
Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

The requirements for the application were to design an inventory application that uses an SQLite database on an android device with the following functionality:
Log in screen that allows new users to register an account and access the application via validated credentials (email and password). The users are saved to an on-device
database. Create an inventory management home screen that allows a user to view their inventory list as a recycler view grid layout (2 x n). This screen should allow users
to add a new item, with fields relevant to inventory management (name, description, quantity), and save it to a user specific sub-database (i.e., each registered user has
their own database for the inventory). The management screen should also allow a user to adjust quantities of items with buttons on the grid (+ / -), edit item fields, and 
delete items from the inventory. Lastly, the application requires SMS permissions to notify a user when an inventory item reaches 0. The app should function normally without
these permissions, should the user choose not to grant them.

What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?

There were 6 activities used in the application: SignInActivity, which allowed a user to click register button and log in using credentials, RegisterActivity which allowed a new user
to register credentials to log in, InventoryActivity, which served as the homescreen for the application and provided the grid layout list of inventory items, AddItemActivity,
which allows a user to add a new item to the database and view it from the InventoryActivity screen, EditItemActivity, which allows a user to edit or delete an item,
and SMSPermissionsActivity, which allows a user to grant permissions or remove permissions from the device for SMS communication.
The UI designs kept buttons in the same relative locations (such as Add New Item, Confirmation buttons, Login buttons, and Back navigation buttons). Keeping these type of buttons in the same
locations and using a theme for color and text size helped create a seemless and easy to navigate application. Another point to make it easy to navigate is when an option on a screen was clicked
(such as register new user) once the user was registered, it would take them back to the appropriate screen (Log in) instead of having the user navigate their way back on their own.


How did you approach the process of coding your app? What techniques or strategies did you use? How could those techniques or strategies be applied in the future?

One technique that I used that was and will be helpful was keeping classes concise. For example, an Add Item button would only take a user to Add Item. Where this potentially could be combined
with the edit item process, keeping the classes and activities seperate reduces any confusion not only for the user, but during coding as well. One thing I would change if I had more time would be to
develop a nav bar that held the add item, edit item, and delete item functionality to allow a user to navigate to these functions from any screen. Using a wireframe to plan how screens should transition
from one to the other was also helpful, and although I did not build in all of the functionality of all of the screens, the model would help any developer to complete the UI layout following the work
I have already implemented.

How did you test to ensure your code was functional? Why is this process important, and what did it reveal?

I tested my code as best as I could. When clicking on Add Item, the app would restart and take the user back to the SignInActivity rather than the AddItemActivity.
I tried to trouble shoot with debug statements but I was not able to determine why the app would not move to the add item screen. With that being said, I was not able to 
populate the inventory list with items to test and ensure that the grid layout on the InventoryActivity worked properly, but left detailed in-line commenting and //FIXME notes
to guide any developer who followed my work. I was however able to test the SMS permissions with debug statements and confirmed that permissions were either granted or not accordingly
and I was also able to ensure that new users were registered into the database, and their credentials were verified upon sign in. Although the verification system could use some fine
tuning (such as ignoring capitalization for email input validation). So I was able to test new and existing users extensively.

Consider the full app design and development process from initial planning to finalization. Where did you have to innovate to overcome a challenge?

The project was fairly straight forward. Using the lessons provided for building simple layouts, simple database functionality, and simple UI, planning was as easy as
pencil and paper. A check list, and diagrams for UI and functionality. If I had more time, I am sure I could have added more user-friendly features such as images (logos, home screen icons, etc)
as well as proper functionality to populate the database. Following the Zybooks lessons and best practices guidelines helped achieve most of the development to the requirements.

In what specific component of your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

I found that building the database tables was not only relatively easy but also enjoyable. I have worked with NoSQL before, so SQLite was a bit different
but I find that structuring databases is something I am good at. I did a little bit of research and found that primary and foreign keys can autoincrement, so I did
not need to device an algorithm to check userId or itemId to ensure that they were not already in use. However, unfortunately I was not able to test the item database
because I did not working functionality to add items (the activity failed to launch). Given more time, and in heinsight, I would have made a small list of items to populate 
the database without the use of the AddItemActivity to ensure that the primary key (userId) was properly mapped to the foreign key (itemIds) and test this with multiple
users.
