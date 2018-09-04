#include "BMI.h"

BMI::BMI() {
	cout << "This is constructor without arguments" << endl;
}


BMI::BMI(string x, int y, double z) {
	cout << "This is constructor with arguments" << endl;
	newName = x;
	newHeight = y;
	newWeight = z;
}


string BMI::getName() {
	return this->newName;
}
