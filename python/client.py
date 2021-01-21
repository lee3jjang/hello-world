from socket import *

client_sock = socket(AF_INET, SOCK_STREAM)
client_sock.connect(('127.0.0.1', 8080))

print('연결 확인 됐습니다.')

while True:
    question = input('질문하세요: ')
    client_sock.send(question.encode('utf-8'))
    print('메시지를 전송했습니다.')

    data = client_sock.recv(1024)
    print(f'받은 데이터: {data.decode("utf-8")}')