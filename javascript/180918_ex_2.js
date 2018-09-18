class Matrix{
    constructor(elements){
        this.elements = elements;
        this.nrow = this.elements.length;
        this.ncol = this.elements[0].length;
    }

    add(B){
        let n = this.nrow;
        let m = this.ncol;
        let C = zeros(n, m); 

        for(var i=0; i<n; ++i){
            for(var j=0; j<m; j++){
                C[i][j] = this.elements[i][j] + B.elements[i][j];
            }
        }

        return new Matrix(C);
    }
}

zeros = (n, m) => {
    let A = [];
    
    for(var i=0; i<n; ++i){
        var temp = [];
        for(var j=0; j<m; j++){
            temp.push(0);
        }
        A.push(temp);
    }

    return A;
};

identity = (n) => {
    let A = zeros(n, n);
    for(var i=0; i<n; ++i){
        A[i][i] = 1;
    }

    return A;
};

rands = (n, m) => {
    let A = zeros(n, m);
    for(var i=0; i<n; ++i){
        for(var j=0; j<m; ++j){
            A[i][j] = Math.random();
        }
    }

    return A;
};

let A = new Matrix([[1,2,3],
                    [2,3,2],
                    [4,4,2]]);

let B = new Matrix([[1,2,3],
                    [2,3,2],
                    [4,4,2]]);                    

console.log(A.add(B).elements);
console.log(identity(3));
console.log(rands(4,2));

