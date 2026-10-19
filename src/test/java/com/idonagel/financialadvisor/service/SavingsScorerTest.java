package com.idonagel.financialadvisor.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SavingsScorerTest {
    @Test
    void scoresOneWhenSavingNothing(){
        SavingsScorer scorer = new SavingsScorer();
        int score = scorer.calculateSavingsScore(10000,10000);
        assertEquals(1,score);

    }
    @Test
    void scoresOneWhenSpendingMoreThanIncome(){
        SavingsScorer scorer = new SavingsScorer();
        int score = scorer.calculateSavingsScore(10000,12000);
        assertEquals(1,score);

    }
    @Test
    void scoresTwoForLowSavings(){
        SavingsScorer scorer = new SavingsScorer();
        int score = scorer.calculateSavingsScore(10000,9500);
        assertEquals(2,score);

    }
    @Test
    void scoresTwoAtTenPercentBoundary(){
        SavingsScorer scorer = new SavingsScorer();
        int score = scorer.calculateSavingsScore(10000,9000);
        assertEquals(2,score);

    }
    @Test
    void scoresThreeJustAboveTenPercent(){
        SavingsScorer scorer = new SavingsScorer();
        int score = scorer.calculateSavingsScore(10000,8999);
        assertEquals(3,score);

    }
    @Test
    void scoresThreeForHighSavings(){
        SavingsScorer scorer = new SavingsScorer();
        int score = scorer.calculateSavingsScore(8000,7000);
        assertEquals(3,score);

    }

    //edge case tests
    @Test
    void scoresOneWhenNoIncome(){
        SavingsScorer scorer = new SavingsScorer();
        int score = scorer.calculateSavingsScore(0,5000);
        assertEquals(1,score);

    }
    @Test
    void scoresOneWhenNoIncomeAndNoExpenses(){
        SavingsScorer scorer = new SavingsScorer();
        int score = scorer.calculateSavingsScore(0,0);
        assertEquals(1,score);

    }
    @Test
    void rejectsNegativeIncome() {
        SavingsScorer scorer = new SavingsScorer();
        assertThrows(IllegalArgumentException.class, () -> scorer.calculateSavingsScore(-1000,500));
    }
    @Test
    void rejectsNegativeExpenses() {
        SavingsScorer scorer = new SavingsScorer();
        assertThrows(IllegalArgumentException.class, () -> scorer.calculateSavingsScore(10000,-100));
    }

}