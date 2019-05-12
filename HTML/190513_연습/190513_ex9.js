const mysql = require('mysql');

//ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'xy123236'

let db = mysql.createConnection({
    host: 'localhost',
    port: '3306',
    user: 'root',
    password: 'xy123236',
    database: 'boards',
    insecureAuth: true
});

//let query = 'create table users(name text, age int)';
//let query = "insert into users values ('sangjin', 30)";
let query = 'select * from users';
db.query(query, (err, data) => {
    console.log(data[0]['name']);
});
