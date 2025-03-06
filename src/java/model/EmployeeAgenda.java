package model;
        
import java.util.List;

public class EmployeeAgenda {
    private int employeeid;
    private int managerid;
    private String department;
    private String fullname;
    private List<LeavePeriod> leavePeriods; // Danh sách các khoảng nghỉ

    public List<LeavePeriod> getLeavePeriods() {
        return leavePeriods;
    }

    public void setLeavePeriods(List<LeavePeriod> leavePeriods) {
        this.leavePeriods = leavePeriods;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public int getManagerid() {
        return managerid;
    }

    public void setManagerid(int managerid) {
        this.managerid = managerid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    
}