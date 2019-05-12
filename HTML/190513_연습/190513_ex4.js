const express = require('express');
const http = require('http');

let exec = require('child_process').exec;
let app = express();

app.get('/', (req, res, next) => {
    res.send('Hello World~');
})

app.get('/run', (req, res, next) => {
    let script = 'start ./쓰레기통/test.txt';

    exec(script, (error, stdout, stderr) => {
        console.log('stdout : %s', stdout);
        console.log('stderr : %s', stderr);
        if(error != null){
            console.log('error : %s', error);
        }
    });
});



http.createServer(app).listen(3000, () => {
    console.log('server on : port 3000');
});