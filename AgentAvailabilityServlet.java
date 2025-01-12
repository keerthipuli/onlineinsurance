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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AgentAvailabilityServlet")
public class AgentAvailabilityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Agent> agents = new ArrayList<>();

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root");

            // Query to get all agents
            String query = "SELECT * FROM agents";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            // Loop through the result set and create Agent objects
            while (rs.next()) {
                String name = rs.getString("name");
                String status = rs.getString("status");
                String contact = rs.getString("contact");

                agents.add(new Agent(name, status, contact));
            }

            // Set the list of agents as a request attribute
            request.setAttribute("agents", agents);

            // Forward to the JSP page
            request.getRequestDispatcher("agent-availability.jsp").forward(request, response);

            // Close resources
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching agent data.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}