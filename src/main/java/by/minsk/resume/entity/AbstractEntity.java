package by.minsk.resume.entity;

import by.minsk.resume.model.AbstractModel;

import java.io.Serializable;


public abstract class AbstractEntity<T> extends AbstractModel implements Serializable {
    public abstract T getId();

    @Override
    public int hashCode() {
        final int prime = 31;//WTF
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractEntity<T> other = (AbstractEntity<T>) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }
    @Override
    public String toString(){
        return String.format("%s[id=%s]", getClass().getSimpleName(),  getId());
    }
}
