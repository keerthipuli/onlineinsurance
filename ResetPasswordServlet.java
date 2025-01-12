package Projectt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String newPassword = request.getParameter("new_password");
        String confirmPassword = request.getParameter("confirm_password");

        // Check if the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        // Check if the admin is registered in the session
        HttpSession session = request.getSession();
        String sessionUsername = (String) session.getAttribute("admin_username");

        if (sessionUsername == null || !sessionUsername.equals(username)) {
            request.setAttribute("error", "Username does not exist. Please register first.");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        try {
            // Update the password in the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root");

            String query = "UPDATE admin SET password = ? WHERE username = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, newPassword);  // Set the new password
            pst.setString(2, username);  // Set the username to identify the admin
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                request.setAttribute("success", "Password reset successfully! You can now log in.");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Error resetting password. Please try again.");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            }

            // Close resources
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while resetting the password. Please try again.");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
        }
    }
}
