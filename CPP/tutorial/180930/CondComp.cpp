/* ��� ��ħ�� �־��. �� ��ħ���� �������� �κ��� �������ؿ�. �� �ҽ��ڵ带. �� ���μ����� ���Ǻ� �������̶�� �ҷ���
���Ǻ� �������μ��� construct�� if�� ����ؿ�

cerr ������ �����ϵǵ��� �߱���ѿ� �� ���α׷����� ���� �ɺ��� ��� DEBUG�� ���ǵǾ� �־�����  
*/


#include <iostream>
using namespace std;
#define DEBUG

#define MIN(a,b) ((a<b)? 100:-100)

int main(){
	int i, j;
	
	i=100;j=30;
	
#ifdef DEBUG
	cerr << "Ʈ���̽�: �����Լ� ����" << endl;
#endif

#if 0
	cout << MKSTR(HELLO C++) >> end;
#endif

	cout << "�ּҰ� : " << MIN(i,j) << endl;
	
#ifdef DEBUG
	cerr << "Ʈ���̽�: �����Լ� ����" << endl;
#endif

	
} 
