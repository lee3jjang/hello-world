#include <iostream>

#define ARRAYLEN(arr) sizeof(arr)/sizeof(arr[0])

using namespace std;

int main()
{
	int a[10] = {1,2,3,4,5,6,7,8,9,10};
	
	int s1 = sizeof(a);
	int s2 = sizeof(a[0]);
	
	int n = ARRAYLEN(a);
	
	for (int i=0; i<n; ++i){
		cout << "a[" << i << "] = " << a[i] << endl; 
	}
	
	cout << s1 << endl;
	cout << s2 << endl;
}
