#!/usr/bin/env python
# coding: utf-8

import pandas as pd

# DB 핸들링 관련 Utilities
class DBHandler:
    def __init__(self, conn):
        self.conn = conn
        self.cur = conn.cursor()
        
    def createTable(self, tableName, path='./'):
        """
        Description
        -----------
        테이블명과 동일한 이름의 엑셀파일 정보를 읽어 테이블을 생성

        Parameters
        ----------
        tableName : str
                    생성할 테이블명
        path : str
               엑셀파일 경로(초기값 : 현재위치)

        Examples
        --------
        createTable('월별신계약건수', './table')

        Notes
        -----
        1. 경로 안에 만들고자 하는 테이블과 동일한 이름의 엑셀파일이 들어 있어야 함
        2. 엑셀파일 작성 시 NOT_NULL, PK 컬럼에 반드시 대문자 O로 체크 해야 함

        """
        df = pd.read_excel((path + "/" if path[-1] != "/" else path)+'{}.xlsx'.format(tableName))
        query = "CREATE TABLE IF NOT EXISTS '{}' ".format(tableName) + "(" + ("'" + df['컬럼명'] + "' " + df['타입'] + df['NOT_NULL'].apply(lambda x: ' NOT NULL' if x=="O" else '') + ", ").sum() + "PRIMARY KEY" + "(" + ("'" +df.loc[df['PK'] == "O", '컬럼명'] + "', ").sum()[:-2] + ")" + ")"
        self.cur.execute(query)

    def dropTable(self, tableName):
        """
        Description
        -----------
        테이블 제거

        Parameters
        ----------
        tableName : str
                    제거할 테이블명

        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        db.dropTable('월별신계약건수')

        """
        query = 'DROP TABLE {}'.format(tableName)
        self.cur.execute(query)

    def toTable(self, query):
        """
        Description
        -----------
        SELECT 쿼리 결과를 데이터프레임으로 변환

        Parameters
        ----------
        query : str
                SELECT 쿼리 스트링

        Returns
        -------
        데이터프레임

        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        query = "select * from 일반보험마감 where 기준년월 == '201712'"
        df = db.toTable(query)

        """
        self.cur.execute(query)
        col = [row[0] for row in self.cur.description]
        df = pd.DataFrame(self.cur.fetchall(), columns=col)
        return df

    def getColumn(self, tableName):
        """
        Description
        -----------
        SELECT 쿼리 결과를 데이터프레임으로 변환

        Parameters
        ----------
        tableName : str
                    컬럼명을 알고자 하는 테이블명

        Returns
        -------
        컬럼명 리스트

        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        col = getColumn('일반보험마감')

        """

        query = 'SELECT * FROM {} WHERE 1!=1'.format(tableName)
        self.cur.execute(query)
        col = [row[0] for row in self.cur.description]
        return col

    def insTable(self, tableName, df):
        """
        Description
        -----------
        데이터프레임을 테이블에 INSERT

        Parameters
        ----------
        tableName : str
                    타겟테이블명
        df : DataFrame
             넣고자 하는 데이터프레임

        Notes
        -----
        데이터프레임과 테이블의 컬럼 순서를 일치시켜야 함

        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        col = getColumn('월별신계약건수')
        row = [('201712', 1000, '11700205', '2019-01-30'),
               ('201801', 2000, '11700205', '2019-01-30')]
        df = pd.DataFrame(row, columns=col)
        db.insTable('월별신계약건수', df)

        """
        n = df.shape[1]
        for _, row in df.iterrows():
            self.cur.execute('INSERT INTO {} VALUES {}'.format(tableName, '(?'+',?'*(n-1)+')'), row)

    def getTable(self, tableName, baseYymm):
        """
        Description
        -----------
        특정 기준년월으로 테이블을 조회

        Parameters
        ----------
        tableName : str
                    테이블명
        baseYymm : str
                   기준년월

        Returns
        -------
        데이터프레임

        Notes
        -----
        기준년월 컬럼이 없는 테이블에서 사용 금지           

        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        df = db.getTable('일반보험마감', '201712')

        """
        query = "SELECT * FROM {} WHERE 기준년월 = '{}'".format(tableName, baseYymm)
        return self.toTable(query)

    def getTableAll(self, tableName):
        """
        Description
        -----------
        전체 테이블을 조회

        Parameters
        ----------
        tableName : str
                    테이블명

        Returns
        -------
        데이터프레임

        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        df = db.getTableAll('일반보험마감')

        """
        query = 'SELECT * FROM {}'.format(tableName)
        return self.toTable(query)

    def delTable(self, tableName, baseYymm):
        """
        Description
        -----------
        특정 기준년월으로 테이블 내용을 삭제

        Parameters
        ----------
        tableName : str
                    테이블명

        Notes
        -----
        기준년월 컬럼이 없는 테이블에서 사용 금지

        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        db.delTable('일반보험마감', '201712')

        """
        self.cur.execute("DELETE FROM {} WHERE 기준년월 = '{}'".format(tableName, baseYymm))

    def delTableAll(self, tableName):
        """
        Description
        -----------
        전체 테이블 내용을 삭제

        Parameters
        ----------
        tableName : str
                    테이블명

        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        db.delTableAll('일반보험마감')

        """
        self.cur.execute('DELETE FROM {}'.format(tableName))
        
    def commit(self):
        """
        Description
        -----------
        Transaction 확정
        
        Examples
        --------
        from DBHandler import DBHandler
        conn = sqlite3.connect('./data/일반보험모니터링_튜토리얼.db')
        db = DBHandler(conn)
        
        db.delTableAll('일반보험마감')
        db.commit()
        
        """