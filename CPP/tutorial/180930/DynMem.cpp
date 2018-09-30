/* 동적메모리가 어떻게 동작하는지 아는게 매우 중요함
C++ 프로그램에서 메모리 는 2개로 나뉘어져
스택 : 함수 안에서 선언된 모든 변수들은 스택에서 메모리를 차지함
힙 : 사용되지 않는 메모리임 -> 할당될 수 있음 메모리가 동적으로
많은시간 알 수 없어 사전에 얼마나 많은 메모리가 필요한지 특정한 정보를 저장하기 위해 정의된 변수에 그리고 메모리의 크기는 런타임때 정해짐
할당되 메모리는 런타임에서 힙에다가 주어진 타입의 변수를 위해서 특별한 operator를 통해서
그게 new 오퍼레이터야
만약 동적으로 할당된 메모리가 더이상 필요해지지 않으면 delete 오퍼레이터를 사용할 수 있어
메모리 해제하는거야

double* pvalue = NULL; // 
pvalue = new double;


*/

#include <iostream>

using namespace std;


int main(){
	double* pvalue;
	pvalue = new double;
	*pvalue = 3;
	*(pvalue+1) = 7;
	cout << pvalue << " ";
	cout << *pvalue << endl;
	cout << pvalue+1 << " ";
	cout << *(pvalue+1) << endl << endl;
	
	delete pvalue;
	cout << pvalue << endl;
	cout << *(pvalue+1) << endl;
}
