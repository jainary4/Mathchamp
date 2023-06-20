package backend_design;

public class Matrix {
double[][] matrix;
Matrix(double[][] input){
	this.matrix= input;
}

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
			a++;
	}
		
}
	return d;
}
public double getDeterminant() throws Software_exception {
	return determinant(this.matrix,this.matrix.length);
}

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


private boolean isSymmetric() {
	Matrix other = this.transpose();
	if(this.matrix==other.matrix) {
		return true;
	}
	else {
		return false;
	}
}
