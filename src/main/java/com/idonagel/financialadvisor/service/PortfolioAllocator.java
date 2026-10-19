package com.idonagel.financialadvisor.service;

public class PortfolioAllocator {




        private static final double MIN_STOCKS = 20;//stock % for the most conservative user (score 1)
        private static final double MAX_STOCKS = 80;//stock % for the most aggressive user (score 3)
        private static final double[] STOCK_CAPS = {50,65,80};//the cap for tolerance 1, 2, 3

        public double calculateStockPercentage(double riskScore, int riskTolerance){
            double fraction = (riskScore -1)/2;
            double line  = MIN_STOCKS + fraction *(MAX_STOCKS - MIN_STOCKS);
            double cap  = STOCK_CAPS[riskTolerance-1];
            return Math.min(line, cap);
        }
    }

