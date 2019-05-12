let foo = {'a': 10, 'b': 3};
//let bar = new Object({'c': 20, 'd': -4});
let bar = new Object({'a': 20, 'd': -4});
console.log(foo);
console.log(bar);
console.log(foo.hasOwnProperty('a'), bar.hasOwnProperty('e'));

function myfun(x, y, z) {
    console.log(x, y, z, "입니다");
}

let l = [5,2,3];
myfun(...l);

let m = [...l, -2, -4, 0];
console.log(m);

let n = [-10, ...l, -55];
console.log(n);

let boo = { ...bar, ...foo, c: 30};
console.log(boo);

function kk(){
    console.log('foo');
}
kk();

const kk2 = function(){
    console.log('foo2');
}
kk2();

const kk3 = () => {
    console.log('kk3');
}
kk3();

const kk4 = () => {
    return 'javascript';
}
let rst = kk4();
console.log(rst);

const kk5 = () => (
    'javascript2'
);
console.log(kk5());
