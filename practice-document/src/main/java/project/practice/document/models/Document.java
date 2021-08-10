package project.practice.document.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

enum EStatus{
    NEW, APPROVED
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;

    @NotBlank
    @Size(max = 500)
    private String title;

    @NotBlank
    private Long userId;

    @NotBlank
    @Column(name="updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Column(columnDefinition="TEXT")
    private String docData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public String getDocData() {
        return docData;
    }

    public void setDocData(String docData) {
        this.docData = docData;
    }


}
