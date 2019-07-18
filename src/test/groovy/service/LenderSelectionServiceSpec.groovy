package service

import ratecalculator.error.LoanCalculationErrors
import ratecalculator.model.Lender
import ratecalculator.service.LenderSelectionServiceImpl
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class LenderSelectionServiceSpec extends Specification {

    def dave = new Lender("dave", new BigDecimal("0.007"), new BigDecimal("100"))
    def bob = new Lender("Bob", new BigDecimal("0.075"), new BigDecimal("640"))
    def jane = new Lender("Jane", new BigDecimal("0.069"), new BigDecimal("480"))
    def fred = new Lender("Fred", new BigDecimal("0.071"), new BigDecimal("520"))

    def "Attempting to fulfill a loan with more than the market has causes an error"() {
        given : "A Lender selection service"
            def service = new LenderSelectionServiceImpl()

        when : "The lender service is called with a request for more money than is available"
            service.getLendersToFulfilLoanRequest([dave], new BigDecimal(2000))

        then : "An exception is thrown"
            RuntimeException ex = thrown()
            ex.message == LoanCalculationErrors.NOT_ENOUGH_CAPITAL.text
    }


    def "Lenders are returned according to the lowest rates and the amount equates to the loan amount" () {
        given : "A Lender selection service"
            def service = new LenderSelectionServiceImpl()

        when : "The lender service is called with a request for more money than is available"
            def response = service.getLendersToFulfilLoanRequest([bob, jane, fred], new BigDecimal(1000))

        then : "The correct lenders are returned"
            response.size() == 2
            response.get(0).name == jane.name
            response.get(0).amount == jane.amount
            response.get(0).rate == jane.rate
            response.get(1).name == fred.name
            response.get(1).amount == fred.amount
            response.get(1).rate == fred.rate
    }

}
