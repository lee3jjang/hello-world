#include <iostream>
using namespace std;

template<class T, int N>
T fixed_mult(T val, T val2){
	return val*N + val2;
}

int main(){
	cout << fixed_mult<int, 3>(10, 2) << endl;
}
