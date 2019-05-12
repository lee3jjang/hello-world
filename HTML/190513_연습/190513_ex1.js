const http = require('http');
const express = require('express');
const bodyParser = require('body-parser');

let app = express();
app.use(bodyParser());


app.get('/', (req, res, next) => {
    res.send('hello world!!');
});

app.post('/', (req, res, next) => {
    let body = req.body;
    console.log(body);
    res.send('/ POST 요청');
});

app.get('/users', (req, res, next) => {
    res.send('/users GET 요청');
});

app.post('/users', (req, res, next) => {
    res.send('/users POST 요청');
});

app.get('/user/:id', (req, res, next) => {
    let params = req.params;
    let querys = req.query;
    console.log(params, querys);
})

http.createServer(app).listen(3000, () => {
    console.log('server on : 3000 port');
});