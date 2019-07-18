package service

import ratecalculator.model.Lender
import ratecalculator.service.QuoteServiceImpl
import spock.lang.Specification

class QuoteServiceSpec extends Specification {

    def jane = new Lender("jane", new BigDecimal("0.069"), new BigDecimal("480"))
    def fred = new Lender("fred", new BigDecimal("0.071"), new BigDecimal("520"))

    def "Quote service correctly returns a quote as per the Zopa specification" () {
        given : "A Quote service"
            def service = new QuoteServiceImpl()

        when : "The service is called with lenders and an amount"
            def quote = service.generateQuote([jane, fred], new BigDecimal("1000"))

        then : "The quote is correctly calculated"
            quote.requestAmount == 1000
            quote.interestRate == 7.0
            quote.monthlyRepayment == 30.78
            quote.totalRepayment == 1108.10
    }
}
