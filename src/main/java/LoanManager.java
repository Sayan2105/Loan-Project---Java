import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/LoanDB";
    private static final String JDBC_USER = "root"; // Update with your MySQL username
    private static final String JDBC_PASSWORD = "password"; // Update with your MySQL password

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public void createLoan(Loan loan) throws SQLException {
        String sql = "INSERT INTO Loan (Loan_Amount, Loan_Reason, Tenure, Monthly_Installment, Annual_Installment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, loan.getLoanAmount());
            pstmt.setString(2, loan.getLoanReason());
            pstmt.setInt(3, loan.getTenure());
            pstmt.setDouble(4, loan.getMonthlyInstallment());
            pstmt.setDouble(5, loan.getAnnualInstallment());
            pstmt.executeUpdate();
        }
    }

    public List<Loan> getLoanRecords() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loan";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("Id"));
                loan.setLoanAmount(rs.getDouble("Loan_Amount"));
                loan.setLoanReason(rs.getString("Loan_Reason"));
                loan.setTenure(rs.getInt("Tenure"));
                loan.setMonthlyInstallment(rs.getDouble("Monthly_Installment"));
                loan.setAnnualInstallment(rs.getDouble("Annual_Installment"));
                loans.add(loan);
            }
        }
        return loans;
    }

    public void updateLoan(int id, Loan updatedLoan) throws SQLException {
        String sql = "UPDATE Loan SET Loan_Amount = ?, Loan_Reason = ?, Tenure = ?, Monthly_Installment = ?, Annual_Installment = ? WHERE Id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, updatedLoan.getLoanAmount());
            pstmt.setString(2, updatedLoan.getLoanReason());
            pstmt.setInt(3, updatedLoan.getTenure());
            pstmt.setDouble(4, updatedLoan.getMonthlyInstallment());
            pstmt.setDouble(5, updatedLoan.getAnnualInstallment());
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteLoan(int id) throws SQLException {
        String sql = "DELETE FROM Loan WHERE Id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
