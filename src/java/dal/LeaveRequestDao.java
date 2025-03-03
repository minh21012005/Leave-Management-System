package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.LeaveRequest;
import java.sql.Date;
import java.util.ArrayList;
import model.EmployeeAgenda;

public class LeaveRequestDao extends DBcontext {

    public ArrayList getMyRequest(Employee employee) {
        String sql = "SELECT * FROM LeaveRequests le where le.EmployeeID = ?";
        ArrayList<LeaveRequest> list = new ArrayList();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, employee.getEmployeeid());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                LeaveRequest leaveRequest = new LeaveRequest();
                leaveRequest.setRequestid(rs.getInt("RequestID"));
                leaveRequest.setEmployeeid(employee.getEmployeeid());
                leaveRequest.setManagerid(rs.getInt("ManagerID"));
                leaveRequest.setStartdate(rs.getDate("StartDate"));
                leaveRequest.setEnddate(rs.getDate("EndDate"));
                leaveRequest.setReason(rs.getString("Reason"));
                leaveRequest.setStatus(rs.getString("Status"));
                leaveRequest.setRequestdate(rs.getDate("RequestDate"));
                list.add(leaveRequest);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }

    public int set(int employeeid, int managerid, Date startdate, Date endDate, String reason) {
        String sqlInsert = "INSERT INTO LeaveRequests (EmployeeID, ManagerID, StartDate, EndDate, Reason) VALUES (?, ?, ?, ?, ?)";
        String sqlGetId = "SELECT @@IDENTITY AS RequestID";

        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Chuẩn bị và thực thi câu lệnh INSERT
            PreparedStatement stInsert = connection.prepareStatement(sqlInsert);
            stInsert.setInt(1, employeeid);
            stInsert.setInt(2, managerid);
            stInsert.setDate(3, startdate);
            stInsert.setDate(4, endDate);
            stInsert.setString(5, reason);
            stInsert.executeUpdate();

            // Lấy RequestID vừa tạo bằng @@IDENTITY
            PreparedStatement stGetId = connection.prepareStatement(sqlGetId);
            ResultSet rs = stGetId.executeQuery();
            int requestId = -1;
            if (rs.next()) {
                requestId = rs.getInt("RequestID"); // Lấy giá trị RequestID
            }

            // Kết thúc transaction thành công
            connection.commit();
            return requestId;

        } catch (SQLException e) {
            try {
                // Nếu có lỗi, rollback transaction
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, "Rollback failed", rollbackEx);
            }
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        } finally {
            try {
                // Khôi phục chế độ auto-commit
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, "Reset auto-commit failed", e);
            }
        }
    }

    public ArrayList getOtherRequests(Employee employee) {
        ArrayList<LeaveRequest> l = new ArrayList();
        try {
            String sql;
            ResultSet rs;
            if (employee.getEmployeeid() == 1) {
                sql = "SELECT * FROM LeaveRequests WHERE Status = ?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, "Pending");
                rs = st.executeQuery();
            } else {
                sql = "SELECT * FROM LeaveRequests WHERE ManagerID = ? AND Status = ?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setInt(1, employee.getEmployeeid());
                st.setString(2, "Pending");
                rs = st.executeQuery();
            }
            while (rs.next()) {
                LeaveRequest leaveRequest = new LeaveRequest();
                leaveRequest.setRequestid(rs.getInt("RequestID"));
                leaveRequest.setEmployeeid(rs.getInt("EmployeeID"));
                leaveRequest.setManagerid(rs.getInt("ManagerID"));
                leaveRequest.setStartdate(rs.getDate("StartDate"));
                leaveRequest.setEnddate(rs.getDate("EndDate"));
                leaveRequest.setReason(rs.getString("Reason"));
                leaveRequest.setStatus(rs.getString("Status"));
                leaveRequest.setRequestdate(rs.getDate("RequestDate"));
                l.add(leaveRequest);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!l.isEmpty()) {
            return l;
        }
        return null;
    }

    public boolean updateStatusRequest(String newStatus, int requestId) {
        String sql = "UPDATE LeaveRequests set Status = ? where RequestID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newStatus);
            st.setInt(2, requestId);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public ArrayList getApprovedLeaveRequest() {
        String sql = "select le.EmployeeID, e.FullName, e.Department, e.ManagerID, le.StartDate, le.EndDate from LeaveRequests le\n"
                + "join Employees e\n"
                + "on le.EmployeeID = e.EmployeeID\n"
                + "where le.Status = ?";
        ArrayList<EmployeeAgenda> list = new ArrayList();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "Approved");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                EmployeeAgenda ea = new EmployeeAgenda();
                ea.setEmployeeid(rs.getInt("EmployeeID"));
                ea.setFullname(rs.getString("FullName"));
                ea.setDepartment(rs.getString("Department"));
                ea.setManagerid(rs.getInt("ManagerID"));
                ea.setStartdate(rs.getDate("StartDate"));
                ea.setEnddate(rs.getDate("EndDate"));
                list.add(ea);
            }
        } catch (SQLException e) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}
