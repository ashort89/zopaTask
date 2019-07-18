package ratecalculator.error;

public enum LoanCalculationErrors {
        NOT_ENOUGH_CAPITAL("Not enough capital in market to serve demand"),
        INVALID_ARGS_SUPPLIED("Invalid number of arguments passed"),
        REQUESTED_LOAN_AMOUNT_LOW("Loan amount requested is less than the minimum allowed"),
        REQUESTED_LOAN_AMOUNT_HIGH("Loan amount requested is higher than the maximum allowed"),
        REQUESTED_LOAN_AMOUNT_INCREMENT("Loan amount requested must be in multiples of 100"),
        FILE_NOT_FOUND("File could not be found"),
        ERROR_READING_FILE("There was a problem reading the data file"),
        ERROR_WITH_CONTENTS("There was a problem with the contents of the data file");

    private final String text;

    LoanCalculationErrors(String label) {
        this.text = label;
    }

    public String getText() {
        return text;
    }

}
