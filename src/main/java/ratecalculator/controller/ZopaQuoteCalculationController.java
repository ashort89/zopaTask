package ratecalculator.controller;

import ratecalculator.error.LoanCalculationErrors;
import ratecalculator.model.Lender;
import ratecalculator.model.Quote;
import ratecalculator.service.LenderCsvFileReadingService;
import ratecalculator.service.LenderCsvFileReadingServiceImpl;
import ratecalculator.service.LenderSelectionService;
import ratecalculator.service.LenderSelectionServiceImpl;
import ratecalculator.service.LoanValidationService;
import ratecalculator.service.LoanValidationServiceImpl;
import ratecalculator.service.QuoteService;
import ratecalculator.service.QuoteServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class ZopaQuoteCalculationController {

    private static final int REQUIRED_ARGS_LENGTH = 2;
    private static final int LENDERS_FILE_PATH_INDEX = 0;
    private static final int LOAN_AMOUNT_INDEX = 1;

    private String filePath;
    private String loanAmount;
    private final LenderCsvFileReadingService lenderCsvFileReadingService;
    private final LoanValidationService loanValidationService;
    private final LenderSelectionService lenderSelectionService;
    private final QuoteService quoteService;


    public ZopaQuoteCalculationController() {
        this.lenderCsvFileReadingService = new LenderCsvFileReadingServiceImpl();
        this.loanValidationService = new LoanValidationServiceImpl();
        this.quoteService = new QuoteServiceImpl();
        this.lenderSelectionService = new LenderSelectionServiceImpl();
    }

    public void getQuote(String [] args) {
        validateArguments(args);
        filePath = args[LENDERS_FILE_PATH_INDEX];
        loanAmount = args[LOAN_AMOUNT_INDEX];

        BigDecimal requestedLoanAmount = loanValidationService.convertLoanRequestAmount(loanAmount);
        List<Lender> lenders = lenderCsvFileReadingService.readLenderFile(filePath);
        List<Lender> lendersForLoan = lenderSelectionService.getLendersToFulfilLoanRequest(lenders, requestedLoanAmount);
        Quote quote = quoteService.generateQuote(lendersForLoan, requestedLoanAmount);
        System.out.println(quote.toString());
    }

    private void validateArguments(String[] args) {
        if (args.length < REQUIRED_ARGS_LENGTH) {
            throw new RuntimeException(LoanCalculationErrors.INVALID_ARGS_SUPPLIED.getText());
        }
    }


}
