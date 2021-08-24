package by.minsk.resume.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hobby")
public class Hobby extends  AbstractEntity<Long> implements Serializable, Comparable<Hobby>, ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOBBY_ID_GENERATOR")
    @SequenceGenerator(name = "HOBBY_ID_GENERATOR", sequenceName = "HOBBY_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profile", nullable = false)
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    @Override
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    private boolean selected;


    public Hobby() {
    }


    public Hobby(String name) {
        super();
        this.name = name;
    }

    public Hobby(String name, boolean selected) {
        super();
        this.name = name;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Transient
    public String getCssClassName(){
        return name.replace(" ", "-").toLowerCase();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Hobby))
            return false;
        Hobby other = (Hobby) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int compareTo(Hobby o) {
        if(o == null || getName() == null) {
            return 1;
        } else{
            return getName().compareTo(o.getName());
        }
    }

    @Override
    public String toString() {
        return String.format("Hobby [name=%s]", name);
    }


}
