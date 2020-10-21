from django.urls import path
from . import views


urlpatterns = [
    path('create/', views.create),
    path('comein/<int:room_pk>/', views.comein),
    path('update/<int:room_pk>/', views.update),
    path('delete/<int:room_pk>/', views.delete),
]