from django.test import TestCase
from django.db.backends.sqlite3.base import IntegrityError
from django.db import transaction
from .models import Album, AlbumComments, Song, AppUsers, UserRecommendations
from .forms import AddAlbumForm, RecommendForm
from django.utils.dateparse import parse_date
from django.urls import reverse, reverse_lazy
from django.core.exceptions import ValidationError
from django.contrib.auth.models import User


# Create your tests here.
class AppAlbumViewTests(TestCase):
    @classmethod
    def setUpTestData(cls):
        print("-----------------------")
        print("-----------------------")
        print("App Album View Testing:")
        print("-----------------------")
        a = Album(title='Set Up Album', desciption='Test Album', artist='aa05722', price=2.99, type_of_format='D', release_date=parse_date("2003-09-09"))
        a.save()
        
        number_of_albums = 5
        for id in range(number_of_albums):
            Album.objects.create(title=f'test {id}', 
                                artist=f'artist {id}',
                                type_of_format='D',
                                price=2.50,
                                release_date=parse_date('2022-01-02'))
            Song.objects.create(title=f'test {id}',
                                runtime=100)
        
        s = Song.objects.get(song_id=1)
        s.albums.add(Album.objects.get(album_id=1))
        s = Song.objects.get(song_id=3)
        s.albums.add(Album.objects.get(album_id=1))
        s = Song.objects.get(song_id=4)
        s.albums.add(Album.objects.get(album_id=1))

        u = User.objects.create_user(email='test1@email.com', username= 'test1@email.com', password='password')
        AppUsers(user= u, display_name= 'test1').save()
        UserRecommendations(to= AppUsers.objects.get(user=u), album=Album.objects.get(album_id=1)).save()
        UserRecommendations(to= AppUsers.objects.get(user=u), album=Album.objects.get(album_id=2)).save()
        u = User.objects.create_user(email='test2@email.com', username= 'test2@email.com', password='password')
        AppUsers(user= u, display_name= 'test2').save()
        UserRecommendations(to= AppUsers.objects.get(user=u), album=Album.objects.get(album_id=3)).save()
        UserRecommendations(to= AppUsers.objects.get(user=u), album=Album.objects.get(album_id=4)).save()
        UserRecommendations(to= AppUsers.objects.get(user=u), album=Album.objects.get(album_id=5)).save()
##---------------------- MODEL TESTING -----------------------## 
            # -------- album ------------
    def test_save_album(self):
        print("Album save")
        db_count = Album.objects.all().count()
        a = Album(title='test', desciption='Test Album', artist='aa05722', price=2.99, type_of_format='D', release_date=parse_date("2003-09-09"))
        a.save()
        self.assertEqual(db_count+1,Album.objects.all().count())
    
    def test_absolute_url_album(self):
        print("Album Absolute URL")
        album = Album.objects.get(album_id=1)
        self.assertEqual(album.get_absolute_url(), '/albums/1/set-up-album/')

    def test_release_date_validation_more_than_3_years_reject(self):
        print("Album Release Date Validation")
        with self.assertRaises(ValidationError):
            a = Album(title='test', desciption='Test Album', artist='aa05722', price=2.99, type_of_format='D', release_date=parse_date("2030-09-09"))
            a.full_clean()
   
    def test_release_date_validation_more_than_1_year_setDateToJan1(self):
            a = Album(title='test', desciption='Test Album', artist='aa05722', price=2.99, type_of_format='D', release_date=parse_date("2025-09-09"))
            a.save()
            a = Album.objects.get(title='test')
            self.assertEqual(a.release_date.month, 1)
            self.assertEqual(a.release_date.day, 1)

    def test_negitive_price(self):
        print("Album Price not Neg")
        with self.assertRaises(ValidationError):
            a = Album(title='test', desciption='Test Album', artist='aa05722', price=-10.00, type_of_format='D', release_date=parse_date("2003-09-09"))
            a.full_clean()
    
        # -------- song ------------
    def test_save_song(self):
        print("Song Save")
        db_count = Song.objects.all().count()
        s = Song(title='Test', runtime=200)
        s.save()
        self.assertEqual(db_count+1,Song.objects.all().count())
    
    def test_runtime_between_30_and_300_secs(self):
        print("Song Runtime between 30 and 300 seconds")
        with self.assertRaises(ValidationError):
            s = Song(title="test", runtime=10)
            s.full_clean()
        with self.assertRaises(ValidationError):
            s = Song(title="test", runtime=1000)
            s.full_clean()

        # -------- comments ------------
    def test_save_album_comments(self):
        print("Album Comments Save")
        db_count = AlbumComments.objects.all().count()
        c = AlbumComments(album=Album.objects.get(album_id=1), user="testUser", comment="First!!")
        c.save()
        self.assertEqual(db_count+1,AlbumComments.objects.all().count())
    
        # -------- user --------
    def test_save_user(self):
        print("App Users Save")
        db_count = AppUsers.objects.all().count()
        u = User.objects.create_user(email='test@email.com', username= 'test@email.com', password='password')
        AppUsers(user= u, display_name= 'test').save()
        self.assertEqual(db_count+1,AppUsers.objects.all().count())

        # -------- recommedations --------
    def test_save_recommendations(self):
        print("Recommeddations Save")
        db_count = UserRecommendations.objects.all().count()
        u = User.objects.create_user(email='test@email.com', username= 'test@email.com', password='password')
        AppUsers(user= u, display_name= 'test').save()
        UserRecommendations(to= AppUsers.objects.get(user=u), album=Album.objects.get(album_id=1)).save()
        self.assertEqual(db_count+1,UserRecommendations.objects.all().count())

##---------------------- CONTROLLER TESTING -----------------------## 
    def test_albumList_url_exists_at_desired_location(self):
        print("Album List View URL in desired location")
        responce = self.client.get(reverse('all_albums'))
        self.assertEqual(responce.status_code, 200)
    
    def test_albumDetails_attributes(self):
        print("Album Details View context attributes correct")
        a = Album.objects.get(album_id=1)
        responce = self.client.get(reverse_lazy('album_details', kwargs={'pk':a.album_id, 'album_slug':a.slug}))
        self.assertEqual(responce.status_code, 200)
        self.assertTrue('comments' in responce.context)
        self.assertTrue('tracklist' in responce.context)
        self.assertTrue('avaliable_songs' in responce.context)
        
        self.assertEqual(len(responce.context['comments']),0)
        self.assertEqual(len(responce.context['tracklist']),3)
        self.assertEqual(len(responce.context['avaliable_songs']),2)

    # def test_createAlbum_works(self):
    #     print("Create Album View Works")
    #     responce = self.client.get(reverse('new_album'))
    #     self.assertEqual(responce.status_code, 200)
        
    
    def test_deleteAlbum_works(self):
        print("Delete Album View Works")
        db_count = Album.objects.all().count()
        a = Album.objects.get(album_id=6)
        responce = self.client.get(reverse_lazy('delete_album', kwargs={'a_pk':a.album_id, 'a_slug':a.slug}))
        self.assertEqual(responce.status_code, 302)
        self.assertEqual(db_count-1,Album.objects.all().count())

    def test_addSong_works(self):
        print("Add Song View Works")
        countB = 0
        countA = 0
        a = Album.objects.get(album_id=1)
        
        responce = self.client.get(reverse_lazy('album_details', kwargs={'pk':a.album_id, 'album_slug':a.slug}))
        countB = len(responce.context['tracklist'])
        responce = self.client.get(reverse_lazy('new_song', kwargs={'a_pk':a.album_id, 'a_slug':a.slug, 's_pk':2}))
        self.assertEqual(responce.status_code, 302)
        
        responce = self.client.get(reverse_lazy('album_details', kwargs={'pk':a.album_id, 'album_slug':a.slug}))
        countA = len(responce.context['tracklist'])
        self.assertEqual(countB+1,countA)


    def test_deleteSong_works(self):
        print("Remove Song from Traacklist View Works")
        countB = 0
        countA = 0
        a = Album.objects.get(album_id=1)
        
        responce = self.client.get(reverse_lazy('album_details', kwargs={'pk':a.album_id, 'album_slug':a.slug}))
        countB = len(responce.context['tracklist'])
        responce = self.client.get(reverse_lazy('delete_song', kwargs={'a_pk':a.album_id, 'a_slug':a.slug, 's_pk':1}))
        self.assertEqual(responce.status_code, 302)
        
        responce = self.client.get(reverse_lazy('album_details', kwargs={'pk':a.album_id, 'album_slug':a.slug}))
        countA = len(responce.context['tracklist'])
        self.assertEqual(countB-1,countA)

    def test_recommendAlbum_works_loggedin_succeed(self):
        print("Recommend Album View Works (when logged in) \n>>includes viewing page & submitting the form")
        login = self.client.login(username='test1@email.com', password='password')
        a = Album.objects.get(album_id=5)
        url = reverse_lazy('recommend', kwargs={'a_pk':a.album_id, 'a_slug':a.slug})
        responce = self.client.get(url)
        
        #can view the form
        self.assertEqual(responce.status_code, 200)
    
        #can make recommendation
        responce = self.client.post(url, data={"to_username": "test2", "album": "4"})
        self.assertEqual(responce.status_code, 200)
    
    def test_recommendAlbum_works_not_loggedin_redirect(self):
        print("Recommend Album View Works (when not logged in)")
        a = Album.objects.get(album_id=5)
        responce = self.client.get(reverse_lazy('recommend', kwargs={'a_pk':a.album_id, 'a_slug':a.slug}))
        
        #redirected to the home page
        self.assertEqual(responce.status_code, 302)
        self.assertTrue(responce.url.startswith(''))

    def test_showMyRecommendations_works_loggedin_succeed(self):
        print("Show User's Recommendations View Works (when logged in)")
        login = self.client.login(username='test1@email.com', password='password')
        responce = self.client.get(reverse_lazy('show_rec'))
        self.assertEqual(responce.status_code, 200)
        
    def test_showMyRecommendations_works_not_loggedin_redirect(self):
        print("Show User's Recommendations View Works (when not logged in)")
        responce = self.client.get(reverse_lazy('show_rec'))
        self.assertEqual(responce.status_code, 302)
        self.assertTrue(responce.url.startswith(''))

    ##---------------------- VIEW TESTING -----------------------## 
    def test_albumList_contains_seeded_album_title(self):
        print("AlbumList Template contains album title")
        url = reverse_lazy('all_albums')
        album = Album.objects.get(album_id=1)
        responce = self.client.get(url)
        self.assertContains(responce, album.title)

    def test_albumDetails_contains_albumInfomation_format(self):
        print("AlbumDtetails Template contains album format type")
        album = Album.objects.get(album_id=1)
        url = reverse_lazy('album_details', kwargs={'pk':album.album_id, 'album_slug':album.slug})
        responce = self.client.get(url)
        self.assertContains(responce, album.type_of_format)