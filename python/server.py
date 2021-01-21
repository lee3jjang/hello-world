from socket import *

server_sock = socket(AF_INET, SOCK_STREAM) # 객체 생성 (어드레스 패밀리: 주소 체계, 소켓 타입)
server_sock.bind(('', 8080)) # '' INADDR_ANY, port
server_sock.listen(1) # 1 : 동시접속 허용 개수

connection_sock, addr = server_sock.accept() # 접속 시 리턴됨

print(f'{str(addr)}에서 접속이 확인되었습니다.')

while True:
    data = connection_sock.recv(1024)
    print(f'받은 데이터: {data.decode("utf-8")}')

    answer = input('답변하세요: ')
    connection_sock.send(answer.encode('utf-8'))
    print('메시지를 보냈습니다.')