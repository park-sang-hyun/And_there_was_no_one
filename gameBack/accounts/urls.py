from django.urls import path, include
from . import views

app_name = 'accounts'

urlpatterns = [
    path('update/<int:memberno>/', views.update),
    path('delete/<int:memberno>/', views.delete),
]
