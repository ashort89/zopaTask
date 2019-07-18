package ratecalculator.service;


import java.math.BigDecimal;

public interface LoanCalculationService {

    BigDecimal calculateMonthlyRepayment(BigDecimal interestRate, BigDecimal amount, Integer paymentPeriod);

    BigDecimal calculateTotalRepayment(BigDecimal amount, Integer paymentPeriod);

    BigDecimal calculateLoanInterestRate(BigDecimal total, BigDecimal loanAmount, Integer paymentPeriod);

}
