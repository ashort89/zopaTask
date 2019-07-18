package ratecalculator.model;

import java.math.BigDecimal;

public class Quote {

    private BigDecimal requestAmount;
    private BigDecimal interestRate;
    private BigDecimal monthlyRepayment;
    private BigDecimal totalRepayment;

    public Quote(BigDecimal requestAmount, BigDecimal interestRate, BigDecimal monthlyRepayment, BigDecimal totalRepayment) {
        this.requestAmount = requestAmount;
        this.interestRate = interestRate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public void setMonthlyRepayment(BigDecimal monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public BigDecimal getTotalRepayment() {
        return totalRepayment;
    }

    public void setTotalRepayment(BigDecimal totalRepayment) {
        this.totalRepayment = totalRepayment;
    }

    @Override
    public String toString() {
        return "Requested amount: £"+requestAmount+"\n"
                +"Annual Interest Rate: "+interestRate+"%\n"
                +"Monthly repayment: £"+monthlyRepayment+"\n"
                +"Total repayment: £"+totalRepayment;
    }
}
