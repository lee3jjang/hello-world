/*
	open a file : 연관시키는 거
	stream에 의해 표현됨
	즉, 이 클래스들의 한 오브젝트임(f)
	
	입력/출력 연산 이 스트림 오브젝트 위에서 행해지는 거는
	물리적 파일에 적용이 될 것입니다.
	
	파일을 스트림 오브젝트와  같이 열기 위해서, 우리는 멤버함수를 사용합니다.
	mode : 옵션 파라미터임
	
	ios::in : 입력 때문에 여는거
	ios::out : 출력때문에 여는거
	ios::binary : 바이너리모드임
	ios::ate : 초기위치는 파일 맨 끝으로 함, 만약 이거 안 하고 초기 위치가 제일 처음임
	ios::app : 모든 출력 오퍼레이션은 끝에서 이뤄짐 -> appending 시키는거
	ios::trunc : 출력 때문에 열었고, 파일이 존재하면 이전 컨텐츠 잘라냄
	
	
	binary : I/O가 포맷 신경 안 쓰고 독립적으로 연산 이루어짐
	non-binary = text 파일
	번역이 좀 이루어짐 왜냐하면 포맷같은거 특수문자(개행, 캐리지리턴 같은거) 
	
	객체 생성, 스트림 열기 하나의 문장으로
	
	성공적으로 파일스트림이 열렸는지 체크
	is_open : 멤버함수임 
*/

#include <iostream>
#include <fstream>
using namespace std;

int main(){
	ofstream f;
	f.open("example.txt", ios::out|ios::app|ios::binary);
	f << "이 문자를 입력하겠습니다." << endl;
	cout << "파일 열렸니? : " << f.is_open() << endl;
	f.close();
	cout << "파일 열렸니? : " << f.is_open() << endl;
}
