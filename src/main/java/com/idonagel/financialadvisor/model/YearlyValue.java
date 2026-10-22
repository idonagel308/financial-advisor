package com.idonagel.financialadvisor.model;
/**
 * The state of the account at the end of one year of a projection.
 *
 * @param year the year number, starting at 1
 * @param balance money in the account at the end of the year, in shekels
 * @param totalDeposited all money put in so far (initial equity plus monthly
 *                       savings), minus what was really withdrawn, in shekels.
 *                       Profit = balance - totalDeposited.
 */
public record YearlyValue(int year, double balance, double totalDeposited) {
}
