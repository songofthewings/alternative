/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import java.util.Vector;

/**
 *
 * @author jiraffa
 */
public class Matrix {

    /** Creates a new instance of Matrix */
    public Matrix() {
    }

    private double[][] matr;
    private int rows;
    private int cols;
    public Matrix(int rows, int cols){
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

    public Matrix multiplicate(Matrix other){
        if (this.getCols() != other.getRows()) {
            return null;
        }
        Matrix res = new Matrix(this.getRows(), other.getCols());
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

    public Vector<Double> multiplicate(Vector<Double> vector){
        if (this.getCols() != vector.size()) {
            return null;
        }
        //вектор - это 1 колонка
        Vector<Double> res = new Vector<Double>();//this.getRows(), other.getCols());
        for(int i = 0; i<this.getRows(); i++){
            //for(int j = 0; j<vector.getCols(); j++){
                double sum = 0;
                for(int k = 0; k<this.getCols(); k++){
                    sum += this.get(i,k)*vector.get(k);
                }
                res.add(sum);
            //}
        }
        return res;
    }
}
