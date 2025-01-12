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

@WebServlet("/subscribe")
public class SubscriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String planType = request.getParameter("planType");
        int duration = Integer.parseInt(request.getParameter("duration"));

        // Connect to the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root")) {
            String query = "INSERT INTO insurance_subscriptions (name, email, plan_type, duration) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, planType);
            pst.setInt(4, duration);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                request.setAttribute("successMessage", "Subscription successful for " + planType + ".");
            } else {
                request.setAttribute("errorMessage", "There was an issue with your subscription. Please try again.");
            }
            request.getRequestDispatcher("/subscriptions.jsp").forward(request, response);

        } catch (SQLException e) {
            Logger.getLogger(SubscriptionServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMessage", "An error occurred while processing your subscription.");
            request.getRequestDispatcher("/subscriptions.jsp").forward(request, response);
        }
    }
}
