public class LoanService {

    public double calculateMonthlyInstallment(double loanAmount, double interestRate, int tenure) {
        double rate = interestRate / 1200;
        if (rate == 0) {
            return loanAmount / tenure;
        } else {
            double numerator = rate * loanAmount;
            double denominator = 1 - Math.pow(1 + rate, -tenure);
            return numerator / denominator;
        }
    }

    public double calculateAnnualInstallment(double monthlyInstallment) {
        return monthlyInstallment * 12;
    }

    public double getAccurateInterestRate(String loanReason) {
        double interestRate;
        switch (loanReason) {
            case "Home Loan":
                interestRate = 0.75;
                break;
            case "Car Loan":
                interestRate = 0.85;
                break;
            case "Education Loan":
                interestRate = 0.65;
                break;
            case "Personal Loan":
                interestRate = 1.0;
                break;
            default:
                interestRate = 0;
                break;
        }
        double economicFactor = 1.02;
        double customerRiskFactor = 1.01;
        interestRate = interestRate * economicFactor * customerRiskFactor;
        return Math.round(interestRate * 100.0) / 100.0;
    }
}
