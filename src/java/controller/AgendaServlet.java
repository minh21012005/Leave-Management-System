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
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date weekStart = new java.sql.Date(cal.getTimeInMillis());
        cal.add(Calendar.DATE, 6);
        Date weekEnd = new java.sql.Date(cal.getTimeInMillis());

        // Tính danh sách các ngày trong tuần
        ArrayList<Date> weekDays = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            long time = weekStart.getTime() + i * 24 * 60 * 60 * 1000L;
            weekDays.add(new java.sql.Date(time));
        }

        LeaveRequestDao dao = new LeaveRequestDao();
        ArrayList<EmployeeAgenda> list = dao.getApprovedLeaveRequest(weekStart, weekEnd);

        req.setAttribute("weekStart", weekStart);
        req.setAttribute("weekEnd", weekEnd);
        req.setAttribute("weekDays", weekDays); // Truyền danh sách ngày
        req.setAttribute("list", list);

        req.getRequestDispatcher("/WEB-INF/agenda.jsp").forward(req, resp);
    }

}
