package com.sung.housingfinance.service;

/*
 *
 * @author 123msn
 * @since 2019-04-06
 */
public interface PolyCurveFittingInterface {

    void setData(double[] y, double[] x);

    double predictData(double x);
}
