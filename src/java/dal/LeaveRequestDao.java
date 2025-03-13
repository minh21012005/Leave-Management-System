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

    // Lấy danh sách yêu cầu nghỉ phép của chính nhân viên
    public ArrayList<LeaveRequest> getMyRequest(Employee employee) {
        String sql = "SELECT * FROM LeaveRequests WHERE EmployeeID = ?";
        ArrayList<LeaveRequest> list = new ArrayList<>();
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
        return list.isEmpty() ? null : list;
    }
    
    public ArrayList<LeaveRequest> getMyRequest(Employee employee, int pageindex, int pagesize) {
        String sql = "SELECT * FROM LeaveRequests WHERE EmployeeID = ?\n"
                + "order by RequestID\n"
                + "offset (?-1)*? rows \n"
                + "fetch next ? rows only";
        ArrayList<LeaveRequest> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, employee.getEmployeeid());
            st.setInt(2, pageindex);
            st.setInt(3, pagesize);
            st.setInt(4, pagesize);
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
        return list.isEmpty() ? null : list;
    }

    // Lấy thông tin yêu cầu nghỉ phép theo RequestID
    public LeaveRequest getRequestById(int requestid) {
        String sql = "SELECT StartDate, EndDate FROM LeaveRequests WHERE RequestID = ?";
        LeaveRequest leaveRequest = new LeaveRequest();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, requestid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                leaveRequest.setStartdate(rs.getDate("StartDate"));
                leaveRequest.setEnddate(rs.getDate("EndDate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return leaveRequest;
    }

    // Thêm yêu cầu nghỉ phép mới
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

    // Lấy danh sách yêu cầu nghỉ phép của người khác (dành cho manager)
    public ArrayList<LeaveRequest> getOtherRequests(Employee employee) {
        ArrayList<LeaveRequest> list = new ArrayList<>();
        try {
            String sql;
            PreparedStatement st;
            if (employee.getEmployeeid() == 1) { // Admin
                sql = "SELECT * FROM LeaveRequests WHERE Status = ?";
                st = connection.prepareStatement(sql);
                st.setString(1, "Pending");
            } else { // Manager
                sql = "SELECT * FROM LeaveRequests WHERE ManagerID = ? AND Status = ?";
                st = connection.prepareStatement(sql);
                st.setInt(1, employee.getEmployeeid());
                st.setString(2, "Pending");
            }
            ResultSet rs = st.executeQuery();
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
                list.add(leaveRequest);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list.isEmpty() ? null : list;
    }

    // Cập nhật trạng thái yêu cầu nghỉ phép
    public boolean updateStatusRequest(String newStatus, int requestId) {
        String sql = "UPDATE LeaveRequests SET Status = ? WHERE RequestID = ?";
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

    // Lấy danh sách agenda không lọc
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
        Map<Integer, EmployeeAgenda> employeeMap = new HashMap<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "Approved");
            st.setDate(2, new java.sql.Date(weekEnd.getTime()));
            st.setDate(3, new java.sql.Date(weekStart.getTime()));
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int employeeId = rs.getInt("EmployeeID");
                EmployeeAgenda ea;

                if (employeeMap.containsKey(employeeId)) {
                    ea = employeeMap.get(employeeId);
                } else {
                    ea = new EmployeeAgenda();
                    ea.setEmployeeid(employeeId);
                    ea.setFullname(rs.getString("FullName"));
                    ea.setDepartment(rs.getString("Department"));
                    ea.setManagerid(rs.getInt("ManagerID"));
                    ea.setLeavePeriods(new ArrayList<>());
                    employeeMap.put(employeeId, ea);
                    list.add(ea);
                }

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

    // Lấy danh sách agenda với lọc theo employeeId và department
    public ArrayList<EmployeeAgenda> getEmployeeAgendaListFiltered(Date weekStart, Date weekEnd, String employeeId, String department) {
        StringBuilder sql = new StringBuilder(
            "SELECT e.EmployeeID, e.FullName, e.Department, e.ManagerID, "
            + "le.StartDate, le.EndDate "
            + "FROM Employees e "
            + "LEFT JOIN LeaveRequests le "
            + "ON le.EmployeeID = e.EmployeeID "
            + "AND le.Status = ? "
            + "AND (le.StartDate <= ? AND le.EndDate >= ?) "
            + "WHERE e.EmployeeID != 1 "
        );

        if (employeeId != null && !employeeId.trim().isEmpty()) {
            sql.append("AND e.EmployeeID = ? ");
        }
        if (department != null && !department.trim().isEmpty()) {
            sql.append("AND e.Department = ? ");
        }
        sql.append("ORDER BY e.EmployeeID");

        ArrayList<EmployeeAgenda> list = new ArrayList<>();
        Map<Integer, EmployeeAgenda> employeeMap = new HashMap<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql.toString());
            int paramIndex = 1;
            st.setString(paramIndex++, "Approved");
            st.setDate(paramIndex++, new java.sql.Date(weekEnd.getTime()));
            st.setDate(paramIndex++, new java.sql.Date(weekStart.getTime()));

            if (employeeId != null && !employeeId.trim().isEmpty()) {
                st.setInt(paramIndex++, Integer.parseInt(employeeId.trim()));
            }
            if (department != null && !department.trim().isEmpty()) {
                st.setString(paramIndex++, department.trim());
            }

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int empId = rs.getInt("EmployeeID");
                EmployeeAgenda ea;

                if (employeeMap.containsKey(empId)) {
                    ea = employeeMap.get(empId);
                } else {
                    ea = new EmployeeAgenda();
                    ea.setEmployeeid(empId);
                    ea.setFullname(rs.getString("FullName"));
                    ea.setDepartment(rs.getString("Department"));
                    ea.setManagerid(rs.getInt("ManagerID"));
                    ea.setLeavePeriods(new ArrayList<>());
                    employeeMap.put(empId, ea);
                    list.add(ea);
                }

                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");
                if (startDate != null && endDate != null) {
                    ea.getLeavePeriods().add(new LeavePeriod(startDate, endDate));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, null, e);
        } catch (NumberFormatException e) {
            Logger.getLogger(LeaveRequestDao.class.getName()).log(Level.SEVERE, "Invalid employeeId format", e);
        }
        return list;
    }

    // Cập nhật thông tin yêu cầu nghỉ phép
    public boolean update(int id, String reason, Date startdate, Date enddate) {
        String sql = "UPDATE LeaveRequests SET StartDate = ?, EndDate = ?, Reason = ? WHERE RequestID = ?";
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

    // Xóa yêu cầu nghỉ phép
    public boolean delete(int id) {
        String sql = "DELETE FROM LeaveRequests WHERE RequestID = ?";
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