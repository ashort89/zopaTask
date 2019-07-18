import spock.lang.Specification

class ZopaLoanQuoteCalculatorSpec extends Specification {
    def "application throws exception with incorrect args"() {
        when:
            new ZopaLoanQuoteCalculator().main("blah")
        then:
            thrown(RuntimeException)
    }
}
