package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import model.Employee;
import model.EmployeeAgenda;

public class AgendaServlet extends BaseRequiredAuthenticationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        // Tính ngày bắt đầu và kết thúc của tuần hiện tại (hoặc tuần được chọn)
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Bắt đầu từ thứ Hai
        Date weekStart = new java.sql.Date(cal.getTimeInMillis());
        cal.add(Calendar.DATE, 6); // Cuối tuần (Chủ nhật)
        Date weekEnd = new java.sql.Date(cal.getTimeInMillis());

        // Gọi DAO
        LeaveRequestDao dao = new LeaveRequestDao();
        ArrayList<EmployeeAgenda> list = dao.getApprovedLeaveRequest(weekStart, weekEnd);

        // Đặt thuộc tính để truyền sang JSP
        req.setAttribute("weekStart", weekStart);
        req.setAttribute("weekEnd", weekEnd);
        req.setAttribute("list", list);

        // Chuyển hướng tới JSP
        req.getRequestDispatcher("/WEB-INF/agenda.jsp").forward(req, resp);
    }

}
