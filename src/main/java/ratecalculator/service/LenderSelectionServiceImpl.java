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
        return getLendersToFulfillRequest(lenders, requestedLoanAmount);

    }
    private List<Lender> getLendersToFulfillRequest(List<Lender> lenders, BigDecimal requestedLoanAmount) {
        //Sort the lenders by the best rate
        lenders.sort(Comparator.comparing(Lender::getRate));
        BigDecimal runningTotal;
        List<Lender> loanLenders = new ArrayList<>();

        for (Lender lender : lenders) {
            //if the amount the lender has is more or equal too the amount required
            if (lender.getAmount().compareTo(requestedLoanAmount) >= 0) {
                //add the amount required for the loan
                runningTotal = sumAmountAvailableFromLenders(loanLenders);
                //if the currentAmount is greater than 0 then we need to diff whats left of the request
                if (runningTotal.compareTo(requestedLoanAmount) < 0) {
                    //check if the offset between the running total and the request amount is less than the lenders amount
                    BigDecimal difference = requestedLoanAmount.subtract(runningTotal);
                    loanLenders.add(new Lender(lender.getName(), lender.getRate(), difference));
                } else {
                    break;
                }
            } else {
                //if its less than the current amount
                BigDecimal currentAmount = sumAmountAvailableFromLenders(loanLenders);
                BigDecimal amountLeft = requestedLoanAmount.subtract(currentAmount);
                if (lender.getAmount().compareTo(amountLeft) > 0) {
                    //if there is any left to add
                    if (amountLeft.compareTo(BigDecimal.ZERO) > 0) {
                        loanLenders.add(new Lender(lender.getName(), lender.getRate(), amountLeft));
                    } else {
                        break;
                    }
                } else {
                    //add the full amount the lender has
                    loanLenders.add(new Lender(lender.getName(), lender.getRate(), lender.getAmount()));
                }
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
