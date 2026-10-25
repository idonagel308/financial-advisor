# Financial Advisor

An educational web API that shows young people how much **time and compound interest** can do for their money, and suggests a simple split between stocks and a money-market fund based on a short risk questionnaire.

> **Educational tool, not investment advice.** The numbers are simplified projections based on historical averages. Past returns do not guarantee future returns.

## Why I built this

I built this because finance is more than numbers. It's also a lot of psychology. If you show people how their money can work for them, at a level of risk they can actually live with, you can change the way they think about their future.

## What it does

1. Takes four questionnaire answers plus monthly income, expenses, current savings and a time horizon in years.
2. Calculates a **risk score** from 1 (needs a conservative portfolio) to 3 (can take more risk).
3. Turns the risk score into a **stocks / money-market split**.
4. Projects the account **month by month** and returns the balance for every year, the total deposited and the profit.

## The algorithm

### 1. Risk score

Each input is scored from 1 to 3. The risk score is their weighted average:

| Input | Weight | What it measures |
|---|---|---|
| Time horizon | 0.35 | How long until the money is needed |
| Risk tolerance | 0.25 | "You invested 10,000 and a month later it's worth 7,000. What do you do?" |
| Upcoming expenses | 0.20 | Big expenses coming soon |
| Family support | 0.12 | A financial safety net |
| Savings score | 0.08 | Calculated from income and expenses (not asked) |

The savings score is calculated, not asked: savings rate of 0% or less → 1, up to 10% → 2, above 10% → 3. The server calculates it from the numbers the client sends, so the client can't send contradictory data.

### 2. Stock percentage

A linear formula maps the risk score to between 20% and 80% stocks:

```
stocks % = 20 + (riskScore − 1) / 2 × 60
```

**The cap rule:** low risk tolerance *caps* the stock percentage, whatever the other answers are: tolerance 1 → at most 50%, tolerance 2 → at most 65%. The reason is behavioural: someone who would sell in a panic after the first big drop locks in the loss, so a lower stock share protects them from themselves.

The rest goes to a money-market fund.

### 3. Expected return

| Asset | Yearly return | Source |
|---|---|---|
| Stocks | 10% | S&P 500 including dividends, 1928–2025, geometric average ([Damodaran, NYU](https://pages.stern.nyu.edu/~adamodar/New_Home_Page/datafile/histretSP.html)) |
| Money-market fund | 3% | Bank of Israel rate 3.25% minus average management fees (~0.16%) |

The portfolio return is the weighted mix, e.g. 50/50 → 6.5%.

Simplifications: the S&P 500 is measured in dollars (no currency risk is modelled), and money-market returns move with the Bank of Israel rate.

### 4. Projection

Monthly compounding, using a monthly rate that compounds to exactly the yearly return after 12 months: `(1 + r)^(1/12) − 1`. Each month the balance earns interest, then the monthly savings are added. Negative savings are withdrawals; if the account runs out, it stays at 0 and the response reports the year the money ran out.

## API

### `POST /api/recommendation`

**Request**

```json
{
  "timeHorizon": 3,
  "riskTolerance": 1,
  "upcomingExpenses": 3,
  "familySupport": 3,
  "monthlyIncome": 8000,
  "monthlyExpenses": 7000,
  "currentEquity": 10000,
  "years": 30
}
```

**Response** (`200 OK`, projection shortened, values rounded)

```json
{
  "riskScore": 2.5,
  "stockPercentage": 50,
  "moneyMarketPercentage": 50,
  "annualReturn": 0.065,
  "projection": [
    { "year": 1, "balance": 23003.44, "totalDeposited": 22000 },
    "... one entry per year ...",
    { "year": 30, "balance": 1133169.96, "totalDeposited": 370000 }
  ],
  "finalBalance": 1133169.96,
  "totalDeposited": 370000,
  "profit": 763169.96,
  "yearMoneyRunsOut": 0
}
```

**Errors** (`400 Bad Request`), always in the same shape:

```json
{ "error": "Score at index 1 must be between 1.0 and 3.0 but was 7" }
```

- Invalid value (e.g. a score outside 1–3, or `years` outside 1–60) → the reason.
- Missing field or broken JSON → `"Request body is missing a field or is not valid JSON"`.

## How to run

Requirements: **Java 24** and **Maven**.

```
mvn spring-boot:run
```

Or open the project in IntelliJ and run `FinancialAdvisorApplication`.

Then open **`http://localhost:8080/`** for the demo page: answer the questions and see your split, a growth chart and a year-by-year table. The page does no calculations; it sends your answers to `POST /api/recommendation` with `fetch` and only displays the answer.

## Tests

```
mvn test
```

41 tests: unit tests for every calculator, and integration tests that send real requests to the API with MockMvc (valid request → 200, invalid input → 400).

## Tech stack

Java 24 · Spring Boot 4 · Maven · JUnit 6 · MockMvc · Postman

## How it was built

My first end-to-end solo project. I wrote every line of the Java code myself, step by step, with Claude (AI) as a tutor and code reviewer. The demo page (`src/main/resources/static/index.html`) was built with AI assistance; I reviewed it, and all logic stays in the Java backend. Every feature went through a GitHub Issue → branch → Pull Request → review → merge.

## Roadmap

- Exact money calculations with `BigDecimal`, and profit for every year in the projection
- Inflation-adjusted returns and a range of possible outcomes instead of one number
- Saving and comparing scenarios (e.g. "30 years at 50/50" vs "30 years at 80/20")