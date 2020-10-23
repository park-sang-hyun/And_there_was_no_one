from django.db import models
from accounts.models import User
from django.core.validators import MaxValueValidator, MinValueValidator


class Room(models.Model):
    title = models.CharField(max_length=30)
    count = models.IntegerField(default=5, blank=True, null=True,
                                validators=[
                                    MaxValueValidator(8),
                                    MinValueValidator(5)
                                ])
    mode = models.IntegerField(default=1, blank=True, null=True)
    drawtime = models.IntegerField(default=10, blank=True, null=True,
                                    validators=[
                                        MaxValueValidator(30),
                                        MinValueValidator(5)
                                    ])
    showtime = models.IntegerField(default=1, blank=True, null=True,
                                    validators=[
                                        MaxValueValidator(100),
                                        MinValueValidator(0)
                                    ])
    showagain = models.BooleanField(default=True, null=True)
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