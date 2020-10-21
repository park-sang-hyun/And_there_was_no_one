from django.shortcuts import render
from django.shortcuts import get_object_or_404
from rest_framework.decorators import api_view
from .models import User
from django.http import JsonResponse


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
