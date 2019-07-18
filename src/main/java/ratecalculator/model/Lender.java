package ratecalculator.model;

import java.math.BigDecimal;

public class Lender {

    private String name;
    private BigDecimal rate;
    private BigDecimal amount;

    public Lender(String name, BigDecimal rate, BigDecimal amount) {
        this.name = name;
        this.rate = rate;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
