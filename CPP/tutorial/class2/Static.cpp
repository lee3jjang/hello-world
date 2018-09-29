#include <iostream>
using namespace std;

class Dummy{
	int x;
public:
	static int n;
	static void f(int);
	Dummy(){ 
		n++; 
	};
};

int Dummy::n=0;
void Dummy::f(int x){cout << "f is called " << x << endl;};

int main(){
	Dummy a;
	Dummy b[5];
	cout << a.n << endl;
	Dummy* c = new Dummy;
	cout << Dummy::n << endl;
	Dummy::f(4);
	delete c;
}
