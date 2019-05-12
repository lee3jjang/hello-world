const express = require('express');
const http = require('http');
let app = express();

let firstMiddleware = (req, res, next) => {
    console.log('첫 번째 미들웨어');
    next();
};

app.get('/', firstMiddleware, (req, res, next) => {
    res.send('hello world');
});

http.createServer(app).listen(3000, () => {
    console.log('server on : 3000 port');
});