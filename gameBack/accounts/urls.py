from django.urls import path, include
from django.conf.urls import url
from . import views

app_name = 'accounts'

urlpatterns = [
    path('score/', views.score),
    path('deltoken/', views.deltoken),
    path('read/<int:id>/', views.read),
    path('findfriend/<str:nickname>/', views.findfriend),
    path('nickname/<str:nickname>/<str:username>/', views.nickname),
    path('update/', views.update),
    path('delete/', views.delete),
    url(r'^$', views.index, name='index'),
    url(r'^(?P<room_name>[^/]+)/$', views.room, name='room'),
]
