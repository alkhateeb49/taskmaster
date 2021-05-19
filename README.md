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
