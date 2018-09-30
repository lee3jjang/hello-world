/*
텍스트파일 : ios::binary 포함 안 하고 오픈하면 텍스트 파일 스트림이 됨
텍스트 저장하고 모든 값??
쓰기 연산 텍스트에다가 하는거 
*/

#include <iostream>
#include <fstream>
using namespace std;

int main(){
	ofstream f("example2.txt");
	if (f.is_open()){
		f << "이건 한 줄이다. \n";
		f << "이건 또 다른 한 줄이다. \n";
		f.close();
	}
	else
		cout << "Open이 안 되요.. ㅠㅠ";
	
}
