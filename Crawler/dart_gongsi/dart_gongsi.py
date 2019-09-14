import time
import inspect
import requests
import pandas as pd
from datetime import datetime
from bs4 import BeautifulSoup


def _send_message(text, access_token, chat_id):
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
    
    # url = 'https://api.telegram.org/bot{}/getUpdates'.format(access_token)
    url = 'https://api.telegram.org/bot{}/sendMessage'.format(access_token)
    res = requests.post(url, data={'chat_id': chat_id, 'text': text})
    if res.status_code == 200:
        now = datetime.now().strftime('%Y/%m/%d %H:%M:%S')
        program = __name__ + '.' + inspect.stack()[0].function
        print('({}) {}: 성공적으로 전송되었습니다.'.format(now, program))
        
        
def _make_message(**kwargs):
    '''
        Description:
            params를 이용해 전송할 text 생성
        
        Input:
            **kwargs: dictionary(keys: filter_text, column_name, rows)
        
        Output:
            text: str
            
        Example:
            make_message(filter_text='공시', column_name='시간 공시대상회사', rows='17:49 한화투자증권')
    '''
    
    filter_text = kwargs['filter_text']
    column_name = kwargs['column_name']
    rows = kwargs['rows']
    now = datetime.now().strftime('%Y/%m/%d %H:%M:%S')
    desc = '({}) 키워드 [{}] (으)로 검색된 공시내역이 있습니다.'.format(now, filter_text)
    text = desc + '\n\n' + column_name + '\n' + rows
    return text


def _monitoring(filter_text):
    '''
        Description:
            테이블 수집 후 filter_text를 이용해 결과를 텍스트로 리턴
        
        Input:
            filter_text: str
            
        Output:
            (column_name, rows, base_time): (str, str, str)
        
        Example:
            monitoring('공시')
        
    '''
    
    # 데이터 가져오기
    url = 'http://dart.fss.or.kr/dsac001/mainAll.do'
    res = requests.get(url)
    soup = BeautifulSoup(res.text, 'lxml')

    # 테이블 추출하기
    gongsi = pd.read_html(str(soup.select_one('table')))[0].iloc[:, :-1]

    # 필터링
    base_time = gongsi.iloc[1, 0]
    gonsi_filtered = gongsi[(gongsi['시간'].str.contains(base_time)) & (gongsi['보고서명'].str.contains(filter_text))]
    column_name = ' '.join(gongsi.columns.get_values())
    rows = '' if len(gonsi_filtered) == 0 else '\n'.join(list(map(lambda row: ' '.join(row), gonsi_filtered.values)))
    return column_name, rows, base_time

def run(filter_text, access_token, chat_id, delay=5):
    '''
        Description:
            전체 프로그램 실행
        
        Input:
            filter_text: str
            access_token: str
            chat_id: str
            delay: int
        
        Example:
            run('공시', 20)
        
    '''
    
    if delay < 5:
        raise Exception('지연시간을 5초 이상으로 설정해야 합니다')
    now = datetime.now().strftime('%Y/%m/%d %H:%M:%S')
    program = __name__ + '.' + inspect.stack()[0].function
    print('({}) {}: 프로그램을 실행합니다. (키워드: [{}], 딜레이: {}초)'.format(now, program, filter_text, delay))
    prev_time = ''
    while(True):
         # 모니터링
        column_name, rows, current_time = _monitoring(filter_text)

        # 결과전송
        now = datetime.now().strftime('%Y/%m/%d %H:%M:%S')
        
        if (rows != '') and (current_time != prev_time):
            print('({}) {}: 키워드 [{}] (으)로 검색된 내용이 있습니다. ({})'.format(now, program, filter_text, current_time))
            text = _make_message(filter_text = filter_text, column_name = column_name, rows = rows)
            _send_message(text, access_token, chat_id)
            prev_time = current_time
        elif current_time == prev_time:
            print('({}) {}: 키워드 [{}] 검색결과가 이전과 중복됩니다.'.format(now, program, filter_text))
        else:
            print('({}) {}: 키워드 [{}] (으)로 아무것도 검색되지 않았습니다.'.format(now, program, filter_text))
        time.sleep(delay)