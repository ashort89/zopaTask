package ratecalculator.service;

import java.math.BigDecimal;

public interface LoanValidationService {
    BigDecimal convertLoanRequestAmount(String amount);
}
