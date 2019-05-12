const fs = require('fs');
const p = (val) => {
    return new Promise((resolve, reject) => {
        fs.readFile('./test.txt', (err, data) => {
            if(err) reject(err);
            resolve(`${val} : ${data.toString()}`);
        });
    });
};

async function myp(){
    var data1 = await p(1);
    var data2 = await p(2);
    var data3 = await p(3);
    console.log(data1, data2, data3);
}

myp();