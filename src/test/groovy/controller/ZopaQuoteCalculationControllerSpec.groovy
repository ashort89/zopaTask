package controller

import ratecalculator.controller.ZopaQuoteCalculationController
import ratecalculator.model.Lender
import ratecalculator.model.Quote
import ratecalculator.service.LenderCsvFileReadingService
import ratecalculator.service.LenderSelectionService
import ratecalculator.service.LoanValidationService
import ratecalculator.service.QuoteService
import spock.lang.Specification

import java.math.RoundingMode

class ZopaQuoteCalculationControllerSpec extends Specification {

    private LenderCsvFileReadingService lenderCsvFileReadingService = Mock(LenderCsvFileReadingService)
    private LoanValidationService loanValidationService = Mock(LoanValidationService)
    private LenderSelectionService lenderSelectionService = Mock(LenderSelectionService)
    private QuoteService quoteService = Mock(QuoteService)

    def controller
    def amount = new BigDecimal(1000)
    def interestRate = new BigDecimal(7).setScale(1, RoundingMode.HALF_DOWN)
    def monthlyRepayment = new BigDecimal(30.78).setScale(2, RoundingMode.HALF_DOWN)
    def totalRepayment = new BigDecimal(1108.10).setScale(2, RoundingMode.HALF_DOWN)

    def jane = new Lender("jane", new BigDecimal("0.069"), new BigDecimal("480"))
    def fred = new Lender("fred", new BigDecimal("0.071"), new BigDecimal("520"))

    def setup() {
        controller = new ZopaQuoteCalculationController(lenderSelectionService: lenderSelectionService,
                loanValidationService: loanValidationService, quoteService: quoteService, lenderCsvFileReadingService: lenderCsvFileReadingService)
    }

    def "Quote calculation Controller returns a correctly formatted quote" () {
        when : "A Quote for a loan is requested"
            String outputQuote = controller.getQuote(["/here/is/my/path", "1000"] as String[])

        then : "The controller calls the expected services"
            1 * controller.loanValidationService.convertLoanRequestAmount(_ as String) >> amount
            1 * controller.lenderCsvFileReadingService.readLenderFile(_ as String) >> [jane, fred]
            1 * controller.lenderSelectionService.getLendersToFulfilLoanRequest(_ as List, _ as BigDecimal) >> [jane, fred]
            1 * controller.quoteService.generateQuote(_ as List, _ as BigDecimal) >> new Quote(amount, interestRate, monthlyRepayment, totalRepayment)

        and : "The quote is in the expected format"

        outputQuote as String
        outputQuote == "Requested amount: £1000\n" +
                "Annual Interest Rate: 7.0%" +
                "\nMonthly repayment: £30.78" +
                "\nTotal repayment: £1108.10"

    }
}
