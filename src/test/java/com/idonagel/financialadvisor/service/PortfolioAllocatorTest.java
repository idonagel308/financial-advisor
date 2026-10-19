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
    void theScoreGetsOverriddenByCap() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        double stocks = allocator.calculateStockPercentage(2.5, 1);
        assertEquals(50, stocks, 0.0001);
    }
    @Test
    void noCapTheLineIsUsed() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        double stocks = allocator.calculateStockPercentage(2.3, 3);
        assertEquals(59, stocks, 0.0001);
    }
    @Test
    void theMiddleCapPutToUse() {
        PortfolioAllocator allocator = new PortfolioAllocator();
        double stocks = allocator.calculateStockPercentage(3.0, 2);
        assertEquals(65, stocks, 0.0001);
    }

}