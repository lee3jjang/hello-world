#include <iostream>
using namespace std;

class Rectangle{
	int width, height;
public:
	Rectangle(int, int);
	int area() {
		return width*height;
	}
};

Rectangle::Rectangle(int w, int h) : width(w), height(h) {
}


int main(){
	Rectangle rect(4,2);
	cout << rect.area();
}
