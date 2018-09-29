#include <iostream>
using namespace std;

class Circle{
	double radius;
public:
	Circle(double r){
		radius = r;
	}
	double circum() {
		return 2*radius*3.14159265;
	}
};

int main() {
	//Circle foo(10);
	Circle foo = 20;
	cout << "Circumference : " << foo.circum() << endl;
}
