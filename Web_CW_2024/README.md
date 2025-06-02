MyMusic Maestro
===============
**About the App**
------------------------
The purpose of this project was to demonstrate my ability to:
1. Develop an interactive web app.
2. Use a Django backend.

<br />This involved me building a web app using Django, which will include Python classes and a database that will provide the user interface to your application using standard web technologies.

<br />**Problem Definition & Requirements**

You will create a music cataloguing application, called 'MyMusic Maestro', to manage albums, songs, and users. The system has the following constraints.
<br />The following were the requirements for the different aspects of the coursework.
>**Albums**
>>have an optional cover art image; they use a default image if a cover art image is not specified
>>have a title, which is required and may not be unique
>>have a description, which could be empty
>>have an artist, which is a string, and required
>>have a price, in GBP, which is required but may be zero
>>have a format, one of 'Digital download', 'CD', 'Vinyl'
>>have a release date; unreleased albums may have a release date up to three years in the future (if more than one year away, it must be set to January)
>>may have songs associated to them, i.e., the tracklist
>>have comments left by users

>**Songs**
>>have a title, which is required and may not be unique
>>have a running time, which is represented in number of seconds and required
>>appear in albums (may be in more than one album)

>**Users**
>>have an email and/or username (their login ID)
>>have a password (their login password)
>>have a display name, shown against their comments
>>comment on albums

Instructions for Using the App
------------------------
<br />To Login:
>User 1
>>Login Email -- user1@email.com
>>
>>Login Password -- password
>>
>>Username (for making recommendations) -- testUser1
>
>User 2
>>Login Email -- user2@email.com
>>
>>Login Password-- password
>>
>>Username (for making recommendations) -- testUser2
>>
<br />**The following commands must be run against the code to run the app:**
```
python3 manage.py compilescss, to compile any SASS files you may have. This is optional and carries no marks; you do not need to use SASS unless desired.
python3 manage.py compilemessages, to generate localisation strings.
python3 manage.py makemigrations, in case required migrations were not supplied.
python3 manage.py migrate, to perform the migrations to the local database.
python3 manage.py seed, to insert your sample data into the database.
python3 manage.py test, to run your tests.
python3 manage.py runserver, to run your web application.
```

**Cool Features:**
>1. Clicking Buy on an album when not logged in will result in a popup
>2. Both the settings & recommendation pages are protected via @login_required in the views and within the templates themselves
>3. Recommendations are saved to the user account that you recommend to

**Errors:**
>1. When adding an album, there is an error after submitting the form. However, if you manually go back to the album list view by deleting the "new" from the URL, the album has been added successfully.

Structure
---------

Project structure is as follows:
1. /MyMusicMaestro -- configuration directory for project
2. /templates -- project-wide shared templates
3. /static -- project-wide shared static resources
4. /locale -- project-wide string localisation
5. /media -- user-uploaded file directory
6. /app_pages -- pages sub-app for public pages, e.g., landing page, help page, about page
7. /app_album_viewer -- viewer/editor sub-app for album management functionality

The sub-apps have the following nested structure:
1. /app_NAME/locale/ -- translations specific to the sub-app
2. /app_NAME/migrations/ -- migrations specific to the sub-app, if applicable
3. /app_NAME/templates/ -- view templates specific to the sub-app
4. /app_NAME/[apps,urls,models,views,tests,admin].py -- Django setup and code for each sub-app


Credits
-------
coverImage.png -- https://www.pexels.com/photo/black-and-gray-vinyl-record-2746823/
Album icons from Openclipart.
