import ratecalculator.controller.ZopaQuoteCalculationController;

public class ZopaLoanQuoteCalculator {

    public static void main(String[] args) {
        System.out.println(new ZopaQuoteCalculationController().getQuote(args));
    }
}
