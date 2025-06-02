MyMusic Maestro
===============

Instructions for marking
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

Cool Features:
>1. Clicking Buy on an album when not logged in will result in a popup
>2. Both the settings & recommendation pages are protected via @login_required in the views and within the templates themselves
>3. Recommendations are saved to the user account that you recommend to

Errors: 
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
