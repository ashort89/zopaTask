package ratecalculator.service;

import ratecalculator.model.Lender;
import ratecalculator.model.Quote;

import java.math.BigDecimal;
import java.util.List;

public class QuoteServiceImpl implements QuoteService  {

    private static final int LOAN_PAYMENT_PERIOD = 36;

    private final LoanCalculationService loanCalculationService;

    public QuoteServiceImpl() {
        this.loanCalculationService = new LoanCalculationServiceImpl();
    }

    @Override
    public Quote generateQuote(List<Lender> lenders, BigDecimal loanAmount) {
        BigDecimal monthlyAmount = lenders.stream()
                .map(lender -> loanCalculationService.calculateMonthlyRepayment(lender.getRate(), lender.getAmount(), LOAN_PAYMENT_PERIOD))
                .reduce(BigDecimal::add).get();
        BigDecimal total = loanCalculationService.calculateTotalRepayment(monthlyAmount, LOAN_PAYMENT_PERIOD);
        BigDecimal interestRate = loanCalculationService.calculateLoanInterestRate(total, loanAmount, LOAN_PAYMENT_PERIOD);
        return new Quote(loanAmount, interestRate, monthlyAmount, total);
    }
}
