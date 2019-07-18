package ratecalculator.service;

import ratecalculator.model.Lender;
import ratecalculator.model.Quote;

import java.math.BigDecimal;
import java.util.List;

public interface QuoteService {
    Quote generateQuote (List<Lender> lenders, BigDecimal loanAmount);
}
