from django.db import models
from django.conf import settings
from django.contrib.auth.models import AbstractUser

class User(AbstractUser):
    nickname = models.CharField('닉네임', max_length=40, unique=True)
    playcount = models.IntegerField(default=0)
    wincount = models.IntegerField(default=0)
    score = models.IntegerField(default=0)
    friends = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='myfriends')