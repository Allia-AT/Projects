from django.db import models
from django.contrib.auth.models import User
from django.core.validators import MaxValueValidator, MinValueValidator
from django.core.exceptions import ValidationError
from django.utils.dateparse import parse_date
from django.utils.translation import gettext as _
from django.urls import reverse
from django.template.defaultfilters import slugify
import re
import datetime
from decimal import Decimal

# Create your models here.

def validate_release_date(value):
    if value.year > datetime.date.today().year + 3:
        raise ValidationError(_('ReleaseDateTooFar'))
               

class Album(models.Model):
    class typeOfFormat(models.TextChoices):
        DOWNLOAD = "D", _("Digital Download")
        CD = "C", _("CD")
        VINYL = "V", _("Vinyl")
    album_id = models.IntegerField(primary_key=True)
    cover = models.ImageField(default='coverImage.png', upload_to='uploads') #album cover image
    title = models.CharField(max_length=50, blank=False, default=None) #title of the album
    desciption = models.CharField(max_length=100) #description of the album
    artist = models.CharField(max_length=50, blank=False, default=None) #artist's name
    price = models.DecimalField(max_digits=4, decimal_places=2, validators = [MinValueValidator(Decimal('0.00'))]) #price of the album
    type_of_format = models.CharField(max_length = 1, choices=typeOfFormat.choices, default=typeOfFormat.DOWNLOAD) #what format can it be bought in
    release_date = models.DateField(validators=[validate_release_date])
    slug = models.SlugField()


    def get_absolute_url(self):
        return reverse('album_details', kwargs={
            'album_slug': self.slug,
            'pk': self.album_id})
    
    def save(self, *args, **kwargs):
        if self.release_date.year > datetime.date.today().year + 1:
            date = str(self.release_date.year) + "-01-01"
            self.release_date = parse_date(date)
        if not self.slug:
            self.slug = slugify(self.title)
        return super().save(*args, **kwargs)

class Song(models.Model):
    song_id = models.IntegerField(primary_key=True)
    albums = models.ManyToManyField(Album) #album the song is in
    title = models.CharField(max_length=50, blank=False, default=None) #title of the song
    runtime = models.IntegerField(validators = [MinValueValidator(30), MaxValueValidator(300)]) #min and max runtimes for the songs in seconds

    def get_absolute_url(self):
        return reverse('album_details', kwargs={
            'album_slug': self.slug,
            'pk': self.album_id})

class AppUsers(models.Model):
    user = models.OneToOneField(User,on_delete=models.CASCADE)
    display_name = models.CharField(max_length=50, blank=False, default=None) #display name
    
class UserRecommendations(models.Model):
    to = models.ForeignKey(AppUsers, on_delete=models.CASCADE)
    album = models.ForeignKey(Album, on_delete=models.CASCADE)


class AlbumComments(models.Model):
    album = models.ForeignKey(Album, on_delete=models.CASCADE)
    user = models.CharField(max_length=50, default="Anonymous")
    #user = models.ForeignKey(User, on_delete=models.CASCADE)
    comment = models.CharField(max_length=200)