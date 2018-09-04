#include <iostream>
#include <string.h>

int main()
{
	
	using namespace std;
	
	// Example 1 
	// Allocate a 1-dimensional array of 3 integers
	int* ipArray = new int[3];
	
	// Fill the array
	for (int i=0; i<3; ++i)
		ipArray[i] = i;
		
	// Output the array
	for (int i=0; i<3; ++i)
		cout << ipArray[i] << " ";
	cout << endl;
	
	// Deallocate the array
	delete [] ipArray;
	
	// Keep the window open
	cin.get();
	
	// Example 2
	// Allocate a 2-dimensional 3x2 array of integers
	int** ippArray = new int*[3];
	for (int i=0; i<3; ++i)
		ippArray[i] = new int[2];
	
	// Fill the array
	for (int i=0; i<3; ++i)
		for (int j=0; j<2; ++j)
			ippArray[i][j] = i+j;
	
	// Output the array
	for (int i=0; i<3; ++i){
		for (int j=0; j<2; ++j)
			cout << ippArray[i][j] << " ";
		cout << endl;
	}
	cout << endl;
	
	// Deallocate the array
	for (int i=0; i<3; ++i)
		delete[] ippArray[i];
	delete[] ippArray;
	
	// Example 3
	// Allocate a 2d array with varying row length
	char** cppStrings = new char*[3];
	cppStrings[0] = new char[9];
	cppStrings[1] = new char[13];
	cppStrings[2] = new char[4];
	
	// Fill the array with strings
	strcpy(cppStrings[0], "XoaX.net");
	strcpy(cppStrings[1], "Michael Hall");
	strcpy(cppStrings[2], "C++");
	
	for (int i=0; i<3; ++i)
		cout << cppStrings[i] << endl;
	cout << endl;
	
	// Example 4
	// Allocate a 3d 3x2x4 array of integers
	int*** ipppArray = new int**[3];
	for (int i=0; i<3; ++i){
		ipppArray[i] = new int*[2];
		for (int j=0; j<2; ++j)
			ipppArray[i][j] = new int[4];
	}
	
	// Fill the array
	for (int i=0; i<3; ++i)
		for (int j=0; j<2; ++j)
			for (int k=0; k<4; ++k)
				ipppArray[i][j][k] = i+j+k;
	
	// Output the array
	for (int i=0; i<3; ++i){
		for (int j=0; j<2; ++j){
			for (int k=0; k<4; ++k)
				cout << ipppArray[i][j][k] << " ";
			cout << endl;
		}
		cout << endl;
	}
	cout << endl;
	
	
	return 0;
}
