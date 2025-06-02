from django import forms
from .models import Album


class AddAlbumForm(forms.ModelForm):
    class Meta:
        model = Album
        fields = ['cover', 'title', 'desciption', 'artist', 'price',
                  'type_of_format', 'release_date']


class RecommendForm(forms.Form):
    to_email = forms.EmailField(required=True)
    to_username = forms.CharField(required=True)
    subject = forms.CharField(required=True)
    message = forms.CharField(widget=forms.Textarea, required=True)