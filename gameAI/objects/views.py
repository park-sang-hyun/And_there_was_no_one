from rest_framework.decorators import api_view
from rest_framework.response import Response
from detection.darkflow.cli import cliHandler as de

import base64


kor_word = {
    'apple': '사과',
    'banana': '바나나',
    'mask': '마스크',
}

@api_view(['POST'])
def imgdetection(request):
    turn = request.data['turn']
    roomId = request.data['roomId']
    image_path = './detection/img/'+ 'room' + roomId + '_' + turn + '.jpg'

    imgdata = base64.b64decode(request.data['inputImage'][22:])
    filename = image_path  # I assume you have a way of picking unique filenames
    with open(filename, 'wb') as f:
        f.write(imgdata)

    # 오브젝트 디텍팅 모델 실행
    # text = speak.image
    return_text = de('room' + roomId + '_' + turn + '.jpg')
    context = {
        'message': True,
        'result': return_text[0][0],
    }
    if return_text[0][0] == []:
        context['message'] = False
    else:
        for idx, word in enumerate(return_text[0][0]):
            context['result'][idx] = kor_word[word]

    # return_text = { 'message': 'true' }
    # 결과물 전송
    return Response(context)