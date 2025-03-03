package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import model.Employee;
import model.EmployeeAgenda;

public class AgendaServlet extends BaseRequiredAuthenticationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        LeaveRequestDao lrd = new LeaveRequestDao();
        ArrayList<EmployeeAgenda> list = lrd.getApprovedLeaveRequest();
        req.setAttribute("list", list);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date()); // Lấy ngày hiện tại của hệ thống
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Đặt về thứ Hai của tuần
        java.sql.Date weekStart = new java.sql.Date(cal.getTimeInMillis());
        cal.add(Calendar.DATE, 6); // Cộng 6 ngày để đến Chủ nhật
        java.sql.Date weekEnd = new java.sql.Date(cal.getTimeInMillis());
        req.setAttribute("weekStart", weekStart);
        req.setAttribute("weekEnd", weekEnd);
        req.getRequestDispatcher("/WEB-INF/agenda.jsp").forward(req, resp);
    }

}
