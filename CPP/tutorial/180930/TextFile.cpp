/*
�ؽ�Ʈ���� : ios::binary ���� �� �ϰ� �����ϸ� �ؽ�Ʈ ���� ��Ʈ���� ��
�ؽ�Ʈ �����ϰ� ��� ��??
���� ���� �ؽ�Ʈ���ٰ� �ϴ°� 
*/

#include <iostream>
#include <fstream>
using namespace std;

int main(){
	ofstream f("example2.txt");
	if (f.is_open()){
		f << "�̰� �� ���̴�. \n";
		f << "�̰� �� �ٸ� �� ���̴�. \n";
		f.close();
	}
	else
		cout << "Open�� �� �ǿ�.. �Ф�";
	
}
