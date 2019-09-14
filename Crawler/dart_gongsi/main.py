import sys
from dart_gongsi import run


if __name__ == '__main__':
    '''
    Description:
        특정 키워드 및 일정 간격으로 DART 공시현황 모니터링
    Example:
        python dart_gonsi_main "증권" 5
    '''
    access_token = '904469782:AAGKkT977saP5wOVcAl18lALBYbqhKx6V28'
    chat_id = '904981301'
    filter_text = '' if len(sys.argv) < 2 else sys.argv[1]
    delay = 5 if len(sys.argv) < 3 else int(sys.argv[2])
    run(filter_text, access_token, chat_id, delay)