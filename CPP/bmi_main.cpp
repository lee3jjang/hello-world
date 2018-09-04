#include <iostream>
#include <string>
#include "BMI.h"

using namespace std;
/* run this program using the console pauser or add your own getch, system("pause") or input loop */

int main(int argc, char** argv) {
	string name;
	int height;
	double weight;
	
	cout << "Enter your name: ";
	cin >> name;
	cout << "Enter your height (in inches): ";
	cin >> height;
	cout << "Enter your weight (in inches): ";
	cin >> weight;
	
	BMI Student(name, height, weight);
	//BMI Student;
	cout << "Student's name is... : " << Student.getName() << endl;
	
	return 0;
}
