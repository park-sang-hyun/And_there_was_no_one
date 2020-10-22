from django.conf.urls import url
from . import consumers 

websocket_urlpatterns = [
    # 연결 ProtocolTypeRouter유형을 검사
    # WebSocket 연결 ( ws : // 또는 wss : // ) 인 경우 연결이 AuthMiddlewareStack.
    url(r'^ws/accounts/(?P<room_name>[^/]+)/$', consumers.ChatConsumer),
]
