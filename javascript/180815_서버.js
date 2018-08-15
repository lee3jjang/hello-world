const http = require('http');

const hostname = '127.0.0.1';
const port = 1234;

http.createServer((req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Hello World!\n');
}).listen(port, hostname, () => {
    console.log(`서버가 여기서 열렸습니다. http://${hostname}:${port}/`);
});