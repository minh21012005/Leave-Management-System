package controller;

import dal.DepartmentDao;
import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
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

        // Lấy danh sách department
        DepartmentDao dd = new DepartmentDao();
        List<String> departments = dd.getDepartment(employee.getEmployeeid());

        // Lấy tham số lọc từ request
        String employeeId = req.getParameter("employeeId");
        String department = req.getParameter("department");

        // Khởi tạo DAO và lấy danh sách agenda
        LeaveRequestDao dao = new LeaveRequestDao();
        ArrayList<EmployeeAgenda> list;

        // Kiểm tra xem có tham số lọc hay không
        if ((employeeId == null || employeeId.trim().isEmpty()) && (department == null || department.trim().isEmpty())) {
            // Không có lọc -> gọi phương thức lấy toàn bộ
            list = dao.getEmployeeAgendaList(weekStart, weekEnd);
        } else {
            // Có lọc -> gọi phương thức lọc
            list = dao.getEmployeeAgendaListFiltered(weekStart, weekEnd, employeeId, department);
        }

        // Đặt các thuộc tính để truyền sang JSP
        req.setAttribute("weekStart", weekStart);
        req.setAttribute("weekEnd", weekEnd);
        req.setAttribute("weekDays", weekDays);
        req.setAttribute("list", list);
        req.setAttribute("departments", departments);
        req.setAttribute("selectedEmployeeId", employeeId); // Giữ giá trị đã nhập
        req.setAttribute("selectionDepartment", department); // Giữ giá trị đã chọn

        req.getRequestDispatcher("/WEB-INF/agenda.jsp").forward(req, resp);
    }
}