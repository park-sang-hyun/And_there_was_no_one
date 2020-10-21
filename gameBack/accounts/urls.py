from django.urls import path, include
from django.conf.urls import url
from . import views

app_name = 'accounts'

urlpatterns = [
    path('update/', views.update),
    path('delete/', views.delete),
    url(r'^$', views.index, name='index'),
    url(r'^(?P<room_name>[^/]+)/$', views.room, name='room'),
]
