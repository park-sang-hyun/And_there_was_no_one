from django.urls import path
from . import views


url_patterns = [
    path('create/', views.create),
    path('comein/<int:room_pk>/', views.comein),
]