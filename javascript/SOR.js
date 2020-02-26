// 1. Jacobi
function Jacobi(A, b, xi=[0, 0, 0], tol=1e-6, error=1e-10){

    // Check dimension
    var m = A.length;
    var n = A[0].length;
    var l = b.length;
    if((m!=n)||(n!=l))
        throw 'Error: Dimension of A or b is not adequate';

    // Initial value
    var x0 = new Array();
    var x1 = new Array();
    for (i=0; i<m; i++)
        x1[i] = xi[i];

    var i, j;
    // Check diagonal
    for (i=0; i<m; i++)
        if(A[i][i]<error)
            throw 'Error: diagonal is close to zero';

    var norm;
    while(true){
        // x1 → x0 copy
        for (i=0; i<m; i++){
            x0[i] = x1[i];
        }

        // Next
        for (i=0; i<m; i++){
            x1[i] = b[i];
            for (j=0; j<m; j++){
                if (i==j) continue;
                x1[i] -= A[i][j]*x0[j];
            }
            x1[i] /= A[i][i];
        }

        // Norm
        norm = 0;
        for (i=0; i<m; i++){
            norm += Math.pow(x1[i]-x0[i], 2);
        }
        norm = Math.pow(norm, 0.5);

        // Check for convergence
        if (norm<tol)
            break;   
    }

    return x1;
}

// 2. Gauss-Seigel
function GaussSeidel(A, b, xi=[0, 0, 0], tol=1e-6, error=1e-10){

    // Check dimension
    var m = A.length;
    var n = A[0].length;
    var l = b.length;
    if((m!=n)||(n!=l))
        throw 'Error: Dimension of A or b is not adequate';

    // Initial value
    var x0 = new Array();
    var x1 = new Array();
    for (i=0; i<m; i++)
        x1[i] = xi[i];

    var i, j;
    // Check diagonal
    for (i=0; i<m; i++)
        if(A[i][i]<error)
            throw 'Error: diagonal is close to zero';

    var norm;
    while(true){
        // x1 → x0 copy
        for (i=0; i<m; i++){
            x0[i] = x1[i];
        }

        // Next
        for (i=0; i<m; i++){
            x1[i] = b[i];
            for (j=0; j<i; j++)
                x1[i] -= A[i][j]*x1[j];
            for (j=i+1; j<m; j++)
                x1[i] -= A[i][j]*x0[j];
            x1[i] /= A[i][i];
        }

        // Norm
        norm = 0;
        for (i=0; i<m; i++){
            norm += Math.pow(x1[i]-x0[i], 2);
        }
        norm = Math.pow(norm, 0.5);

        // Check for convergence
        if (norm<tol)
            break;
    }

    return x1;
}

// 3. SOR
function SOR(A, b, xi=[0, 0, 0], w=1.5, tol=1e-6, error=1e-10){

    // Check dimension
    var m = A.length;
    var n = A[0].length;
    var l = b.length;
    if((m!=n)||(n!=l))
        throw 'Error: Dimension of A or b is not adequate';

    // Initial value
    var x0 = new Array();
    var x1 = new Array();
    for (i=0; i<m; i++)
        x1[i] = xi[i];

    var i, j;
    // Check diagonal
    for (i=0; i<m; i++)
        if(A[i][i]<error)
            throw 'Error: diagonal is close to zero';

    var norm;
    while(true){
        // x1 → x0 copy
        for (i=0; i<m; i++){
            x0[i] = x1[i];
        }

        // Next
        for (i=0; i<m; i++){
            x1[i] = b[i];
            for (j=0; j<i; j++)
                x1[i] -= A[i][j]*x1[j];
            for (j=i+1; j<m; j++)
                x1[i] -= A[i][j]*x0[j];
            x1[i] /= A[i][i];
        }
        for (i=0; i<m; i++){
            x1[i] = x1[i]*w+x0[i]*(1-w);
        }

        // Norm
        norm = 0;
        for (i=0; i<m; i++){
            norm += Math.pow(x1[i]-x0[i], 2);
        }
        norm = Math.pow(norm, 0.5);

        // Check for convergence
        if (norm<tol)
            break;
    }

    return x1;
}

////////////////////////////////////////////////////////

// Setting
tol = 1e-8;
error = 1e-10;
w = 1.5;

// Ax=b
var A = [[3, -1, 0],
         [-1, 3, -1],
         [0, -1, 3]];
var b = [1, 1, 1];

// Test
x1 = Jacobi(A, b, [0, 0, 0], tol, error);
x2 = GaussSeidel(A, b);
x3 = SOR(A, b, [0, 0, 1]);

console.log(x1);
console.log(x2);
console.log(x3);