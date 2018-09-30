#include <iostream>
#include <array>

using namespace std;

int main(){
	array<int, 3> al = {1,2,3};
	int A[] = {4,7,-2};
	
	cout << al[2] << ++al[2] << al[2] << endl;
	
	for(int i:A)
		cout << i << endl;
		
		
	int B[3][2] = {{1,3},{-2,5},{0,8}};
	
	for(int* row:B){
		for(int j=0; j<2; ++j)
			cout << *(row+j) << " ";
		cout << endl;
	}
}
