const express = require('express');
const http = require('http');

let app = express();

app.get('/send', (req, res, next) => {
    res.send('<h1>Hello World!</h1>');
    //res.send('hello world!');
});

app.get('/download', (req, res, next) => {
    res.download('./test.txt');
});

app.get('/redirect', (req, res, next) => {
    res.redirect('/send');
});

app.get('/json', (req, res, next) => {
    res.status(201).json({message: 'success', code: 0});
});

http.createServer(app).listen(3000, () => {
    console.log('server on : 3000 port');
});