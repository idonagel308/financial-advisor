package com.idonagel.financialadvisor.model;
/**
 * Everything a person tells the app: their questionnaire answers and their money.
 * Values that can be calculated from these (savings score, risk score,
 * monthly savings) are deliberately not included.
 *
 * @param timeHorizon      answer to "when will you need most of the money", 1 to 3
 * @param riskTolerance    answer to "your portfolio dropped 30%", 1 to 3
 * @param upcomingExpenses answer to "a big expense coming up", 1 to 3
 * @param familySupport    answer to "can someone help you financially", 1 to 3
 * @param monthlyIncome    income per month, in shekels
 * @param monthlyExpenses  expenses per month, in shekels
 * @param currentEquity    money already saved or invested, in shekels
 * @param years            how many years to project
 */
public record AdvisorRequest(int timeHorizon, int riskTolerance, int upcomingExpenses, int familySupport, double monthlyIncome, double monthlyExpenses, double currentEquity, int years) {
}
