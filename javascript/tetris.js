
// object를 넣으면 clone을 return 함
function clone(obj) {
    return JSON.parse(JSON.stringify(obj));
}

// min <= i < max 인 정수 i를 랜덤으로 추출함
// 진짜 난수는 아니고 의사난수임
var rndSeed = 1;
function seededRandom(min, max) {
    max = max || 1;
    min = min || 0;

    rndSeed = (rndSeed * 9301 + 49297) % 233280;
    var rnd = rndSeed / 233280;

    return Math.floor(min + rnd * (max - min));
}

// min <= i <- max 인 정수 i를 랜덤으로 추출함
function randomNumBetween(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}

// 낮은 값이 더 잘 나오도록 조절된 정수 난수임
function randomWeightedNumBetween(min, max) {
    return Math.floor(Math.pow(Math.random(), 2) * (max - min + 1) + min);
}

// object 둘 중에 하나 랜덤으로 선택
function randomChoice(propOne, propTwo) {
    if (Math.round(Math.random()) === 0) {
        return clone(propOne);
    } else {
        return clone(propTwo);
    }
}

// a 안에 obj가 있는지 검사하기
function contains(a, obj) {
    var i = a.length;
    while (i--) {
        if (a[i] === obj) {
            return true;
        }
    }
    return false;
}

// object를 인자로 전달하면 그 중에서 랜덤으로 하나를 골라 그 key값을 리턴함
function randomKey(obj) {
    var keys = Object.keys(obj);
    var i = seededRandom(0, keys.length);
    return keys[i];
}

// object를 인자로 전달하면 그 중에서 랜덤으로 value 를 하나로 골라 리턴함
function randomProperty(obj) {
    return(obj[randomKey(obj)]);
}

//search라는 regex를 target에서 찾은 다음에 그걸 replacement로 모두 치환
function replaceAll(target, search, replacement) {
    return target.replace(new RegExp(search, 'g'), replacement);
}


// 현재 블록
var currentShape = {x: 0, y:0, shape: undefined};

// 블록의 모양
var shapes = {
	I: [[0,0,0,0], [1,1,1,1], [0,0,0,0], [0,0,0,0]],
	J: [[2,0,0], [2,2,2], [0,0,0]],
	L: [[0,0,3], [3,3,3], [0,0,0]],
	O: [[4,4], [4,4]],
	S: [[0,5,5], [5,5,0], [0,0,0]],
	T: [[0,6,0], [6,6,6], [0,0,0]],
	Z: [[7,7,0], [0,7,7], [0,0,0]]
};

// 그리드
var grid = [
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0,0,0],
    ];


// currentShape에 있는 정보 : 현재 위치(x,y) + shape
// grid에 currentShape를 각인
function applyShape() {
    //for each value in the current shape (row x column)
    for (var row = 0; row < currentShape.shape.length; row++) {
        for (var col = 0; col < currentShape.shape[row].length; col++) {
            //if its non-empty
            if (currentShape.shape[row][col] !== 0) {
                //set the value in the grid to its value. Stick the shape in the grid!
                grid[currentShape.y + row][currentShape.x + col] = currentShape.shape[row][col];
            }
        }
    }
}

// grid에 있는 currentShape를 제거
function removeShape() {
    for (var row = 0; row < currentShape.shape.length; row++) {
        for (var col = 0; col < currentShape.shape[row].length; col++) {
            if (currentShape.shape[row][col] !== 0) {
                grid[currentShape.y + row][currentShape.x + col] = 0;
            }
        }
    }
}
