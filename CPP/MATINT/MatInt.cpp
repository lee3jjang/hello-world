#include <iostream>
#include "MatInt.h"

using namespace std;

MatOps::MatInt::~MatInt() {
	for (int i=0; i<this->rows; i++){
		delete[] this->mat[i];
	}
	
	delete[] this->mat;
}
	
int** MatOps::MatInt::generateMatrix(int rows, int cols) {
	int** temp = new int*[rows];
	
	for (int i=0; i<rows; i++){
		temp[i] = new int[cols];
	}
	
	return temp;
}

void MatOps::MatInt::print() {
	for (int i=0; i<rows; i++){
		for (int j=0; j<cols; j++){
			cout << this->mat[i][j] << " ";
		}
		cout << endl;
	}
}

void MatOps::MatInt::trap() {
	int **temp = generateMatrix(this->cols, this->rows);
	
	for (int i=0; i<this->cols; i++){
		for (int j=0; j<this->rows; j++){
			temp[i][j] = this->mat[j][i];
		}
	}
	
	for (int i=0; i<this->rows; i++){
		delete[] this->mat[i];
	}
	//delete[] this->mat;
	
	this->mat = temp;
	
	int tmp = this->rows;
	this->rows = this->cols;
	this->cols = tmp;
}

void MatOps::MatInt::add(MatInt &m) {
	
	if(this-> rows != m.getNumRows() || this->cols != m.getNumCols()){
		cout << "Matrices sould be of same size (rows and columns)" << endl;
		exit(-1);
	}
	
	for (int i=0; i<this->rows; i++){
		for (int j=0; j<this->cols; j++){
			this->mat[i][j] += m.getValueAt(i,j);
		}
	}
}

int MatOps::MatInt::getValueAt(int i, int j) {
	return this->mat[i][j];
}

int MatOps::MatInt::getNumRows() {
	return this->rows;
}

int MatOps::MatInt::getNumCols() {
	return this->cols;
}

void MatOps::MatInt::populateMatrix(int *src, int size) {
	if(rows*cols != size){
		cout << "size of matrix is not equal size of source array!" << endl;
		exit(-1);
	}
	
	int pos = 0;
	for (int i=0; i<rows; i++){
		for (int j=0; j<cols; j++){
			mat[i][j] = src[pos++];
		}
	}
}
