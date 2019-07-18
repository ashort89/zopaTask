package service

import ratecalculator.error.LoanCalculationErrors
import ratecalculator.service.LoanValidationServiceImpl
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class LoanValidationServiceSpec extends Specification {
    def "Loan validation service throws exception with #reason"() {
        given : "A loan validation service"
            def loanService = new LoanValidationServiceImpl()

        when: "The loan validation service is called with an amount"
            loanService.convertLoanRequestAmount(amount)

        then: "An exception is thrown with the appriopriate message"
            RuntimeException ex = thrown()
            ex.message == exceptiontext

        where :
            amount   | reason                                       | exceptiontext
            "100000" | "Request amount that is too high"            | LoanCalculationErrors.REQUESTED_LOAN_AMOUNT_HIGH.text
            "100"    | "Request amount that is too low"             | LoanCalculationErrors.REQUESTED_LOAN_AMOUNT_LOW.text
            "1099"   | "Request amount that is not multiple of 100" | LoanCalculationErrors.REQUESTED_LOAN_AMOUNT_INCREMENT.text
    }

    def "Loan validation service doesnt throws exception when #reason"() {
        given : "A loan validation service"
        def loanService = new LoanValidationServiceImpl()

        when: "The loan validation service is called with an amount"
        loanService.convertLoanRequestAmount(amount)

        then: "An exception is not thrown because the amount is to high"
            noExceptionThrown()

        where :
            amount   | reason
            "1000"   | "Amount is at the lower boundary"
            "15000"  | "Amount is at the upper boundary"
    }

    def "Loan validation service converts request string correctly"() {
        given : "A loan validation service"
            def loanService = new LoanValidationServiceImpl()

        when: "The loan validation service is called with an amount"
            def amount = loanService.convertLoanRequestAmount("1500")

        then: "The amount is correctly passed back as a big decimal"
            amount as BigDecimal
            amount == new BigDecimal(1500)
    }
}
