#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main(){
	string line;
	ifstream f("example2.txt");
	// getline : f에서 한 줄 뽑아서 line으로 집어넣고 성공했으면 뭔가 리턴? 
	// 텍스트파일 읽은 다음에 컨텐츠를 스크린에 프린트함
	// while 루프  파일은 line별로 읽음 getline 가지고
	// getline이 리턴하는 거는 stream object의 레퍼런스??
	// 스트림의 구체적인 상태를 체크하는 멤버함수들(부울값 리턴 함)
	// bad() : 읽거나 쓰는거 실패하면 true 
	 
	if(f.is_open()){
		while(getline(f, line))
			cout << line << endl;
		f.close();
	} else
		cout << "파일 못 열겠다..";
		
		
	f.open("example3.txt");
	cout << f.bad() << endl;
	
	f.close();
}
