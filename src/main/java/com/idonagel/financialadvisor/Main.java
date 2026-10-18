package com.idonagel.financialadvisor;

import com.idonagel.financialadvisor.service.RiskCalculator;

public class Main {
    public static void main (String[]  args){
        RiskCalculator calculator = new RiskCalculator();
        int[] scores = {3,1,3,3,3};
        double riskScore = calculator.calculateRiskScore(scores);

        System.out.println(riskScore);
    }
}
