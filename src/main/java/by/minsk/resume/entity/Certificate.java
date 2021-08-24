package by.minsk.resume.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "certificate")
@Entity
public class Certificate extends AbstractEntity<Long> implements Serializable,ProfileEntity {
    private static final long serialVersionUID = 8834021477498329665L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CERTIFICATE_ID_GENERATOR")
    @SequenceGenerator(name = "CERTIFICATE_ID_GENERATOR", sequenceName = "CERTIFICATE_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "large_url", nullable = false)
    private String largeUrl;

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    @Column(name = "small_url", nullable = false)
    private String smallUrl;

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Certificate() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profile", nullable = false)
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Certificate))
            return false;
        Certificate other = (Certificate) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (largeUrl == null) {
            if (other.largeUrl != null)
                return false;
        } else if (!largeUrl.equals(other.largeUrl))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (smallUrl == null) {
            if (other.smallUrl != null)
                return false;
        } else if (!smallUrl.equals(other.smallUrl))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((largeUrl == null) ? 0 : largeUrl.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((smallUrl == null) ? 0 : smallUrl.hashCode());
        return result;
    }
}