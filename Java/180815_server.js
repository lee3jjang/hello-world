var express = require('express');
var bodyParser = require('body-parser');
var fs = require('fs');

var app = express();
app.locals.pretty = true;
app.set('view engine', 'jade');
app.set('views', './viewsa');
app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended: false }));

app.get('/topic/:id', (req, res) => {
    var id = req.params.id;
    fs.readdir('data', (err, files) => {
        if(err){
            console.log(err);
            res.status(500).send('내부 서버 오류');
        };
        fs.readFile('data/'+id, 'utf8', (err, data) => {
            if(err){
                console.log(err);
                res.status(500).send('내부 서버 오류');
            };
            res.render('view', {topics:files, title:id, desc:data});
        });
    });
});

app.get('/topic/:id/:mode', (req, res) => {
    var topics = [
        'Javascript is...',
        'Python is...',
        'R is...'
    ];

    var output = `
        <a href="/topic/0">자바스크립트 버튼</a><br>
        <a href="/topic/1">파이썬 버튼</a><br>
        <a href="/topic/2">R 버튼</a><br>
        ${topics[req.params.id]}<br>
        <p> ${req.params.mode} </p>
    `;

    res.send(output);
});

app.post('/topic', (req, res) => {
    var title = req.body.title;
    var desc = req.body.desc;
    fs.writeFile('data/'+title, desc, (err) => {
        if(err){
            console.log(err);
            res.status(500).send('내부 서버 오류');
        };
        res.send('안녕 포스트! => ' + title);
    });
});

app.get('/form', (req, res) => {
    res.render('from');
});

app.get('/temp', (req, res) => {
    res.render('index', {time:req.query.title, title:req.query.desc});
    console.log('겟으로 들어옴');
});

app.post('/temp', (req, res) => {
    res.render('index', {time:req.body.title, title:req.body.desc});
    console.log('포스트로 들어옴');
});

app.get('/route', (req, res) => {
    res.send('라우터 안녕~~! <img src="/test.jpg">');
});

app.get('/dynamic', (req, res) => {
    var lis = '';

    for(var i=0; i<5; i++){
        lis = lis + '<li>coding</li>';
    }

    var time = Date();

    var output = `
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <title>상진이의 홈페이지</title>
        </head>
        <body>
            Hello Static!
            <ul>
                ${lis}
            </ul>
            ${time}
        </body>
    </html>`
    res.send(output);
});

app.get('/', (req, res) => {
    res.send('Hello World!');
});

app.get('/login', (req, res) => {
    res.send('로그인 해주세요');
    console.log('로그인 페이지로 누군가 들어 왔습니다.');
});

app.listen(3000, () => {
    console.log('실행이 성공했습니다.');
});

