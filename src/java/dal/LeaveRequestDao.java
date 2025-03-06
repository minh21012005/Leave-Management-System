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
import java.util.HashMap;
import java.util.Map;
import model.EmployeeAgenda;
import model.LeavePeriod;

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

    public LeaveRequest getRequestById(int requestid) {
        String sql = "SELECT [StartDate]\n"
                + "      ,[EndDate]\n"
                + "  FROM [dbo].[LeaveRequests] where RequestID = ?";
        LeaveRequest leaveRequest = new LeaveRequest();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, requestid);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                leaveRequest.setStartdate(rs.getDate("StartDate"));
                leaveRequest.setEnddate(rs.getDate("EndDate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return leaveRequest;
    }

    public boolean set(int employeeid, int managerid, Date startdate, Date endDate, String reason) {
        String sql = "INSERT INTO LeaveRequests (EmployeeID, ManagerID, StartDate, EndDate, Reason) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, employeeid);
            st.setInt(2, managerid);
            st.setDate(3, startdate);
            st.setDate(4, endDate);
            st.setString(5, reason);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
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

    public ArrayList<EmployeeAgenda> getEmployeeAgendaList(Date weekStart, Date weekEnd) {
        String sql = "SELECT e.EmployeeID, e.FullName, e.Department, e.ManagerID, "
                + "le.StartDate, le.EndDate "
                + "FROM Employees e "
                + "LEFT JOIN LeaveRequests le "
                + "ON le.EmployeeID = e.EmployeeID "
                + "AND le.Status = ? "
                + "AND (le.StartDate <= ? AND le.EndDate >= ?) "
                + "WHERE e.EmployeeID != 1 "
                + "ORDER BY e.EmployeeID";

        ArrayList<EmployeeAgenda> list = new ArrayList<>();
        Map<Integer, EmployeeAgenda> employeeMap = new HashMap<>(); // Để gộp nhân viên

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "Approved");
            st.setDate(2, new java.sql.Date(weekEnd.getTime()));
            st.setDate(3, new java.sql.Date(weekStart.getTime()));
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int employeeId = rs.getInt("EmployeeID");
                EmployeeAgenda ea;

                // Nếu nhân viên đã tồn tại trong map, lấy ra để cập nhật
                if (employeeMap.containsKey(employeeId)) {
                    ea = employeeMap.get(employeeId);
                } else {
                    // Tạo mới EmployeeAgenda nếu chưa tồn tại
                    ea = new EmployeeAgenda();
                    ea.setEmployeeid(employeeId);
                    ea.setFullname(rs.getString("FullName"));
                    ea.setDepartment(rs.getString("Department"));
                    ea.setManagerid(rs.getInt("ManagerID"));
                    ea.setLeavePeriods(new ArrayList<>()); // Thêm danh sách các khoảng nghỉ
                    employeeMap.put(employeeId, ea);
                    list.add(ea); // Thêm vào danh sách kết quả
                }

                // Thêm khoảng thời gian nghỉ phép nếu có
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");
                if (startDate != null && endDate != null) {
                    ea.getLeavePeriods().add(new LeavePeriod(startDate, endDate));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public boolean update(int id, String reason, Date startdate, Date enddate) {
        String sql = "UPDATE [dbo].[LeaveRequests]\n"
                + "   SET StartDate = ?, EndDate = ?, Reason= ?\n"
                + " WHERE RequestID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDate(1, startdate);
            st.setDate(2, enddate);
            st.setString(3, reason);
            st.setInt(4, id);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM [dbo].[LeaveRequests]\n"
                + "      WHERE RequestID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
