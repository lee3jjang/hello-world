#include <iostream>

using namespace std;

int main(){
	char* pvalue;
	pvalue = new char[20];
	
	pvalue = "Hello My name is... ";
	cout << pvalue << endl;
	cout << &(pvalue[0]) << endl;
	cout << pvalue[0] << endl;
	cout << &pvalue << endl;
	cout << &pvalue+1 << endl;
	cout << *(&pvalue) << endl << endl;
	
	int* qvalue;
	qvalue = new int[20];
	qvalue[0] = 5;
	qvalue[2] = -5;
	
	cout << qvalue << endl;
	cout << qvalue+1 << endl;
	cout << *(qvalue) << endl;
	cout << *(qvalue+1) << endl;
	cout << *(qvalue+2) << endl;
	cout << &qvalue << endl;
	cout << &qvalue+1 << endl;
	
}


// H�� ����ִ� ���� �ּҸ� ����ϸ� Hello World�� ����
// qvalue : ������
// &�����Ͱ� ��� �ִ� �ּ�
 
