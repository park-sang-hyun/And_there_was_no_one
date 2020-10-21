from django.db import models
from django.conf import settings
from django.contrib.auth.models import AbstractUser

class User(AbstractUser):
    # username, password는 AbstractUser에서 상속받아서 따로 기재하지않는다.

    # email 등등은 사용하지 않으므로 None으로 처리한다. 
    email = None
    first_name = None
    last_name = None

    # verbose_name옵션을 지정하지 않으면 CamelCase 클래스 이름을 기준으로 camel case 이와 같이 모두 소문자로 변경한다

    nickname = models.CharField(
        verbose_name=('Nickname'),
        max_length=30,
        unique=True
    )
    playcount = models.IntegerField('플레이수', default=0)
    wincount = models.IntegerField('이긴수', default=0)
    score = models.IntegerField('점수', default=0)
    friends = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='myfriends')

    # VScode경고 때문에 delete할 때 django.db.utils.OperationalError: no such table: allauth_socialaccount 에러남
    # django는 object와 속성들을 모든 model클래스에 동적으로 추가하는데 vscode가 이를 모르고 경고함
    # 이 것을 해결하기 위해 아래와 같이 표시함.
    # object = UserManager()

    REQUIRED_FIELDS = ['nickname', ]

    # nickname이 해당 user의 대표값이 돼서 admin페이지에서 nickname으로 구분 가능
    def __str__(self):
        return self.nickname