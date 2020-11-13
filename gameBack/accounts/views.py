from django.shortcuts import render
from django.utils.safestring import mark_safe

from django.shortcuts import get_object_or_404
from rest_framework.decorators import api_view
from .models import User
from .serializers import UserSerializer

from django.http import JsonResponse
import json

from rest_framework.authtoken.models import Token

@api_view(['POST']) # read
def deltoken(request):
    token = Token.objects.get(user_id=request.data['id'])
    token.delete()

    return JsonResponse({'message':'done'})

@api_view(['GET']) # read
def read(request, id):
    user = get_object_or_404(User, id=id)
    serializer = UserSerializer(user)
    context = {
        'nickname': user.nickname,
        'score' : user.score
    }
    return JsonResponse(context)


@api_view(['GET']) # read
def nickname(request, nickname):
    print(nickname)

    try:
        get_object_or_404(User, nickname=nickname)
        return JsonResponse({'message':'exist'})
    except:
        return JsonResponse({'message':'ok'})


@api_view(['PUT']) # update
def update(request):
    me = get_object_or_404(User, id=request.user.pk)
    me.nickname = request.data['nickname']
    # 수정할 내용이 닉네임밖에 없는 것 같음 
    me.save()
    return JsonResponse({'message':'SUCCESS'})


@api_view(['DELETE']) # delete
def delete(request):
    me = get_object_or_404(User, id=request.user.pk)
    me.delete()
    return JsonResponse({'message':'SUCCESS'})

# def room(request):
#     context = {}
#     return render(request, 'accounts/room.html', context)

def index(request):
    return render(request, 'accounts/index.html', {})

def room(request, room_name):
    return render(request, 'accounts/room.html', {
        'room_name_json': mark_safe(json.dumps(room_name))
    })