
package model;

import java.sql.Date;
public class LeavePeriod {
    private Date startDate;
    private Date endDate;

    public LeavePeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
