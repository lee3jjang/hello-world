/* �����޸𸮰� ��� �����ϴ��� �ƴ°� �ſ� �߿���
C++ ���α׷����� �޸� �� 2���� ��������
���� : �Լ� �ȿ��� ����� ��� �������� ���ÿ��� �޸𸮸� ������
�� : ������ �ʴ� �޸��� -> �Ҵ�� �� ���� �޸𸮰� ��������
�����ð� �� �� ���� ������ �󸶳� ���� �޸𸮰� �ʿ����� Ư���� ������ �����ϱ� ���� ���ǵ� ������ �׸��� �޸��� ũ��� ��Ÿ�Ӷ� ������
�Ҵ�� �޸𸮴� ��Ÿ�ӿ��� �����ٰ� �־��� Ÿ���� ������ ���ؼ� Ư���� operator�� ���ؼ�
�װ� new ���۷����;�
���� �������� �Ҵ�� �޸𸮰� ���̻� �ʿ������� ������ delete ���۷����͸� ����� �� �־�
�޸� �����ϴ°ž�

double* pvalue = NULL; // 
pvalue = new double;


*/

#include <iostream>

using namespace std;


int main(){
	double* pvalue;
	pvalue = new double;
	*pvalue = 3;
	*(pvalue+1) = 7;
	cout << pvalue << " ";
	cout << *pvalue << endl;
	cout << pvalue+1 << " ";
	cout << *(pvalue+1) << endl << endl;
	
	delete pvalue;
	cout << pvalue << endl;
	cout << *(pvalue+1) << endl;
}
