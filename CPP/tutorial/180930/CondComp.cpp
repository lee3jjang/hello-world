/* 몇개의 지침이 있어요. 이 지침들은 선택적인 부분을 컴파일해요. 니 소스코드를. 이 프로세스를 조건부 컴파일이라고 불러요
조건부 프리프로세서 construct는 if랑 비슷해요

cerr 문잘을 컴파일되도록 야기시켜요 이 프로그램에서 만약 심볼릭 상수 DEBUG가 정의되어 있었으면  
*/


#include <iostream>
using namespace std;
#define DEBUG

#define MIN(a,b) ((a<b)? 100:-100)

int main(){
	int i, j;
	
	i=100;j=30;
	
#ifdef DEBUG
	cerr << "트레이스: 메인함수 안임" << endl;
#endif

#if 0
	cout << MKSTR(HELLO C++) >> end;
#endif

	cout << "최소값 : " << MIN(i,j) << endl;
	
#ifdef DEBUG
	cerr << "트레이스: 메인함수 나옴" << endl;
#endif

	
} 
