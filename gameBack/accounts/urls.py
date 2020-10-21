from django.urls import path, include
from . import views

app_name = 'accounts'

urlpatterns = [
    path('update/', views.update),
    path('delete/', views.delete),
]
