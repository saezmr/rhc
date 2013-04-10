package an.dpr.enbizzi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="calendar_versions")
public class CalendarVersion {

    private Long versionId;
    private Boolean active;
    
    /**
     * @return the versionId
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Long getVersionId() {
        return versionId;
    }
    /**
     * @param versionId the versionId to set
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }
    /**
     * @return the active
     */
    @Column
    public Boolean getActive() {
        return active;
    }
    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
}
