package com.dpi.financial.ftcom.model.to;


import com.dpi.financial.ftcom.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "Report_SEQ")
@Table(name = "Report")
@NamedQuery(name = Report.FIND_BY_CODE, query = "select r from Report r where r.reportCode = :code")
public class Report extends BaseEntity {

    public static final String FIND_BY_CODE = "Report.findByCode";

    @Column(name = "REPORT_CODE", unique = true, nullable = false)
    private String reportCode;

    @Column(name = "REPORT_NAME", nullable = false)
    private String reportName;

    @Column(name = "REPORT_LOCATION", nullable = false)
    private String reportLocation;

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(String reportLocation) {
        this.reportLocation = reportLocation;
    }
}
