import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        LoanManager loanManager = new LoanManager();
        LoanService loanService = new LoanService();

        // Create a new loan
        Loan newLoan = new Loan();
        newLoan.setLoanAmount(10000);
        newLoan.setLoanReason("Car Loan");
        newLoan.setTenure(24);

        // Calculate installments
        double interestRate = loanService.getAccurateInterestRate(newLoan.getLoanReason());
        double monthlyInstallment = loanService.calculateMonthlyInstallment(newLoan.getLoanAmount(), interestRate, newLoan.getTenure());
        newLoan.setMonthlyInstallment(monthlyInstallment);
        newLoan.setAnnualInstallment(loanService.calculateAnnualInstallment(monthlyInstallment));

        // Create a loan record in the database
        try {
            loanManager.createLoan(newLoan);
            System.out.println("Loan record created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Declare loans list outside try block for broader scope
        List<Loan> loans = null;

        // Get and display all loan records
        try {
            loans = loanManager.getLoanRecords();
            if (loans != null) {
                for (Loan loan : loans) {
                    System.out.println("Loan ID: " + loan.getId() + ", Amount: " + loan.getLoanAmount() +
                            ", Reason: " + loan.getLoanReason() + ", Tenure: " + loan.getTenure() +
                            ", Monthly Installment: " + loan.getMonthlyInstallment() +
                            ", Annual Installment: " + loan.getAnnualInstallment());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update a loan record (for example, update the first record)
        try {
            if (loans != null && !loans.isEmpty()) {
                Loan loanToUpdate = loans.get(0);
                loanToUpdate.setLoanAmount(15000);
                loanManager.updateLoan(loanToUpdate.getId(), loanToUpdate);
                System.out.println("Loan record updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Delete a loan record (for example, delete the first record)
        try {
            if (loans != null && !loans.isEmpty()) {
                loanManager.deleteLoan(loans.get(0).getId());
                System.out.println("Loan record deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
