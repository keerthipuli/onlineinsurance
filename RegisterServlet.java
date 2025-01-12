package Projectt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        String policy = request.getParameter("policy");
        String payment = request.getParameter("payment");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");

        Connection con = null;
        PreparedStatement pst = null;

        try {
            // Load the MySQL driver and establish a database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root");

            // SQL query to insert user data
            String query = "INSERT INTO users (name, username, age, policy, payment, email, password, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);

            // Set the parameters for the prepared statement
            pst.setString(1, name);
            pst.setString(2, username);
            pst.setInt(3, Integer.parseInt(age));
            pst.setString(4, policy);
            pst.setString(5, payment);
            pst.setString(6, email);
            pst.setString(7, password);  // In production, hash the password before saving it
            pst.setString(8, address);

            // Execute the query and check if rows were affected
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                request.setAttribute("success", "Registration successful! You can now log in.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // Log the error and forward back to the registration page with an error message
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);

        } finally {
            try {
                // Close resources
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
