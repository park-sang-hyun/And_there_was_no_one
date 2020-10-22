from django.shortcuts import get_object_or_404

from rest_framework.response import Response
from rest_framework.decorators import api_view

from accounts.models import User

from .models import Room, UserRoom
from .serializers import RoomSerializer


# front에서 axios 등의 요청을 보낼 때, header에 ( Authorization: Token 토큰값 ) 을 넣어서 보내줄 것


# 방 만들기
@api_view(['POST'])
def create(request):
    # 받아온 초기 설정 값을 넣어준다
    serializer = RoomSerializer(data=request.data)
    print(serializer)
    # 유효성 검사
    if serializer.is_valid(raise_exception=True):
        # 저장
        room = serializer.save()

        # 중계 테이블 연결을 위해 인스턴스 생성
        userroom = UserRoom(leader=True)
        userroom.room = room
        userroom.user = request.user

        userroom.save()

        context = {
            'data': serializer.data,
            'room_pk': room.pk,
        }

        return Response(context)


# 방 정보 확인
@api_view(['GET'])
def roomlist(request):
    rooms = Room.objects.order_by('-pk')

    roomlist = []

    # 해당 방의 현재 멤버 수를 합쳐서 보내준다.
    for room in rooms:
        r_dict = {
            'member': UserRoom.objects.filter(room=room).count(),
        }
        r = RoomSerializer(instance=room)
        r_dict.update(r.data)
        roomlist.append(r_dict)

    context = {
        'roomlist': roomlist,
    }

    return Response(context)


# 방 입장
@api_view(['POST'])
def comein(request, room_pk):
    # 현재 방의 최대 인원수보다 방에 입장한 유저수가 적은 지 확인
    room = get_object_or_404(Room, pk=room_pk)
    count = UserRoom.objects.filter(room=room).count()

    if room.count <= count:
        context = {
            'message': '인원이 꽉 차서 들어갈 수 없습니다.'
        }
        return Response(context)
    
    # 중계 테이블에서 방과 유저를 연결만 하면 됨
    userroom = UserRoom(leader=False)
    userroom.room = room
    userroom.user = request.user

    userroom.save()

    context = {
        'message': '방에 입장하셨습니다.',
    }

    return Response(context)

        
# 방 수정 (방장)
@api_view(['PUT', 'GET'])
def update(request, room_pk):

    userroom = get_object_or_404(UserRoom, user=request.user)

    # 방장이 아니면 접근할 수 없음
    if userroom.leader == True:
        room = get_object_or_404(Room, pk=room_pk)

        if request.method == 'PUT':
            serializer = RoomSerializer(instance=room, data=request.data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
                return Response(serializer.data)

        else:
            serializer = RoomSerializer(instance=room)
            return Response(serializer.data)
    
    context = {
        'message': '방 설정을 수정할 권한이 없습니다.',
    }

    return Response(context)
    

# 방 삭제
@api_view(['DELETE'])
def delete(request, room_pk):

    userroom = get_object_or_404(UserRoom, user=request.user)

    if userroom.leader == True:
        room = get_object_or_404(Room, pk=room_pk)
        room.delete()

        context = {
            'message': '방이 삭제되었습니다.',
        }

        return Response(context)
    
    context = {
        'message': '방을 삭제할 권한이 없습니다.',
    }

    return Response(context)
    

# 방 나가기
@api_view(['DELETE'])
def comeout(request, room_pk):

    room = get_object_or_404(Room, pk=room_pk)
    userroom = get_object_or_404(UserRoom, user=request.user)

    # 방을 가지고 해당하는 userroom 인원 정보 가져오기
    count = UserRoom.objects.filter(room=room).count()

    # 만약 마지막 1인이었다면 방을 삭제시킨다.
    if count == 1:
        room.delete()

    else:
        if userroom.leader == True:
        # 방장을 다른 유저에게 넘긴다
            nextleader = UserRoom.objects.filter(room=room).order_by('-pk')[0]
            nextleader.leader = True
            nextleader.save()
        # 중계테이블에 해당 유저 정보를 삭제
        userroom.delete()

    context = {
        'message': '방을 나오셨습니다.',
    }

    return Response(context)