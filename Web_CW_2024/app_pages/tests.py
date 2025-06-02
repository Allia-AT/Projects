from django.test import TestCase
from app_album_viewer.models import User, AppUsers
from django.urls import reverse

# Create your tests here.
class AppPagesTestsCase(TestCase):
    #@classmethod
    def test_setUpTestData(cls):
        print("-----------------------")
        print("-----------------------")
        print("App Pages Testing:")
        print("-----------------------")
        
        u = User.objects.create_user(email='test1@email.com', username= 'test1@email.com', password='password')
        AppUsers(user= u, display_name= 'test1').save()

    def test_show_homePage_view(self):
        print("Home Page View Works")
        responce = self.client.get(reverse('page_home'))
        self.assertEqual(responce.status_code, 200)


    def test_show_contactPage_view(self):
        print("Contact Page View Works")
        responce = self.client.get(reverse('page_contact'))
        self.assertEqual(responce.status_code, 200)

    def test_show_helpPage_view(self):
        print("Help Page View Works")
        responce = self.client.get(reverse('page_help'))
        self.assertEqual(responce.status_code, 200)

    def test_show_aboutPage_view(self):
        print("About Page View Works")
        responce = self.client.get(reverse('page_about'))
        self.assertEqual(responce.status_code, 200)

    def test_settings_page_logged_in(self):
        print("Settings View works when logged in")
        
        u = User(username="test")
        u.set_password("test")
        u.save()
        AppUsers.objects.create(user=u, display_name="testUser")
        self.client.login(username="test", password="test")
        responce = self.client.get(reverse('page_account'))
        self.assertEqual(responce.status_code, 200)

    def test_settings_page_not_logged_in(self):
        print("Settings View works when not logged in")

        responce = self.client.get(reverse('page_account'))
        self.assertEqual(responce.status_code, 302)
