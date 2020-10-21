from django.shortcuts import render

from rest_framework.response import Response
from rest_framework.decorators import api_view

from accounts.models import User

from .models import Room, UserRoom
from .serializers import RoomSerializer, UserRoomSerializer


# front에서 axios 등의 요청을 보낼 때, header에 ( Authorization: Token 토큰값 ) 을 넣어서 보내줄 것


# 방 만들기
@api_view(['POST'])
def create(request):
    # 받아온 초기 설정 값을 넣어준다
    serializer = RoomSerializer(data=request.data)

    # 유효성 검사
    if serializer.is_valid(raise_exception=True):
        # 저장
        room = serializer.save()

        # 중계 테이블 연결을 위해 인스턴스 생성
        userroom = UserRoomSerializer()
        userroom.leader = True
        userroom.room = room
        userroom.user = request.user

        # 유효성 검사
        if userroom.is_valid(raise_exception=True):
            userroom.save()

            context = {
                'data': serializer.data,
                'room_pk': room.pk,
            }

            return Response(context)


# 방 입장
@api_view(['POST'])
def comein(request, room_pk):
    # 중계 테이블에서 방과 유저를 연결만 하면 됨
    userroom = UserRoomSerializer()
    userroom.leader = False
    userroom.room = get_object_or_404(Room, pk=room_pk)
    userroom.user = request.user

    # 유효성 검사
    if userroom.is_valid(raise_exception=True):
        userroom.save()

        context = {
            'message': '방에 입장하셨습니다.',
        }

        return Response(context)