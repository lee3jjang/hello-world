// __LINE__ : 현재 라인 숫자 프로그램에 컴파일 되고 있을 때
// __FILE__ : 현재 파일 이름 프로그램의 컴파일 될 때


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
