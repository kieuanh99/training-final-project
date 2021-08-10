package project.practice.document.models;

public class DataCA {
    private DataActivity[] dataActivity;
    private String title;
    private String projectName;
    private String projectId;
    private String auditDate;
    private String customer;
    private String items;
    private String auditTeam;
    private String remarks;
    private String today;


    public DataActivity[] getDataActivity() {
        return dataActivity;
    }

    public void setDataActivity(DataActivity[] dataActivity) {
        this.dataActivity = dataActivity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getAuditTeam() {
        return auditTeam;
    }

    public void setAuditTeam(String auditTeam) {
        this.auditTeam = auditTeam;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
