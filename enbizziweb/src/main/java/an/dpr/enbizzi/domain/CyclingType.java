package an.dpr.enbizzi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CyclingType {

    private int cyclingTypeId;
    private String name;
    /**
     * @return the difficultyId
     */
    @Id
    @Column
    public int getCyclingTypeId() {
        return cyclingTypeId;
    }
    /**
     * @param difficultyId the difficultyId to set
     */
    public void setCyclingTypeId(int cyclingTypeId) {
        this.cyclingTypeId = cyclingTypeId;
    }
    /**
     * @return the name
     */
    @Column
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
