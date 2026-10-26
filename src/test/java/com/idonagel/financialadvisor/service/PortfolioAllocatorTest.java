package com.idonagel.financialadvisor.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioAllocatorTest {
    @Test
    void returnsMinimumForLowestScore() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        double stocks = allocator.calculateStockPercentage(1.0, 1);
        assertEquals(20, stocks, 0.0001);
    }
    @Test
    void capLimitsStocksForLowTolerance() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        double stocks = allocator.calculateStockPercentage(2.5, 1);
        assertEquals(50, stocks, 0.0001);
    }
    @Test
    void usesLineWhenBelowCap() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        double stocks = allocator.calculateStockPercentage(2.3, 3);
        assertEquals(59, stocks, 0.0001);
    }
    @Test
    void capsAtSixtyFiveForMediumTolerance() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        double stocks = allocator.calculateStockPercentage(3.0, 2);
        assertEquals(65, stocks, 0.0001);
    }

    // bad input tests
    @Test
    void rejectsToleranceBelowRange() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        assertThrows(IllegalArgumentException.class, () -> allocator.calculateStockPercentage(2.0,0));
    }
    @Test
    void rejectsToleranceAboveRange() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        assertThrows(IllegalArgumentException.class, () -> allocator.calculateStockPercentage(2.0,4));
    }
    @Test
    void rejectsRiskScoreAboveRange() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        assertThrows(IllegalArgumentException.class, () -> allocator.calculateStockPercentage(5.0,3));
    }
    @Test
    void rejectsRiskScoreBelowRange() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        assertThrows(IllegalArgumentException.class, () -> allocator.calculateStockPercentage(0.0,3));
    }


    // expected return tests
    @Test
    void returnsBlendedReturnForBalancedPortfolio(){
        PortfolioAllocator allocator = new PortfolioAllocator();
        double annualReturn = allocator.calculateExpectedReturn(50);
        assertEquals(0.065,annualReturn,0.0000001);
    }
    @Test
    void returnsBlendedReturnForUnevenSplit(){
        PortfolioAllocator allocator = new PortfolioAllocator();
        double annualReturn = allocator.calculateExpectedReturn(59);
        assertEquals(0.0713,annualReturn,0.0000001);
    }
    @Test
    void returnsLowestReturnForMostConservativePortfolio(){
        PortfolioAllocator allocator = new PortfolioAllocator();
        double annualReturn = allocator.calculateExpectedReturn(20);
        assertEquals(0.044,annualReturn,0.0000001);
    }
    @Test
    void returnsHighestReturnForMostAggressivePortfolio(){
        PortfolioAllocator allocator = new PortfolioAllocator();
        double annualReturn = allocator.calculateExpectedReturn(80);
        assertEquals(0.086,annualReturn,0.0000001);
    }


}