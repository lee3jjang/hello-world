schtasks /create /tn "내프로그램" /tr "C:\ProgramData\Anaconda3\python.exe C:\Users\Administrator\Downloads\test.py" /sc minute  /mo 5 /st 05:12 /et 05:30 /sd 2018/08/19
schtasks /run /tn "내프로그램"
schtasks /end /tn "내프로그램"
schtasks /delete /f /tn "내프로그램"

