from django.urls import path
from . import views

urlpatterns = [
    path('', views.show_home, name='page_home'),
    path('help/', views.show_help, name='page_help'),
    path('contact/', views.show_contact, name='page_contact'),
    path('about/', views.show_about, name='page_about'),
    path('account/', views.show_account, name='page_account'),
]
