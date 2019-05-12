// 1. parseInt
console.log(parseInt('1'));

// 2. JSON.stringify
let foo = {a: 1, b:2};
console.log(JSON.stringify(foo));

// 3. JSON.parse
let boo = '{"a":1, "b":2}';
console.log(JSON.parse(boo));

// 4. Destructuring Array
let foo4 = [1, 2, 3];
let [one, two, three] = foo4;
console.log(one);

// 5. Destructuring Object
let foo5 = {a: 1, b: null};
let {a, X, y=10} = foo5;
console.log(a);
console.log(X);
console.log(y);

// 6. if
if (false){
    console.log("Hello True!");
} else if(false){
    console.log("Hello second true!");
} else {
    console.log("Hello False!");
}

// 7. for
for(let i=0; i<3; i++){
    console.log(i + "입니다!");
}

// 8. for ~ in
let l = [1, 5, 2];
for(idx in l){
    console.log(idx, l[idx]);
}
let m = {a: 45, b: 22, c:-23};
for(idx in m){
    console.log(idx, m[idx]);
}