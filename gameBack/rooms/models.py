from django.db import models
from accounts.models import User
from django.core.validators import MaxValueValidator, MinValueValidator


class Room(models.Model):
    title = models.CharField(max_length=30)
    count = models.IntegerField(default=5, blank=True, null=True,
                                validators=[
                                    MaxValueValidator(8),
                                    MinValueValidator(4)
                                ])
    mode = models.IntegerField(default=1, blank=True, null=True)
    difficulty = models.IntegerField(default=1, blank=True, null=True,
                                    validators=[
                                        MaxValueValidator(3),
                                        MinValueValidator(1)
                                    ])
    start = models.BooleanField(default=False, null=True)

    class Meta:
        managed = False
        db_table = 'Room'


class UserRoom(models.Model):
    leader = models.BooleanField(default=False, null=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    room = models.ForeignKey(Room, on_delete=models.CASCADE)

    class Meta:
        managed = False
        db_table = 'UserRoom'