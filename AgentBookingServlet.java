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

@WebServlet("/AgentBookingServlet")
public class AgentBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String appointmentDate = request.getParameter("appointmentDate");

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root");

            // Insert booking details into the database
            String query = "INSERT INTO bookings (name, email, phone, appointment_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, appointmentDate);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                // Redirect to a success page (or display a success message)
                response.sendRedirect("booking-success.jsp");
            } else {
                // Redirect to a failure page (or display an error message)
                request.setAttribute("error", "Booking failed. Please try again.");
                request.getRequestDispatcher("agent-booking.jsp").forward(request, response);
            }

            // Close resources
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("agent-booking.jsp").forward(request, response);
        }
    }
}
