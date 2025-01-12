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

@WebServlet("/CancellationServlet")
public class CancellationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");
        String agentName = request.getParameter("agentName");
        String appointmentDate = request.getParameter("appointmentDate");
        String reason = request.getParameter("reason");

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root");

            // Insert cancellation details into the database
            String query = "INSERT INTO cancellations (appointment_id, agent_name, appointment_date, reason) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, appointmentId);
            pst.setString(2, agentName);
            pst.setString(3, appointmentDate);
            pst.setString(4, reason);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                // Redirect to a success page
                response.sendRedirect("cancellation-success.jsp");
            } else {
                // Redirect to an error page
                request.setAttribute("error", "Cancellation failed. Please try again.");
                request.getRequestDispatcher("cancellation.jsp").forward(request, response);
            }

            // Close resources
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("cancellation.jsp").forward(request, response);
        }
    }
}
