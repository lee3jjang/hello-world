const mysql = require('mysql');

let db = mysql.createConnection({
    host: '127.0.0.1',
    port: '3306',
    user: 'root',
    password: '1234',
    database: 'users'
});

db.query('SELECT * FROM info', (err, data) => {
    if(err) console.log('err 발생 : ' + err);
    else console.log(data);
});

db.query('INSERT INTO info(name, age) values("상진", 30)');

//alter user 'root'@'localhost' identified with mysql_native_password by '1234';