#include <iostream>
using namespace std;

class CVector{
public:
	int x,y;
	CVector() {};
	CVector (int a, int b) : x(a), y(b) {}
	CVector operator + (const CVector&);
	CVector& operator= (const CVector&);
};

CVector& CVector::operator= (const CVector& param){
	x = param.x;
	y = param.y;
	return *this;
}

CVector CVector::operator + (const CVector& param){
	CVector temp;
	temp.x = x + param.x;
	temp.y = y + param.y;
	return temp;
}

CVector operator*(const double a, const CVector& rhs){
	CVector temp;
	temp.x = a*rhs.x;
	temp.y = a*rhs.y;
	return temp;
}

int main(){
	CVector foo(3,1);
	CVector bar(1,2);
	CVector add;
	CVector scalar;
	CVector assign;
	
	add = foo + bar;
	scalar = 4*foo;
	assign = scalar;
	scalar = 4*scalar;
	
	cout << "(" << add.x << "," << add.y << ")"<< endl;
	cout << "(" << scalar.x << "," << scalar.y << ")"<< endl;
	cout << "(" << assign.x << "," << assign.y << ")"<< endl;
	
	return 0;
}
