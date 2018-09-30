#include <iostream>
#include <array>

using namespace std;

void mulArray(int* x, int* y, int z){
	
	unsigned int size = y-x;
	
	
}

int main(){
	std::array<int, 5> values;
	
	values[0] = 5;
	values[1] = 10;
	values[2] = 1;
	values[3] = 2;
	values[4] = 4;
	values[5] = 77;
	
	cout << values[0] << endl;
	cout << "the begin is : " << values.begin() << endl;
	cout << "the end is : " << values.end() << endl;
	
	cout << "the begin's value is : " << *values.begin() << endl;
	cout << "the end's value is : " << *values.end() << endl;
	cout << "the begin's value + 1 is : " << *values.begin()+1 << endl;
	cout << "the begin's next value is : " << *(values.begin()+1) << endl;
	cout << "the end's next value is : " << *(values.end()+1) << endl;
}
