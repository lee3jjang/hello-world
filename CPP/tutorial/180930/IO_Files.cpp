/*
출력과 입력을 파일에서 하는거

ofstream : 파일에 쓰는 스트림 클래스
ifstream : 파일에서 읽는 스트림 클래스
fstream : 읽기 쓰기 다 됨

istream이랑 ostream class에서 이 클래스들이 다 파생됨
cin : istream 클래스의 오브젝트
cout : ostream 클래스의 오브젝트
사실 우리는 파일스트림을 쓸 수 있음 cin 하듯?
차이점 : 연관시켜야 함 이 스트림들을 실제 파일이랑 
*/

#include <iostream>
#include <fstream>
using namespace std;

int main(){
	ofstream f;
	f.open("example.txt");
	f << "이 문자를 입력하겠습니다." << endl;
	f.close();
}
