#include <iostream>
using namespace std;

class Box{
public:
	static int i;
	Box() {
		i++;
		cout << "생성자 출동!, i=" << i << endl;
		
	}
	~Box() {
		i++;
		cout << "소멸자 출동!, i=" << i << endl;
	}
};

int Box::i = 0;

int main(){
	Box* myBoxArray;
	myBoxArray = new Box[4];
	delete[] myBoxArray;
}
