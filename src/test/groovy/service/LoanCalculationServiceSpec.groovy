package service

import ratecalculator.service.LoanCalculationServiceImpl
import spock.lang.Specification
import spock.lang.Unroll


@Unroll
class LoanCalculationServiceSpec extends Specification {

    def "Monthly repayment is calculated correctly" () {
        given : "A LoanCalculationService"
            def service = new LoanCalculationServiceImpl()

        when : "The service is called to calculate the monthly repayment for the loan"
            def monthlyRepayment = service.calculateMonthlyRepayment(new BigDecimal("0.069"), new BigDecimal("480"), 36)

        then : "the amount is calculated correctly"
            monthlyRepayment == 14.79 as BigDecimal
    }

    def "Repayment amount is calculated correctly" () {
        given : "A LoanCalculationService"
            def service = new LoanCalculationServiceImpl()

        when : "The service is called to calculate the total amount to be repaid"
            def total = service.calculateTotalRepayment(new BigDecimal(14.79), 36)

        then : "The amount is correct"
            total == 532.78 as BigDecimal

    }

    def "Interest rate for the total loan is calculated correctly" () {
        given : "a a LoanCalculationService"
            def service = new LoanCalculationServiceImpl()

        when : "The service is called to calculate the interest rate"
            def interest = service.calculateLoanInterestRate()

        then : "the interest is correct"
            interest == 7 as BigDecimal
    }

}
