#include <iostream>
using namespace std;

class MyClass{
public:
	int x;
	MyClass(int val) : x(val) {}
	const int& get() const {
		cout << "Hello Const!" << endl;
		return x;
	}
	int& get() {return x;}
};

void print(const MyClass& arg){
	cout << arg.get() << endl;
}

int main(){
	int x;
	MyClass foo(10);
	const MyClass bar(20);
	foo.get() = 15;
	cout << foo.get() << endl;
	cout << bar.get() << endl;
	
}
