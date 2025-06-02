from django.core.management.base import BaseCommand
from django.core.files.images import ImageFile
from django.utils.dateparse import parse_date
import json
from datetime import date
from ...models import Song, Album, AlbumComments, AppUsers, UserRecommendations
from django.contrib.auth.models import User
from pathlib import Path

ROOT_DIR = Path('app_album_viewer') / 'management' / 'commands' / 'sample_data'

class Command(BaseCommand):
    def handle(self, *args, **options):
        Song.objects.all().delete()
        Album.objects.all().delete()
        AppUsers.objects.all().delete()

        counter = 1
        #should be empty due to CASCADE
        assert not AlbumComments.objects.all()
        assert not UserRecommendations.objects.all()

        with open(ROOT_DIR / 'sample_data.json') as json_file:
            sample_data = json.load(json_file)
        
        
        for album in sample_data['albums']:
            kwargs = {'title': album['title'],
                    'desciption': album['description'],
                    'artist': album['artist'],
                    'release_date': parse_date(album['release_date']),
                    'type_of_format' : album['format'][0]}
            
            image_path = album['cover']
            if image_path is not None:
                kwargs['cover'] = ImageFile(open(ROOT_DIR / image_path, 'rb'),
                                            name = image_path)
            price = album['price']
            if price == 0:
                price = 0.0
            kwargs['price'] = price
            
            Album(**kwargs).save()

            
            
            for user_display_name, messages in album['comments'].items():
                for message in messages:
                    AlbumComments(album=Album.objects.get(album_id=counter),user=user_display_name, comment=messages).save()
            counter += 1
        
        
        counter = 1
        for song in sample_data['songs']:
            kwargs = {'title': song['title'],
                      'runtime': song['runtime']}
            Song(**kwargs).save()

            s = Song.objects.get(song_id = counter)

            for album in song['albums']:
                for sub_al in Album.objects.all():
                    if sub_al.title == album:
                        s.albums.add(sub_al)

            counter +=1

        for user in sample_data['users']:
            u = User.objects.create_user(username=user['email'], email=user['email'], password='password')
            kwargs = {'user': u,
                        'display_name':user['displayName']}
            AppUsers(**kwargs).save()
            a = AppUsers.objects.get(user=u)
            for r in user['recommenations']:
                UserRecommendations(to=AppUsers.objects.get(user=u), album=Album.objects.get(album_id=r)).save()
            
        self.stdout.write('seed complete')

