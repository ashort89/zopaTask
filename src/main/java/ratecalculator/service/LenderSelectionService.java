package ratecalculator.service;

import ratecalculator.model.Lender;

import java.math.BigDecimal;
import java.util.List;

public interface LenderSelectionService {

    List<Lender> getLendersToFulfilLoanRequest(List<Lender> lenders, BigDecimal requestedLoanAmount);
}
