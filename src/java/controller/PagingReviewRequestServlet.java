package controller;

import dal.LeaveRequestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Employee;
import model.LeaveRequest;

/**
 *
 * @author minho
 */
public class PagingReviewRequestServlet extends BaseRequiredAuthenticationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {
        LeaveRequestDao lrd = new LeaveRequestDao();
        ArrayList<LeaveRequest> list = lrd.getOtherRequests(employee);
        int totalItems = list.size();
        int pageSize = 5;
        int totalPages = (totalItems + pageSize - 1) / pageSize;
        int currentPage = Integer.parseInt(req.getParameter("currentpage"));
        ArrayList<LeaveRequest> listMain = lrd.getOtherRequests(employee, currentPage, pageSize);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("list", listMain);
        req.getRequestDispatcher("/WEB-INF/reviewRequest.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException {

    }

}
