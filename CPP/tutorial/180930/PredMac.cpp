// __LINE__ : ���� ���� ���� ���α׷��� ������ �ǰ� ���� ��
// __FILE__ : ���� ���� �̸� ���α׷��� ������ �� ��


#include <iostream>
using namespace std;

int main(){
	
	char* str = __TIME__;
	cout << __LINE__ << endl;
	cout << __FILE__ << endl;
	cout << __DATE__ << endl;
	cout << __TIME__ << endl;
	cout << str[0] << endl;
} 
