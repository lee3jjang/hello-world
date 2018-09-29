#include <iostream>
using namespace std;

class Dummy {
public:
	bool istime(Dummy&);
};

bool Dummy::istime(Dummy& param){
	if (&param == this) return true;
	else return false;
}


int main(){
	Dummy a;
	Dummy* b = &a;
	if(b->istime(a))
		cout << "Yes, &a is b";
}
