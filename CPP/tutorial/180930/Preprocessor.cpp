/* 프리프로세서는 지시들을 말합니다. 지침을 컴파일러한테 줍니다. 전처리를 하라고 정보를 실제 컴파일 시작하기 전에
모든 프리프로세서 지시들은 #으로 시작합니다. 그리고 화이트스페이즈 문자나 나타날 수 있습니다 한 라인에
프리프로세서는 C++ 문장이 아니어서 세미콜론으로 끝나지 않습니다ㅣ
#include 지침을 모든 예제에서 볼 수 있는데
이 매크로는 헤더파일을 소스 파일에 포함하다는 뜻입니다
다른 프리프로세서 지침, C++에 의해 서포팅되는게 있어요
#inlcude
#define
#if
#else
#line
등등

#define
컨스턴트 만들어줍니다
#define 매크로이름 대체할텍스트

전처리 이 코드를
리다이텍트를 해요 p로
 

*/

#include <iostream>
using namespace std;

#define PI 3.14159 
#define MIN(a,b) ((a<b)? 100:-100)

int main(){
	cout << "Pi 값 : " << PI << endl;
	int x=4, y=2;
	
	cout << MIN(x,y) << endl; 
} 
