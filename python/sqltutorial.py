import smtplib, os, pickle  # smtplib: 메일 전송을 위한 패키지
from email import encoders  # 파일전송을 할 때 이미지나 문서 동영상 등의 파일을 문자열로 변환할 때 사용할 패키지
from email.mime.text import MIMEText   # 본문내용을 전송할 때 사용되는 모듈
from email.mime.multipart import MIMEMultipart   # 메시지를 보낼 때 메시지에 대한 모듈
from email.mime.base import MIMEBase     # 파일을 전송할 때 사용되는 모듈

smtp = smtplib.SMTP('smtp.gmail.com', 587)
smtp.ehlo()
smtp.starttls()
smtp.login('lee3jjang@gmail.com', 'xxxxxxx')

msg = MIMEMultipart()
msg['Subject'] = '저의 SMTP Send Text 테스트'
part = MIMEText('SMTP로 메일 보내기 본문 메시지입니다')
msg.attach(part)
with open('linux.hwp', 'rb') as f:
    part = MIMEBase("application", "octet-stream")
    part.set_payload(f.read())
    encoders.encode_base64(part)
    part.add_header('Content-Disposition', 'attachment', filename='notlinux.hwp')
    msg.attach(part)
msg['To'] = 'lee3jjang@naver.com'
smtp.sendmail('lee3jjang@gmail.com', 'lee3jjang@naver.com', msg.as_string())