from channels.generic.websocket import WebsocketConsumer
from django.contrib.auth.decorators import login_required
from asgiref.sync import async_to_sync
from channels.auth import login
import json

class ChatConsumer(WebsocketConsumer):
    # websocket이 연결 되었을 때 행해질 메서드
    def connect(self):
        print("SDFSDFSDFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")
        print(self)
        print(self.scope)
        print(self.scope['user'])
        print(self.scope['user'].pk)
        print(self.scope['url_route'])
        print(self.scope['url_route']['kwargs'])
        print(self.scope['url_route']['kwargs']['room_name'])
        print(self.channel_layer)
        
        
        self.room_name = self.scope['url_route']['kwargs']['room_name']
        self.room_group_name = 'chat_%s' % self.room_name

        # Join room group
        async_to_sync(self.channel_layer.group_add)(
            self.room_group_name,
            self.channel_name
        )
        print(self.room_group_name)
        print(self.channel_name)
        print(self.room_name)
        self.accept()
        print("SDFSDFSDFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")
        # 여기서 사용자 이름으로 User 객체를 받아와서 DB에 저장하자. 
        # return self
        
	# 연결이 끊길 경우 행해질 메서드
    # @login_required
    def disconnect(self, close_code):
        # Leave room group
        async_to_sync(self.channel_layer.group_discard)(
            self.room_group_name,
            self.channel_name
        )

	# 클라이언트로부터 메세지를 받으면 행해질 메서드
    # 아래에서는 메세지를 받으면 다시 클라이언트로 보내는 코드를 작성한 예시이다.

    def receive(self, text_data):
        text_data_json = json.loads(text_data)
        message = text_data_json['message']

        # Send message to room group
        async_to_sync(self.channel_layer.group_send)(
            self.room_group_name,
            {
                'type': 'chat_message',
                'message': message
            }
        )

        # async_to_sync(login)(self.scope, user)
        # self.scope["session"].save()

    # Receive message from room group

    def chat_message(self, event):
        message = event['message']

        # Send message to WebSocket
        self.send(text_data=json.dumps({
            'message': message
        }))