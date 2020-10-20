from rest_framework import serializers
from .models import Room, UserRoom


class RoomSerializer(serializers.ModelSerializer):
    class Meta:
        model = Room
        fields = '__all__'

class UserRoomSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserRoom
        fields = ('leader', )
