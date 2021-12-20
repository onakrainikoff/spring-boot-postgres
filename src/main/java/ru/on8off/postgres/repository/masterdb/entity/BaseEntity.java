package ru.on8off.postgres.repository.masterdb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.Objects;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity<ID>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected ID id;

    @Column(name = "date_created", nullable = false)
    protected ZonedDateTime dateCreated = ZonedDateTime.now();

    public BaseEntity(){

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public boolean equals(Object object) {
        if(!this.getClass().isInstance(object)) {
            return false;
        } else {
            BaseEntity other = (BaseEntity)object;
            return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + this.getId() + ")";
    }
}
