let foo = []
let bar = new Array();
console.log(foo.length);
console.log(bar.length);

foo.push(1);
foo.push("1");
console.log(foo);
console.log('foo의 길이:', foo.length);
console.log('foo를 pop:', foo.pop());
console.log('foo를 pop한 후 남은거:', foo);
foo.push("1");
foo.push('a');

console.log(foo.indexOf(1));
console.log(foo.indexOf('1'));
console.log(foo.indexOf('3'));
console.log(foo);

foo.forEach(function (ite, idx, arr) {
    console.log(ite, idx, arr[idx]);
})

let newFoo = foo.filter(function (item, index, array) {
    if(typeof(item) == 'string'){
        return true
    }
});

console.log(newFoo);