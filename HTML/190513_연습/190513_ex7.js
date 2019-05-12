const fs = require('fs');

fs.readFile('./test.txt', (err, data) => {
    console.log(1);
    console.log(data.toString());

    fs.readFile('./test.txt', (err, data) => {
        console.log(2);
        console.log(data.toString());

        fs.readFile('./test.txt', (err, data) => {
            console.log(3);
            console.log(data.toString());
        });
    });
});

console.log('test');