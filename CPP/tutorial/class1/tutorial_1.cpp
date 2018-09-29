#include <iostream>
using namespace std;

class Rectangle {
	int width, height;
public:
	void set_values (int, int);
	int area (void) {
		return width*height;
	};
};

void Rectangle::set_values (int w, int h){
	width = w;
	height = h;
}


int main()
{
	cout << "Hello world!" << endl;
	
	Rectangle rect, rectb;
	
	rect.set_values(4,2);
	rectb.set_values(5,6);
	
	cout << "rect area: " << rect.area() << endl;
	cout << "rectb area: " << rectb.area();
}

