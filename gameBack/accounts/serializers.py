from django.contrib.auth import get_user_model
from rest_framework import serializers

from allauth.account.adapter import get_adapter
from allauth.account.utils import setup_user_email

from rest_auth.registration.serializers import RegisterSerializer
from rest_framework.authtoken.models import Token

User = get_user_model()

class UserSerializer(RegisterSerializer):
    nickname = serializers.CharField(
        required=True,
        max_length=30,
    )

    def get_cleaned_data(self):
        # 원래 RegisterSerializer의 get_cleaned_data에 nickname 추가
        data_dict = super().get_cleaned_data()
        data_dict['nickname'] = self.validated_data.get('nickname', '')
        return data_dict

    class Meta:
        model = User
        fields = ('id', 'username', 'nickname', )


class TokenSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Token
        fields = ('key', 'user')


