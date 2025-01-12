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
import java.sql.ResultSet;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root");

            // SQL query to verify admin credentials
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);  // Hashing passwords in real scenarios
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Login successful, create session
                HttpSession session = request.getSession();
                session.setAttribute("admin_logged_in", true);
                response.sendRedirect("admindashboard.jsp"); // Redirect to admin dashboard
            } else {
                // Login failed, send error message
                request.setAttribute("error", "Invalid credentials. Please try again.");
                request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
            }

            // Close resources
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again later.");
            request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
        }
    }
}
