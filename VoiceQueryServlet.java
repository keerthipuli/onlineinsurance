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

@WebServlet("/VoiceQueryServlet")
public class VoiceQueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userQuery = request.getParameter("query");
        String aiResponse = "We have received your query: " + userQuery; // Example AI response

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root");

            // Insert query and response into the database
            String query = "INSERT INTO queries (user_query, ai_response) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, userQuery);
            pst.setString(2, aiResponse);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                request.setAttribute("successMessage", "Query submitted successfully!");
                request.setAttribute("aiResponse", aiResponse);
            } else {
                request.setAttribute("errorMessage", "Failed to submit query. Please try again.");
            }

            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
        }

        // Forward to the JSP page to display success or error messages
        request.getRequestDispatcher("voice-recognition.jsp").forward(request, response);
    }
}
