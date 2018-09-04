#ifndef CPP_MATRIX_OPERATIONS_MATINT_H
#define CPP_MATRIX_OPERATIONS_MATINT_H

namespace MatOps {
	
	class MatInt {
	private:
		int rows = 0;
		int cols = 0;
		int** mat;
		
	public:
		
		MatInt(int rows, int cols) : rows(rows), cols(cols) {
			this->mat = generateMatrix(this->rows, this->cols);
		};
		~MatInt();
		
		int** generateMatrix(int, int);
		void populateMatrix(int*, int);
		void print();
		void trap();
		void add(MatInt&);
		void sub(MatInt&);
		void mul(MatInt&);
		void div(MatInt&);
		
		int getValueAt(int, int);
		int getNumRows();
		int getNumCols();
	
	};
}

#endif
