from django.db import models
from django.conf import settings


class Room(models.Model):
    title = models.CharField(max_length=20)             # 방 제목
    count = models.IntegerField(default = 5)            # 인원 수
    mode = models.IntegerField(default = 1)             # 모드 선택
    drawtime = models.IntegerField(default = 10)        # 그림 그리는 시간
    showtime = models.IntegerField(default = 1)         # 완성된 그림 보여주는 시간
    showagain = models.BooleanField(default = True)     # 다시 보여줄 건지 여부


class UserRoom(models.Model):
    leader = models.BooleanField(default = False)       # 리더 여부
    user = models.ForeignKey(                           # 방에 참여한 유저
                        settings.AUTH_USER_MODEL,
                        on_delete=models.CASCADE
                        )
    room = models.ForeignKey(                           # 현재 방
                        Room,
                        on_delete=models.CASCADE
                        )

