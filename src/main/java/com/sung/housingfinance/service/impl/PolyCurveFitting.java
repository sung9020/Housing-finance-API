package com.sung.housingfinance.service.impl;

import com.sung.housingfinance.service.PolyCurveFittingInterface;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.springframework.stereotype.Service;

/*
 *
 * @author 123msn
 * @since 2019-04-06
 */

/**
 * 다항식 커브 피팅 클래스
 */
public class PolyCurveFitting implements PolyCurveFittingInterface {

    final int degree;
    RealMatrix realMatrix;

    public PolyCurveFitting(int degree){
        this.degree = degree;
    }

    @Override
    public double predictData(double x) {
        double predictedY = realMatrix.preMultiply(xVector(x))[0];

        return predictedY;
    }

    @Override
    public void setData(double[] y, double[] x) {
        double[][] xVectors = new double[x.length][];
        for(int i = 0; i < x.length; i++){
            xVectors[i] = xVector(x[i]); // x 개수 만큼 다항식을 생성하여 제공
        }
        OLSMultipleLinearRegression olsMultipleLinearRegression = new OLSMultipleLinearRegression();
        olsMultipleLinearRegression.setNoIntercept(true);
        olsMultipleLinearRegression.newSampleData(y, xVectors);
        realMatrix = MatrixUtils.createColumnRealMatrix(olsMultipleLinearRegression.estimateRegressionParameters());
    }

    private double[] xVector(double x){
        double[] poly = new double[degree + 1];
        double xSquare = 1;
        for( int i =0; i < degree + 1; i++){
            poly[i] = xSquare;
            xSquare *= x; // 1, x, x^2
        }
        return poly;
    }

}
