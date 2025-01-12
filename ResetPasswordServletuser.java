package Projectt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/resetPassword")
public class ResetPasswordServletuser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Check if the passwords match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("/reset-password.jsp").forward(request, response);
            return;
        }

        // Hash the password before saving (for security)
        String hashedPassword = hashPassword(newPassword);

        // Update the password in the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root")) {
            String query = "UPDATE user SET password = ? WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, hashedPassword);
            pst.setString(2, email);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                request.setAttribute("successMessage", "Password reset successful.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Email not found.");
                request.getRequestDispatcher("/reset-password.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("/reset-password.jsp").forward(request, response);
        }
    }

    // Simple hash function for the password (consider using stronger hashing mechanisms like bcrypt)
    private String hashPassword(String password) {
        try {
            // This is just an example, use a better hash function for real applications
            return Integer.toHexString(password.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
            return password;  // In a real scenario, avoid this fallback.
        }
    }
}
