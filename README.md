# taskmaster
## lab 26
### Homepage
The main page should be built out to match the wireframe. In particular, it should have a heading at the top of the page, an image to mock the “my tasks” view, and buttons at the bottom of the page to allow going to the “add tasks” and “all tasks” page.
* Home Page<br/>
![image description](screenshots/1.png)

### Add a Task
On the “Add a Task” page, allow users to type in details about a new task, specifically a title and a body. When users click the “submit” button, show a “submitted!” label on the page.
* Add Task<br/>
![image description](screenshots/2.png)

### All Tasks
The all tasks page should just be an image with a back button; it needs no functionality.

* All Task<br/>
![image description](screenshots/3.png)


---
## lab 27

### Homepage
The main page should be modified to contain three different buttons with hardcoded task titles. When a user taps one of the titles, it should go to the Task Detail page, and the title at the top of the page should match the task title that was tapped on the previous page.

The homepage should also contain a button to visit the Settings page, and once the user has entered their username, it should display “{username}’s tasks” above the three task buttons.
* Home Page<br/>
![image description](screenshots/1.png)

### Add a Task
On the “Add a Task” page, allow users to type in details about a new task, specifically a title and a body. When users click the “submit” button, show a “submitted!” label on the page.
* Add Task<br/>
![image description](screenshots/2.png)

### All Tasks
The all tasks page should just be an image with a back button; it needs no functionality.

* All Task<br/>
![image description](screenshots/3.png)

### Task Detail Page<br/>
Create a Task Detail page. It should have a title at the top of the page, and a Lorem Ipsum description.

* Task Detail<br/>
![image description](screenshots/4.png)

### Settings Page<br/>
Create a Settings page. It should allow users to enter their username and hit save.

* Settings Page<br/>
![image description](screenshots/5.png)

---
## lab 28

### Task Model
Create a Task class. A Task should have a title, a body, and a state. The state should be one of “new”, “assigned”, “in progress”, or “complete”.

### Homepage
Refactor your homepage to use a RecyclerView for displaying Task data. This should have hardcoded Task data for now.<br/>

Some steps you will likely want to take to accomplish this:

Create a ViewAdapter class that displays data from a list of Tasks.<br/>
In your MainActivity, create at least three hardcoded Task instances and use those to populate your RecyclerView/ViewAdapter.<br/>
Ensure that you can tap on any one of the Tasks in the RecyclerView, and it will appropriately launch the detail page with the correct Task title displayed<br/>
* HomePage<br/>
![image description](screenshots/home2.png)

---
## lab 29 

### Task Model and Room
Following the directions provided in the Android documentation, set up Room in your application, and modify your Task class to be an Entity.

### Add Task Form
Modify your Add Task form to save the data entered in as a Task in your local database.

### Homepage
Refactor your homepage’s RecyclerView to display all Task entities in your database.

### Detail Page
Ensure that the description and status of a tapped task are also displayed on the detail page, in addition to the title. (Note that you can accomplish this by passing along the entire Task entity, or by passing along only its ID in the intent.)

---
## lab 31

### Espresso Testing
Added test using Espresso. Currently the tests make sure the MainActivity loads correctly, the username can be changed, new tasks can be saved.


---
## lab 32

### Amplify and DynamoDB
Using the amplify add api command, create a Task resource that replicates our existing Task schema. Update all references to the Task data to instead use AWS Amplify to access your data in DynamoDB instead of in Room.

---
## lab 33

### Related Data
Create a second entity for a team, which has a name and a list of tasks. Update your tasks to be owned by a team.

Manually create three teams by running a mutation exactly three times in your code. (You do NOT need to allow the user to create new teams.)
---
## lab 36

### Cognito
- User Login
Add Cognito to your Amplify setup. Add in user login and sign up flows to your application, using Cognito’s pre-built UI as appropriate. Display the logged in user’s username somewhere relevant in your app.

- User Logout
Allow users to log out of your application.
---

## lab 37

###  S3
- Uploads
On the “Add a Task” activity, allow users to optionally select a file to attach to that task. If a user attaches a file to a task, that file should be uploaded to S3, and associated with that task.

![image description](screenshots/6.png)

- Displaying Files
On the Task detail activity, if there is a file that is an image associated with a particular Task, that image should be displayed within that activity. (If the file is any other type, you should display a link to it.)

![image description](screenshots/7.png)

---

## lab 38

### Notifications

Store which team a user is part of in the cloud
Add a Lambda trigger on task creation
Use SNS to send a notification as part of that Lambda file should be uploaded to S3, and associated with that task.

![image description](screenshots/8.png)

---

## lab 41

### Intent Filters

Adding a Task from Another Application
Add an intent filter to your application such that a user can hit the “share” button on an image in another application, choose TaskMaster as the app to share that image with, and be taken directly to the Add a Task activity with that image pre-selected.

`No screenshots, since the app keep cracking`

---

## Lab 42

### Location

- - Location
When the user adds a task, their location should be retrieved and included as part of the saved Task.

- - Displaying Location
On the Task Detail activity, the location of a Task should be displayed if it exists.

![image description](screenshots/9.png)
