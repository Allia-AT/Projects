from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse, HttpResponseRedirect
from django.urls import reverse
from django.contrib.auth import authenticate, login
from django.contrib.auth.decorators import login_required 
from app_album_viewer.models import AppUsers
from django.core.mail import send_mail
from django.template.loader import render_to_string
from django.utils.translation import gettext as _
from django.contrib import messages

# Create your views here.

def show_home(request):
    return HttpResponse(render(request, 'home.html'))

def show_help(request):
    return HttpResponse(render(request, 'help.html'))

@login_required
def show_account(request):
    app_user = get_object_or_404(AppUsers, user = request.user)
    return HttpResponse(render(request, 'account.html',
                                context={'app_user': app_user}))

def show_about(request):
    return HttpResponse(render(request, 'about.html'))

def show_contact(request):
    return HttpResponse(render(request, 'contact.html'))
