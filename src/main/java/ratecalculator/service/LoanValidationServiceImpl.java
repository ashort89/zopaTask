package ratecalculator.service;

import ratecalculator.error.LoanCalculationErrors;

import java.math.BigDecimal;

public class LoanValidationServiceImpl implements LoanValidationService {

    private static final BigDecimal MINIMUM_LOAN_AMOUNT = new BigDecimal(1000);
    private static final BigDecimal MAXIMUM_LOAN_AMOUNT = new BigDecimal(15000);
    private static final BigDecimal LOAN_AMOUNT_INCREMENTAL_VALUE = new BigDecimal(100);

    @Override
    public BigDecimal convertLoanRequestAmount(String amount) {
        BigDecimal loanAmount = new BigDecimal(amount);
        validateLoanAmount(loanAmount);
        return loanAmount;
    }

    private void validateLoanAmount(BigDecimal amount) {
        if (amount.compareTo(MINIMUM_LOAN_AMOUNT) < 0) {
            throw new RuntimeException(LoanCalculationErrors.REQUESTED_LOAN_AMOUNT_LOW.getText());
        } else if (amount.compareTo(MAXIMUM_LOAN_AMOUNT) > 0) {
            throw new RuntimeException(LoanCalculationErrors.REQUESTED_LOAN_AMOUNT_HIGH.getText());
        } else if (!amount.remainder(LOAN_AMOUNT_INCREMENTAL_VALUE).equals(BigDecimal.ZERO)) {
            throw new RuntimeException(LoanCalculationErrors.REQUESTED_LOAN_AMOUNT_INCREMENT.getText());
        }
    }

}
