var mysql = require('mysql');
var conn = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password : 'xy123236',
    database : 'o2'
});

conn.connect();
//conn.query('select 1+1 as solution', (err, rows, fields) => {
//    if (err) throw err;
//    console.log('The solution is:', rows[0].solution);
//    console.log(rows);
//});

var sql = 'insert into topic (title, description, author) values(?,?,?)';
var params = ['Supervisor', 'Watcher', 'graphittie'];
//var sql = 'select * from topic';
conn.query(sql, params,(err, rows, fields) => {
    if (err) throw err;
    console.log(rows.insertId);
});
conn.end();