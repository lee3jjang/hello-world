import inspect
import requests
from datetime import datetime


# 참고: https://hexoul.blogspot.com/2017/11/telegram-sendmessage-api.html
def send_message(text):
    '''
        Description:
            DartGongsi로 text 전송
        
        Input:
            text: str
            
        Output:
            None
            
        Example:
            text = '이 것은 샘플메시지입니다.'
            send_message(text)
            
        Reference:
            https://hexoul.blogspot.com/2017/11/telegram-sendmessage-api.html
    '''
    access_token = '904469782:AAGKkT977saP5wOVcAl18lALBYbqhKx6V28'
    chat_id = '904981301'
    # url = 'https://api.telegram.org/bot{}/getUpdates'.format(access_token)
    url = 'https://api.telegram.org/bot{}/sendMessage'.format(access_token)
    res = requests.post(url, data={'chat_id': chat_id, 'text': text})
    if res.status_code == 200:
        now = datetime.now().strftime('%Y/%m/%d %H:%M:%S')
        print('({}) {}: 성공적으로 전송되었습니다.'.format(now, __name__ + '.' + inspect.stack()[0].function))
