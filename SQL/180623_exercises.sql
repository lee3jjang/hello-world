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

