#include <iostream>
#include <vector>

using namespace std;

int main(){
	
	vector<int> vec;
	int i;
	
	cout << "������ ũ�� = " << vec.size() << endl;
	
	for(i=0; i<5; i++){
		vec.push_back(i);
	}
	
	cout << "������ ũ�� = " << vec.size() << endl;
	cout << "���� ���� : " << vec[3] << endl;
	
	
	vector<int>::iterator v = vec.begin();
	while(v != vec.end()){
		cout << "���빰 : " << *v << endl;
		v++;
	}
}


// v.push_back(x) : x�� v �� �ڿ� ���� ����
// v.size() : v�� size�� �����
// v.begin() : iterator�� start
// v.end() : iterator�� end 
