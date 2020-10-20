from django.shortcuts import render

from rest_framework.response import Response
from rest_framework.decorators import api_view

from accounts.models import User

from .models import Room, UserRoom
from .serializers import RoomSerializer, UserRoomSerializer


# 방 만들기
@api_view(['POST'])
def create(request):
    serializer = RoomSerializer(data=request.data)
