
package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Employee;
import model.LeaveRequest;

public class Agenda extends BaseRequiredAuthenticationController{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        LeaveRequestDao lrd = new LeaveRequestDao();
        ArrayList<LeaveRequest> list = lrd.getApprovedLeaveRequest();
        req.setAttribute("leaveRequests", list);
        req.getRequestDispatcher("/WEB-INF/agenda.jsp").forward(req, resp);
    }
    
}
