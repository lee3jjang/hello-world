#include <iostream>
#include <string>
#include "BMI.h"

#define PI 3.14

using namespace std;

int main(){
	
	string name;
	int height;
	double weight;
	
	cout << "PI is ..." << PI << endl;
	
	cout << "Enter your name: ";
	cin >> name;
	cout << "Enter your height (in inches): ";
	cin >> height;
	cout << "Enter your weight (in inches): ";
	cin >> weight;
	
	//BMI Student_1(name, height, weight);
	BMI Student;
	
	Student.setName(name);

	cout << "Student's name is... : " << Student.getName() << cout;
	
	return 0;
}
