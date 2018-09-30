#include <iostream>
#include <vector>

using namespace std;

int main(){
	
	vector<int> vec;
	int i;
	
	cout << "벡터의 크기 = " << vec.size() << endl;
	
	for(i=0; i<5; i++){
		vec.push_back(i);
	}
	
	cout << "벡터의 크기 = " << vec.size() << endl;
	cout << "벡터 내용 : " << vec[3] << endl;
	
	
	vector<int>::iterator v = vec.begin();
	while(v != vec.end()){
		cout << "내용물 : " << *v << endl;
		v++;
	}
}


// v.push_back(x) : x를 v 맨 뒤에 집어 넣음
// v.size() : v의 size을 출력함
// v.begin() : iterator의 start
// v.end() : iterator의 end 
