#include <iostream>
#include <array>

using namespace std;

void printarray (int arg[], int length){
	for(int i=0; i<length; ++i)
		cout << arg[i] << " ";
	cout << endl;	
}


int main(){
	array<int,3> myarray {10,20,30};
	
	for (int i=0; i<myarray.size(); ++i)
		++myarray[i];
	
	for(int elem:myarray)
		cout << elem << endl;
}
