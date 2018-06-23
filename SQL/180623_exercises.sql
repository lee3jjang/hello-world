create table ex2_1 (
    column1 char(10),
    column2 varchar2(10),
    column3 number
);


insert into ex2_1 (column1, column2) values ('abc2', 'def2');

select * from ex2_1;

select column1, length(column1) as len1 from ex2_1;

create table ex2_5 (
    col_date date,
    col_timestamp timestamp
    );
    
insert into ex2_5 values (sysdate, systimestamp);

select * from ex2_5;

create table ex2_6 (
    col_null varchar2(10),
    col_not_null varchar2(10) not null
);

insert into ex2_6 values ('aa', '');

-- 제약조건
create table ex2_7 (
    col_unique_null varchar2(10) unique,
    col_unique_nnull varchar2(10) unique not null,
    col_unique varchar2(10),
    constraints unique_nm1 unique(col_unique)
    );
        
select constraint_name, constraint_type, table_name, search_condition from user_constraints where table_name = 'EX2_8';
insert into ex2_7 values ('aa','aa','aa');
select * from ex2_7;
insert into ex2_7 values ('', 'bb', 'bb');
insert into ex2_7 values ('','cc','cc');

-- 기본키
create table ex2_8 (
    col1 varchar2(10) primary key,
    col2 varchar2(10)
);

select * from ex2_8;
insert into ex2_8 values ('bb','aa');

-- 체크
create table ex2_9 (
    num1 number constraints check1 check (num1 between 1 and 9),
    gender varchar2(10) CONSTRAINTS CHECK2 CHECK (gender in ('m', 'f'))  
);

select constraint_name, constraint_type, table_name, search_condition from user_constraints where table_name = 'EX2_10';
insert into ex2_9 values (2, 'f');
select * from ex2_9;

-- 디폴트
create table ex2_10 (
    col1 varchar2(10) not null,
    col2 varchar2(10) null,
    create_date date default sysdate);
    
insert into ex2_10 (col1, col2) values ('aa','bb');
select * from ex2_10;

-- 컬럼명 변경
alter table ex2_10 rename column col1 to col3;
desc ex2_10;

-- 컬럼 타입 변경
alter table ex2_10 modify col3 varchar2(30);

-- 컬럼 추가/삭제
alter table ex2_10 add col4 number;
alter table ex2_10 drop column col2;

-- 제약조건 추가/삭제
alter table ex2_10 add constraints pk primary key (col3);
alter table ex2_10 drop constraints sys_c007039;

-- 테이블 복사
create table ex2_10_1 as select col3, col4 from ex2_10;
select * from ex2_10_1;

-- 뷰 생성
select * from ex2_9;
create or replace view 연습2_9 as select * from ex2_9;
select * from 연습2_9;

-- 인덱스 생성/삭제
create unique index ex2_10_ix01 on ex2_9(gender);
select index_name, index_type, table_name, uniqueness from user_indexes where table_name = 'EX2_9';
drop index ex2_10_ix01;

-- 시노님 생성/삭제
create or replace synonym syn_channel for channels;

select * from channels;
select * from syn_channel;

alter user hr identified by xy123236 account unlock;
grant select on employees to lee3jjang;
grant select on syn_channel to public;
drop synonym syn_channel;

-- 시퀀스 생성
create sequence my_seq1
increment by 1
start with 1
minvalue 1
maxvalue 1000
nocycle
nocache;

select * from ex2_9;
delete ex2_9;
insert into ex2_9 (num1) values (my_seq1.nextval);
select my_seq1.currval from dual;
drop sequence my_seq1;

-- 파티션 생성
create table sales(
    prod_id number(6,0) not null,
    cust_id number(6,0) not null,
    sales_month varchar2(6),
    amount_sold number(10,2),
    create_date date default sysdate,
    update_date date default sysdate    
)
partition by range(sales_month)
(
    partition sales_q1_1998 values less than ('199804') tablespace oratest,
    partition sales_q2_1998 values less than ('199807') tablespace oratest
);
