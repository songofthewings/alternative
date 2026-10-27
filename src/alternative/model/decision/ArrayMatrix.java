/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

/**
 *
 * @author jiraffa
 */
public class ArrayMatrix {

    /** Creates a new instance of Matrix */
    public ArrayMatrix() {
    }

    private double[][] matr;
    private int rows;
    private int cols;
    public ArrayMatrix(int rows, int cols){
    	matr = new double[rows][cols];
    	this.rows = rows;
	this.cols = cols;
    }
    public double get(int row, int col){
	return matr[row][col];
    }
    public int getRows(){
	return rows;
    }
    public int getCols(){
	return cols;
    }
    public void setElementAt(double elem, int row, int col){
	matr[row][col] = elem;
    }

    public ArrayMatrix multiplicate(ArrayMatrix other){
        if (this.getCols() != other.getRows()) {
            return null;
        }
        ArrayMatrix res = new ArrayMatrix(this.getRows(), other.getCols());
        for(int i = 0; i<this.getRows(); i++){
            for(int j = 0; j<other.getCols(); j++){
                double sum = 0;
                for(int k = 0; k<this.getCols(); k++){
                    sum += this.get(i,k)*other.get(k,j);
                }
                res.setElementAt(sum, i, j);
            }
        }
        return res;
    }
}
