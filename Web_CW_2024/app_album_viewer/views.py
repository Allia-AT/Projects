from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse, HttpResponseRedirect
from django.views import generic
from django.urls import reverse, reverse_lazy
from django.contrib import messages
from django.contrib.auth.models import User
from .forms import AddAlbumForm, RecommendForm
from django.contrib.auth.decorators import login_required 
from .models import Album, AlbumComments, Song, AppUsers, UserRecommendations
from django import forms
from django.utils.translation import gettext as _
from django.contrib.messages.views import SuccessMessageMixin
from django.core.mail import send_mail
from django.template.loader import render_to_string

# Create your views here.
class AlbumListView(generic.ListView):
    model = Album


class AlbumDetailView(generic.DetailView):
    model = Album
    
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['comments'] = context['album'].albumcomments_set.all()
        context['tracklist'] = context['album'].song_set.all()
        context['avaliable_songs'] = Song.objects.all().difference(context['tracklist'])
        return context
        
def buy_not_logged_in(request, album_id=None):
    messages.add_message(request, messages.INFO, _('Login To Buy'))
    return HttpResponseRedirect(reverse_lazy('all_albums'))


class AlbumCreateView(SuccessMessageMixin, generic.edit.CreateView):
    form_class = AddAlbumForm
    template_name = _('album_add_form.html')
    success_message = _('Album Added')
    
    def get_success_url(self):
        return self.object.get_absolute_url

class TracklistView(generic.DetailView):
    template_name = "app_album_viewer/tracklist.html"
    model = Album
    
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['tracklist'] = context['album'].song_set.all()
        return context

def AlbumDeleteView(request, a_pk=None, a_slug=None):
        album = get_object_or_404(Album, album_id=a_pk)
        album.delete()
        messages.add_message(request, messages.INFO, _('Album Deleted'))
        return HttpResponseRedirect(reverse_lazy('all_albums'))

def AddSongView(request, a_pk=None, a_slug=None, s_pk=None):
    album = get_object_or_404(Album, album_id=a_pk)
    song = get_object_or_404(Song, song_id=s_pk)
    song.albums.add(album)
    return HttpResponseRedirect(reverse_lazy('album_details', kwargs={'pk':a_pk, 'album_slug':a_slug}))

def DeleteSongView(request, a_pk=None, a_slug=None, s_pk=None):
    album = get_object_or_404(Album, album_id=a_pk)
    song = get_object_or_404(Song, song_id=s_pk)
    song.albums.remove(album)
    messages.add_message(request, messages.INFO, _('Song Removed'))
    return HttpResponseRedirect(reverse_lazy('album_details', kwargs={'pk':a_pk, 'album_slug':a_slug}))

def RecommendAlbum(request, a_pk=None, a_slug=None):
    if not request.user.is_authenticated:
        messages.add_message(request, messages.ERROR, _('MustBeLoggedIn'))
        return HttpResponseRedirect(reverse('page_home'))
    
    if request.method == 'GET':
        album = get_object_or_404(Album, album_id = a_pk)
        subject = ('Recommending Album') % {'name':album.title}
        form = RecommendForm({'subject':subject})

    else:
        form = RecommendForm(request.POST)
        album = get_object_or_404(Album, album_id=a_pk)
        
        if form.is_valid():
            user = get_object_or_404(AppUsers, display_name=form.cleaned_data.get('to_username'))
            UserRecommendations(to=user, album=album).save()
            send_mail(form.cleaned_data['subject'],
                        render_to_string('recommend.txt',
                                            {'msg': form.cleaned_data['message']}),
                        None,
                        [[form.cleaned_data['to_email']]])
            messages.add_message(request, messages.SUCCESS, _('Recommedation Sent'))
            return HttpResponseRedirect(album.get_absolute_url())

    return HttpResponse(render(request, 'recommend_form.html',
                               context={'form': form, 'album': album}))

@login_required
def ShowMyRecommedations(request):
    context ={}
    u = get_object_or_404(AppUsers, user=request.user)
    context['recommeded'] = UserRecommendations.objects.filter(to=u)
    for al in context['recommeded']:
        context['album'] =  get_object_or_404(Album, album_id=al.album_id)
    return HttpResponse(render(request, 'app_album_viewer/album_rec.html',
                                context))