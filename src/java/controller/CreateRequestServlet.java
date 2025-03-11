package controller;

import dal.LeaveRequestDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employee;
import java.sql.Date;

public class CreateRequestServlet extends BaseRequiredAuthenticationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        String reason = req.getParameter("reason");
        Date startDate = Date.valueOf(req.getParameter("fromDate"));
        Date endDate = Date.valueOf(req.getParameter("toDate"));
        LeaveRequestDao lrd = new LeaveRequestDao();
        lrd.set(employee.getEmployeeid(), employee.getManagerid(), startDate, endDate, reason);
        String message = "Successful!";
        req.setAttribute("message", message);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        req.setAttribute("employee", employee);
        req.getRequestDispatcher("/WEB-INF/createRequest.jsp").forward(req, resp);
    }

}
