/*
��°� �Է��� ���Ͽ��� �ϴ°�

ofstream : ���Ͽ� ���� ��Ʈ�� Ŭ����
ifstream : ���Ͽ��� �д� ��Ʈ�� Ŭ����
fstream : �б� ���� �� ��

istream�̶� ostream class���� �� Ŭ�������� �� �Ļ���
cin : istream Ŭ������ ������Ʈ
cout : ostream Ŭ������ ������Ʈ
��� �츮�� ���Ͻ�Ʈ���� �� �� ���� cin �ϵ�?
������ : �������Ѿ� �� �� ��Ʈ������ ���� �����̶� 
*/

#include <iostream>
#include <fstream>
using namespace std;

int main(){
	ofstream f;
	f.open("example.txt");
	f << "�� ���ڸ� �Է��ϰڽ��ϴ�." << endl;
	f.close();
}
