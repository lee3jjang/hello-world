const http = require('http');

http.createServer((req, res) => {
    let {url, method} = req;
    let body = {};
    console.log(url, method);

    req.on('data', (data) => {
        console.log(data);
        console.log(data.toString());
        data.toString().split('&').map(item => {
            let s = item.split('=');
            let key = s[0];
            let value = s[1];
            body[key] = value;
        })
    }).on('end', () => {
        console.log(body)
    });

    let resData = '<html><body><h1>!!!!!hello world!!!!!</h1></body></html>';
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end(resData);
}).listen(3000, () => { 
    console.log('server on : 3000 port');
});