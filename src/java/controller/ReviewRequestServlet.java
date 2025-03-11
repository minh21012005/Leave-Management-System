package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Employee;
import model.LeaveRequest;

public class ReviewRequestServlet extends BaseRequiredAuthenticationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        int requestId = Integer.parseInt(req.getParameter("requestid"));
        String action = req.getParameter("action");
        String newStatus = action.equals("approve") ? "Approved" : "Rejected";
        LeaveRequestDao lrd = new LeaveRequestDao();
        lrd.updateStatusRequest(newStatus, requestId);
        String message = "Successful!";
        req.setAttribute("message", message);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        LeaveRequestDao lrd = new LeaveRequestDao();
        ArrayList<LeaveRequest> list = lrd.getOtherRequests(employee);
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/reviewRequest.jsp").forward(req, resp);
    }
}
