#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main(){
	string line;
	ifstream f("example2.txt");
	// getline : f���� �� �� �̾Ƽ� line���� ����ְ� ���������� ���� ����? 
	// �ؽ�Ʈ���� ���� ������ �������� ��ũ���� ����Ʈ��
	// while ����  ������ line���� ���� getline ������
	// getline�� �����ϴ� �Ŵ� stream object�� ���۷���??
	// ��Ʈ���� ��ü���� ���¸� üũ�ϴ� ����Լ���(�οﰪ ���� ��)
	// bad() : �аų� ���°� �����ϸ� true 
	 
	if(f.is_open()){
		while(getline(f, line))
			cout << line << endl;
		f.close();
	} else
		cout << "���� �� ���ڴ�..";
		
		
	f.open("example3.txt");
	cout << f.bad() << endl;
	
	f.close();
}
