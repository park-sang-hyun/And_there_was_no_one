from django.urls import path, include
from django.conf.urls import url
from . import views

app_name = 'accounts'

urlpatterns = [
    path('read/<int:id>/', views.read),
    path('nickname/<str:nickname>/', views.nickname),
    path('update/', views.update),
    path('delete/', views.delete),
    url(r'^$', views.index, name='index'),
    url(r'^(?P<room_name>[^/]+)/$', views.room, name='room'),
]
