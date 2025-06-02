from django.urls import re_path, path, include
from . import views

urlpatterns = [
    path('', views.AlbumListView.as_view(),name='all_albums'),
    path('<str:pk>/<slug:album_slug>/', views.AlbumDetailView.as_view(),name='album_details'),
    path('<str:pk>/songs', views.TracklistView.as_view(),name='tracklist'),
    path('<str:album_id>/buy', views.buy_not_logged_in, name='buy_album'),
    path('new', views.AlbumCreateView.as_view(), name='new_album'),
    path('<str:a_pk>/<slug:a_slug>/delete', views.AlbumDeleteView, name='delete_album'),
    path('<str:a_pk>/<slug:a_slug>/add_song/<str:s_pk>', views.AddSongView, name='new_song'),
    path('<str:a_pk>/<slug:a_slug>/delete_song/<str:s_pk>', views.DeleteSongView, name='delete_song'),
    path('<str:a_pk>/<slug:a_slug>/recommend', views.RecommendAlbum, name='recommend'),
    path('show_recommend', views.ShowMyRecommedations, name='show_rec'),
]