import os
import pandas as pd
import sqlite3

# 파일 불러오기
file_names = os.listdir('result')
file_names.sort()
file_name = file_names[-1]
print('--- 다음 파일을 업로드 합니다: {} ---'.format(file_name))
exchange = pd.read_excel('./result/{}'.format(file_name))
exchange['날짜'] = exchange['날짜'].apply(lambda x: x.strftime('%Y%m%d'))

# DB 연결
conn = sqlite3.connect('mydb.db')
cur = conn.cursor()

# 테이블 생성
sql_create_exchange_table = """
    CREATE TABLE IF NOT EXISTS exchange (
      날짜  text PRIMARY KEY,
      매매기준율 number,
      현찰_사실때 number,
      현찰_파실때 number,
      송금_보내실때 number,
      송금_받으실때 number,
      TC_사실때 number,
      외화수표_파실때 number
    );
"""
cur.execute(sql_create_exchange_table)

# 데이터 업로드
print('--- 데이터 업로드 시작 ---')
sql = "INSERT INTO exchange(날짜, 매매기준율, 현찰_사실때, 현찰_파실때, 송금_보내실때, 송금_받으실때, TC_사실때, 외화수표_파실때) values (?, ?, ?, ?, ?, ?, ?, ?)"
cur.executemany(sql, exchange.values)
conn.commit()
print('--- 데이터 업로드 완료(데이터 수: {:,.0f}) ---'.format(len(exchange)))