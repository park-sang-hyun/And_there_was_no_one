from django.urls import path
from . import views


urlpatterns = [
    path('create/', views.create),
    path('list/', views.roomlist),
    path('comein/<int:room_pk>/', views.comein),
    path('update/<int:room_pk>/', views.update),
    path('delete/<int:room_pk>/', views.delete),
    path('comeout/<int:room_pk>/', views.comeout),
]