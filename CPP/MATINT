#include <iostream>
#include "MatInt.h"

using namespace std;



int main(int argc, char** argv) {
	
	// 1st matrix
	int srcA[] = {1,2,3,4,5,6};
	MatOps::MatInt A(2,3);
	A.populateMatrix(srcA, 6);
	
	// 2nd matrix
	int srcB[] = {6,5,4,3,2,1};
	MatOps::MatInt B(3,2);
	B.populateMatrix(srcB, 6);
	
	A.print();
	cout << endl;
	B.print();
	cout << endl;
	A.add(B);
	A.print();
	
	return 0;
}
