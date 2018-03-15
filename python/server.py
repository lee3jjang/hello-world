import socket

# HOST를 ''으로 하면 컴퓨터의 IP를 자동적으로 할당함
HOST = ''
PORT = 9009

def runServer():
    # 소켓 객체 생성
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        # 생성한 소켓을 서버IP 밑 포트와 바인드
        sock.bind((HOST, PORT))
        # 처리 가능한 연결의 수 설정
        sock.listen(1)
        print("client waiting")
        
        # 클라이언트 요청을 기다리고 연결 요청이 오면 수락
        conn, addr = sock.accept()
        with conn:
            print("[%s]와 연결됨" % addr[0])
            while True:
                # 클라이언트의 요청 메시지를 수신
                # 1024바이트를 수신하고 수신 데이터를 data라는 변수로 둠
                x = int(conn.recv(1024).decode())
                y = x*2
                if not x:
                    break
                print("message[%d]" % x)
                
                # 클라이언트로 응답 데이터 송신
                conn.sendall(str(y).encode())
                
runServer()
