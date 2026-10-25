package com.idonagel.financialadvisor.service;

import com.idonagel.financialadvisor.model.YearlyValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectionCalculatorTest {
    @Test
    void zeroReturnJustAddsDeposits() {
        ProjectionCalculator calculator = new ProjectionCalculator();
        List<YearlyValue> result = calculator.project(5000, 1000, 0.0, 2);

        assertEquals(2, result.size());                          // one row per year

        YearlyValue firstYear = result.getFirst();
        assertEquals(1, firstYear.year());                       // years start at 1, not 0
        assertEquals(17000, firstYear.balance(), 0.01);          // 12 deposits added in year 1
        assertEquals(17000, firstYear.totalDeposited(), 0.01);   // deposits tracked correctly

        YearlyValue secondYear = result.get(1);
        assertEquals(29000, secondYear.balance(), 0.01);         // balance carries over into year 2
    }

    @Test
    void oneYearCompoundsToExactlyTheAnnualReturn() {
        ProjectionCalculator calculator = new ProjectionCalculator();
        List<YearlyValue> result = calculator.project(10000, 0, 0.12, 1);
        assertEquals(1,result.size());

        YearlyValue firstYear = result.getFirst();
        assertEquals(1, firstYear.year());
        assertEquals(11200, firstYear.balance(), 0.01);
    }
    @Test
    void projectsThirtyYearsForExampleUser() {
        ProjectionCalculator calculator = new ProjectionCalculator();
        List<YearlyValue> result = calculator.project(10000, 1000, 0.065, 30);

        assertEquals(30,result.size());
        YearlyValue firstYear = result.getFirst();

        assertEquals(1, firstYear.year());
        assertEquals(23003.44, firstYear.balance(), 0.01);
        assertEquals(185474, result.get(9).balance(), 1);
        assertEquals(1133170, result.get(29).balance(), 1);
        assertEquals(370000,result.get(29).totalDeposited(),0.01);

    }

    // tests for the case the user as a negative monthly split.
    @Test
    void withdrawalsStopWhenAccountIsEmpty(){
        ProjectionCalculator calculator = new ProjectionCalculator();
        List<YearlyValue> result = calculator.project(10000, -1000, 0.0, 2);

        assertEquals(2,result.size());
        YearlyValue firstYear = result.getFirst();
        assertEquals(0, firstYear.balance(),0.01);
        assertEquals(0, result.get(1).balance());
        assertEquals(0,firstYear.totalDeposited());
    }

    // invalid input tests
    @Test
    void rejectsZeroYears(){
        ProjectionCalculator calculator = new ProjectionCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.project(10000,1000,0.065,0));
    }
    @Test
    void rejectsMoreThanSixtyYears(){
        ProjectionCalculator calculator = new ProjectionCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.project(10000,1000,0.065,61));
    }
    @Test
    void allowsExactlySixtyYears() {
        ProjectionCalculator calculator = new ProjectionCalculator();
        List<YearlyValue> result = calculator.project(10000,0, 0.0, 60);
        assertEquals(60, result.size());
    }
    @Test
    void rejectsNegativeEquity(){
        ProjectionCalculator calculator = new ProjectionCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.project(-5000,1000,0.065,10));
    }


}