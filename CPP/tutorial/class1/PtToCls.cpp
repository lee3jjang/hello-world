#include <iostream>
using namespace std;

class Rectangle {
	int width, height;
public:
	Rectangle(int w, int h) : width(w), height(h) {}
	int area(void) { return width*height; }
};

int main() {
	Rectangle rect(3,4);
	Rectangle *foo, *bar, *baz;
	
	foo = &rect;
	bar = new Rectangle(5,6);
	baz = new Rectangle[2] { {2,5}, {3,6} };
	
	cout << rect.area() << endl;
	cout << foo->area() << endl;
	cout << bar->area() << endl;
	cout << baz[0].area() << endl;
	cout << baz[1].area() << endl;
	cout << (*foo).area() << endl;
}
