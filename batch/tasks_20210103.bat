@REM https://m.blog.naver.com/PostView.nhn?blogId=superyeoju&logNo=221274899573&proxyReferer=https:%2F%2Fwww.google.com%2F
schtasks /create /tn "작업 스케줄러 테스트" /tr "C:\Users\noilkwon\dev\기타\reuslt.jpg" /sc HOURLY /st 10:19
schtasks /query /tn "작업 스케줄러 테스트"
schtasks /delete /tn "작업 스케줄러 테스트" /f