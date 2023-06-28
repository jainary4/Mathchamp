package backend_design;

public class Matrix {
double[][] matrix;
Matrix(double[][] input){
	this.matrix= input;
}
/**
 * 
 * @param matrix a double type matrix 
 * @param n : Gives the size of the matrix , as determinant is only possible for square matrices we know its size is nxn
 * @return returns a double type value which is the determinant of the entered matrix
 * @throws Software_exception: Throws an exception if the input matrix is not square
 */

private double determinant(double[][] matrix, int n) throws Software_exception {
	int row = matrix.length;
	int column= matrix[0].length;
	double result=0;
	 if(row==column) {
		if(n==2) {
			result=matrix[0][0]*matrix[1][1]-matrix[1][0]*matrix[0][1];
			return result;
		}
		else {
			for(int i=0;i<n;i++) {
				result= result+ Math.pow(-1, i)*matrix[0][i]*determinant(submatrix(n,0,i,matrix),n-1);
			}
		}
		return result;
	}
	 else {
		 throw new Software_exception("Cannot find determinant of non-square matrix");
	 }
	 
}
/**
 * 
 * @param n the size of the matrix 
 * @param i the row number to cut to find the submatrix 
 * @param j the column number to cut to find the submatrix 
 * @param matrix the original matrix which would be reduced to a submatrix based on values of i , j 
 * @return returns the submatrix of double array type
 **/
private double[][] submatrix(int n, int i, int j, double[][] matrix) {
	int a=0;
	int b=0;
	double[][] d = new double[n-1][n-1];
	for(int p=0;p<n;p++) {
		if(p!=i) {
			for(int q=0;q<n;q++) {
				if(q!=j) {
					d[a][b]= matrix[p][q];
					b++;
			}
		}
		b=0;	
		a++;
	}
   }
	return d;
}
/**
 * This the method that will be visible to the user to get the determinant for the Matrix type object they are using, the above 2 methods are 
 * just the helper method for this method
 * @return a double type value which is the determinant
 * @throws Software_exception throws the determinant exception if Matrix object field is not a square matrix
 */
public double getDeterminant() throws Software_exception {
	return determinant(this.matrix,this.matrix.length);
}
/**
 * Adds 2 Matrix type objects by adding the corresponding elements in the Matrix field
 * @param m the second matrix type object which will be added to the called object 
 *  @return a new matrix whose field is the added double array of the called object and the parameter object
 * @throws Software_exception
 */
public Matrix add(Matrix m) throws Software_exception{
	if(this.matrix.length==m.matrix.length && this.matrix[0].length==m.matrix[0].length) {
		double[][] result = new double[m.matrix.length][m.matrix[0].length];
		for(int i=0;i<m.matrix.length;i++) {
			for(int j=0;j<m.matrix[0].length;j++) {
				result[i][j]= m.matrix[i][j]+this.matrix[i][j];
			}
		}
		Matrix answer = new Matrix(result);
		return answer;
	}
	else {
		throw new Software_exception("The matrices are not equal in size. Can't Add");
	}
	
}
/**
 * subtracts 2 Matrix type objects by subtracting the corresponding elements in the Matrix field
 * @param m the second matrix type object which will be subtracted to the called object 
 *  @return a new matrix whose field is the subtracted double array of the called object and the parameter object
 * @throws Software_exception
 */
public Matrix subtract(Matrix m) throws Software_exception{
	if(this.matrix.length==m.matrix.length && this.matrix[0].length==m.matrix[0].length) {
		double[][] result = new double[m.matrix.length][m.matrix[0].length];
		for(int i=0;i<m.matrix.length;i++) {
			for(int j=0;j<m.matrix[0].length;j++) {
				result[i][j]= m.matrix[i][j]- this.matrix[i][j];
		}
		}
		Matrix answer = new Matrix(result);
		return answer;
	}
	else {
		throw new Software_exception("The matrices are not equal in size. Can't Subtract");
	}
	
	}
/**
 * Gives the transpose of the calling object Matrix
 * @return a new Matrix which is the transpose of the calling object matrix
 */
public Matrix transpose() {
	double[][] result = new double[this.matrix.length][this.matrix[0].length];
	for(int i=0;i<this.matrix.length;i++) {
		for(int j=0;j<this.matrix[0].length;j++) {
			result[i][j]= this.matrix[j][i];
		}
	}
	Matrix answer = new Matrix(result);
	return answer;
}

public Matrix MultiplyByConstant(double n) {
	double[][] result = new double[this.matrix.length][this.matrix[0].length];
	for(int i=0; i<this.matrix.length;i++) {
		for(int j=0;j<this.matrix[0].length;j++) {
			result[i][j]= n*this.matrix[i][j];
		}
	}
	Matrix answer = new Matrix(result);
	return answer;
}
public Matrix MultiplyByMatrix(Matrix m) throws Software_exception{
	int column1= this.matrix[0].length;
	int row1 =  this.matrix.length;
	int column2 = m.matrix[0].length;
	int row2= m.matrix.length;
	if(column1==row2) {
		// can be multiplied
		double array[][] = new double[row1][column2];
		for(int i=0;i<row1;i++) {
			for(int j=0;j<column2;j++) {
				for(int k=0;k<column1;k++) {
					array[i][j]+=this.matrix[i][k]*m.matrix[k][j];
				}
			}
				
		}
		Matrix result= new Matrix(array);
		return result;
	}
	else {
		throw new Software_exception("cant multiply the matrices as the columns of 1st dont match the rows of 2nd")
	}
			
	
}

@SuppressWarnings("unused")
private boolean isSymmetric() {
	Matrix other = this.transpose();
	if(this.matrix==other.matrix) {
		return true;
	}
	else {
		return false;
	}
}
private double minor(int n, int i , int j, double[][] matrix) throws Software_exception {
	double[][] minor = submatrix(n,i,j,matrix);
	Matrix result = new Matrix(minor);
	double answer = result.getDeterminant();
	return answer;
	
}
/**
 * Finds the inverse matrix privately for the internal use and returns a Matrix type object which is the inverse of the calling object
 * @param n the size of the matrix
 * @param matrix a double type array which the field of the calling Matrix type object
 * @return a NEW MATRIX TYPE object which is the inverse of the calling object
 * @throws Software_exception if the determinant is 0 or non-existant
 */
private Matrix inverse(int n, double[][] matrix) throws Software_exception{
  //algorithm to find the inverse:
	// we need the determinant which we have a function for.
	// we need the adjoint matrix to satisfy the equation: A^-1= 1/(det(A))*Adj(A) 
	// the Adj(A)= Cofactor(A)^T => the traspose of Cofactor matrix. 
	// finding the cofactor matrix we need the submatrix or the minor function , as C_ij= (-1)^ij M_ij
	// we can start by taking in the determinant and storing it in a variable and if the determinant is not zero, if the determinant is
	// zero the inverse of the function does not exists. 
	double det = this.getDeterminant();
	if(det!=0) {
		double[][] cofactor_matrix = new double[n][n];
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				cofactor_matrix[i][j]= Math.pow(-1, i*j)*minor(n,i,j,matrix);
			}
		}
		// now we have our cofactor array and by taking its transpose we get the adjoint matrix
		Matrix cofactor = new Matrix(cofactor_matrix);
		Matrix Adjoint = cofactor.transpose();
		Matrix Inverse = Adjoint.MultiplyByConstant(1/det);
		return Inverse;
	}
	else {
		throw new Software_exception("Inverse does not exist as determinant is zero or does not exist");
	}
	

}
public Matrix getInverse() throws Software_exception {
		Matrix answer = this.inverse(this.matrix.length,this.matrix);
		return answer;
}



}
 
