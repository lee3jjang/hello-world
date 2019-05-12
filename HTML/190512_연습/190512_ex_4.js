class Square {
    constructor(height, width){
        this.height = height;
        this.width = width;
    }

    // get 키워드 : 속성처럼 사용 가능
    get area() {
        return this.calcArea();
    }

    calcArea() {
        return this.height * this.width;
    }
}

var foo1 = new Square(1, 2);
var foo2 = new Square(11, 2);

console.log(foo1.area);
console.log(foo2.area);
