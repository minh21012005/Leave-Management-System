
package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import model.Employee;
import model.LeaveRequest;

public class UpdateServlet extends BaseRequiredAuthenticationController{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        int requestid = Integer.parseInt(req.getParameter("requestid"));
        String reason = req.getParameter("reason");
        Date startDate = Date.valueOf(req.getParameter("fromDate"));
        Date endDate = Date.valueOf(req.getParameter("toDate"));
        LeaveRequestDao lrd = new LeaveRequestDao();
        lrd.update(requestid, reason, startDate, endDate);
        String message = "Successful!";
        HttpSession session = req.getSession();
        session.setAttribute("message", message);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        req.setAttribute("employee", employee);
        int requestid = Integer.parseInt(req.getParameter("requestid"));
        req.setAttribute("requestid", requestid);
        LeaveRequestDao lrd = new LeaveRequestDao();
        LeaveRequest lr = lrd.getRequestById(requestid);
        Date startDate = lr.getStartdate();
        Date endDate = lr.getEnddate();
        req.setAttribute("startdate", startDate);
        req.setAttribute("enddate", endDate);
        req.getRequestDispatcher("/WEB-INF/update.jsp").forward(req, resp);
    }
}
