package ratecalculator.service;

import ratecalculator.error.LoanCalculationErrors;
import ratecalculator.model.Lender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LenderSelectionServiceImpl implements LenderSelectionService {

    @Override
    public List<Lender> getLendersToFulfilLoanRequest(List<Lender> lenders, BigDecimal requestedLoanAmount) {
        checkLendersSupplyMarketDemand(lenders, requestedLoanAmount);
        return getLendersToFulfillQuote(lenders, requestedLoanAmount);

    }

    private List<Lender> getLendersToFulfillQuote(List<Lender> lenders, BigDecimal requestedLoanAmount) {
        lenders.sort(Comparator.comparing(Lender::getRate));
        BigDecimal runningTotal = BigDecimal.ZERO;
        List<Lender> loanLenders = new ArrayList<>();

        for (Lender lender : lenders) {
            if (runningTotal.compareTo(requestedLoanAmount) < 0) {
                if (runningTotal.add(lender.getAmount()).compareTo(requestedLoanAmount) > 0) {
                    Lender loanLender = new Lender(lender.getName(), lender.getRate(), requestedLoanAmount.subtract(runningTotal));
                    loanLenders.add(loanLender);
                    runningTotal = runningTotal.add(requestedLoanAmount.subtract(runningTotal));
                } else {
                    loanLenders.add(lender);
                    runningTotal = runningTotal.add(lender.getAmount());
                }
            } else {
                break;
            }
        }
        return loanLenders;
    }

    private void checkLendersSupplyMarketDemand(List<Lender> lenders, BigDecimal requestedLoanAmount) {
        BigDecimal totalAmountInMarket = sumAmountAvailableFromLenders(lenders);
        if (requestedLoanAmount.compareTo(totalAmountInMarket) > 0) {
            throw new RuntimeException(LoanCalculationErrors.NOT_ENOUGH_CAPITAL.getText());
        }
    }

    private BigDecimal sumAmountAvailableFromLenders(List<Lender> loanLenders) {
        return loanLenders
                .stream()
                .map(Lender::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
