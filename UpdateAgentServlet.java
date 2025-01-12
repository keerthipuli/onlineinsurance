package Projectt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/UpdateAgentServlet")
public class UpdateAgentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int agentId = Integer.parseInt(request.getParameter("agentId"));
        String status = request.getParameter("status");
        String schedule = request.getParameter("schedule");

        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB", "root", "root");
            String query = "UPDATE agents SET status = ?, schedule = ? WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, status);
            pst.setString(2, schedule);
            pst.setInt(3, agentId);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                response.sendRedirect("manage-agents.jsp?updateSuccess=true");
            } else {
                response.sendRedirect("manage-agents.jsp?updateFailed=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manage-agents.jsp?updateFailed=true");
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
