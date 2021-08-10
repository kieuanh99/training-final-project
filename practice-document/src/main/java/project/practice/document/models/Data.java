package project.practice.document.models;

public class Data {
    private String title;
    private String performedBy;
    private String discussedWith;
    private String problemDescription;
    private String impact;
    private String rootCause;
    private String correctiveActions;
    private String defineTheProblem;
    private DataArray[] why;
    private String carType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getDiscussedWith() {
        return discussedWith;
    }

    public void setDiscussedWith(String discussedWith) {
        this.discussedWith = discussedWith;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public String getCorrectiveActions() {
        return correctiveActions;
    }

    public void setCorrectiveActions(String correctiveActions) {
        this.correctiveActions = correctiveActions;
    }

    public String getDefineTheProblem() {
        return defineTheProblem;
    }

    public void setDefineTheProblem(String defineTheProblem) {
        this.defineTheProblem = defineTheProblem;
    }

    public DataArray[] getWhy() {
        return why;
    }

    public void setWhy(DataArray[] why) {
        this.why = why;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}



