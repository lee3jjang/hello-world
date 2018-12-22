/*
var foo = 'bar1';
console.log(foo);

if(1) {
    var foo = 'bar2';
    console.log(foo);
}

console.log(foo);

//--------------------------------

let foo = 'bar1';
console.log(foo);

if(1) {
    let foo = 'bar2';
    console.log(foo);
}

console.log(foo);

//--------------------------------

var foo = 'bar1';
var foo = 'bar2';

let bar = 'foo1';
let bar = 'foo2';

console.log(foo);
console.log(bar);

//--------------------------------

const foo = 'bar';
console.log(foo);
foo = 4;

//--------------------------------

let foo = 'bar';
foo = 4;
console.log(foo);

//--------------------------------

let foo;
console.log(foo);
foo = NaN;
console.log(foo);

foo = {
    a: 1,
    b: 2,
    c: () => 10
}

console.log(foo.a);
console.log(foo.b);
console.log(foo.c);
console.log(foo.c(4));

foo = [1,2,3,4,5];
console.log(foo);
console.log(foo[0]);
foo.forEach(s => console.log(s));
foo.forEach(s => {
    console.log('출력 : ' + s);
})

console.log(foo.length);

//console.log(foo.findIndex(s => s>=1));
console.log(foo.find(s => s>=1.2));
console.log(foo.includes(7));
//console.log(foo.pop());
//console.log(foo.pop());
//console.log(foo);
//console.log(foo.toString());
let bar = foo.values();

console.log(bar.next().value);
console.log(bar.next().value);
console.log(bar.next().value);
console.log(bar.next().value);
console.log(bar.next().value);
console.log(bar.next().value);

//--------------------------------

console.log(parseInt('1'));
let foo = {a:1, b:2};
let bar = JSON.stringify(foo);
let poo = JSON.parse(bar);
poo = JSON.parse('{"a":1,"b":2}');
console.log(bar);
console.log(poo);

//--------------------------------

let foo = [1,2,3];
let [one, two, three] = foo;
console.log(two);
console.log(one);

foo = {a:1, b:null};
console.log(foo);
let {a, b, c=10} = foo;
console.log(b);

//--------------------------------

class Square{
    constructor(height, width){
        this.height = height;
        this.width = width;
    }

    get area(){
        return this.calcArea();
    }

    calcArea(){
        return this.height * this.width;
    }
}

var foo1 = new Square(1,2);
var foo2 = new Square(11,2);

console.log(foo2.area);

//--------------------------------

const foo1 = require('./ES5');
const foo2 = require('./ES5/index');

let area1 = foo1.area(10);
let area2 = foo2.pi;

console.log(area1);
console.log(area2);

*/

let date = new Date();
console.log(date);
console.log(date.getDate());
console.log(date.getDay());
console.log(date.getFullYear());
console.log(date.getHours());
console.log(date.getMonth());
