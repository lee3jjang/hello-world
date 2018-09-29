#include <iostream>
using namespace std;

template <class T>
T sum(T a, T b){
	T result;
	result = a+b;
	return result;
}

template <class T, class U>
bool are_equal(T a, U b){
	return a==b;
}


int main(){
	int i=5, j=6, k;
	double f=2.0, g=0.5, h;
	k = sum<int>(i, j);
	h = sum<double>(f, g);
	
	cout << k << endl;
	cout << h << endl;
	
	if (are_equal(10, 10.0))
		cout << "equal";
	else
		cout << "not equal";
}
