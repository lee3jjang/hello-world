#include <iostream>
using namespace std;

class Box{
public:
	static int i;
	Box() {
		i++;
		cout << "������ �⵿!, i=" << i << endl;
		
	}
	~Box() {
		i++;
		cout << "�Ҹ��� �⵿!, i=" << i << endl;
	}
};

int Box::i = 0;

int main(){
	Box* myBoxArray;
	myBoxArray = new Box[4];
	delete[] myBoxArray;
}
