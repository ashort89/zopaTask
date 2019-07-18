package ratecalculator.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class LoanCalculationServiceImpl implements LoanCalculationService {

    private static final BigDecimal MONTHS_IN_YEAR = new BigDecimal(12);
    private static final BigDecimal DECIMAL_TO_PERCENTAGE = new BigDecimal(100);

    @Override
    public BigDecimal calculateMonthlyRepayment(BigDecimal interestRate, BigDecimal amount, Integer paymentPeriod) {
        //Let P be the monthly payment amount you have to pay
        //Let N be the number of monthly payments (installments) = paymentPeriod
        //Let I be the annual interest rate = interest rate
        //Let A be the amount borrowed = amount
        //Let R = (1 + I/12)
        //P = A * ( R - 1 ) / ( 1 - R ^ (-N) )
        BigDecimal R = BigDecimal.ONE.add(interestRate.divide(MONTHS_IN_YEAR, MathContext.DECIMAL64));
        BigDecimal leftHandSide = amount.multiply(R.subtract(BigDecimal.ONE));
        BigDecimal rightHandSide = BigDecimal.ONE.subtract(R.pow(-paymentPeriod, MathContext.DECIMAL64));
        return leftHandSide.divide(rightHandSide, RoundingMode.HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN);
    }

    @Override
    public BigDecimal calculateTotalRepayment(BigDecimal amount, Integer paymentPeriod) {
        return amount.multiply(BigDecimal.valueOf(paymentPeriod)).setScale(2, RoundingMode.HALF_DOWN);
    }

    @Override
    public BigDecimal calculateLoanInterestRate(BigDecimal total, BigDecimal principle, Integer paymentPeriod) {
        //(1/t)(A/P - 1)
        BigDecimal leftHandSide = BigDecimal.ONE.divide(BigDecimal.valueOf(paymentPeriod), MathContext.DECIMAL64);
        BigDecimal rightHandSide = total.divide(principle, MathContext.DECIMAL64).subtract(BigDecimal.ONE);
        return leftHandSide.multiply(rightHandSide).multiply(DECIMAL_TO_PERCENTAGE).multiply(MONTHS_IN_YEAR).setScale(1, RoundingMode.HALF_DOWN);
    }


}


