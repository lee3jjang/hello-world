#include <iostream>
#include <string>

using namespace std;

#ifndef BMI_H
#define BMI_H

class BMI {
	
public:
	//Default Constructor
	BMI();
	BMI(string, int, double);
	
	string getName(void);
	int getHeight(void);
	double getWeight(void);
	
private:
	//Member Variables
	string newName;
	int newHeight;
	double newWeight;
	
};

#endif
