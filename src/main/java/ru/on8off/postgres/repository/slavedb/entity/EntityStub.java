package ru.on8off.postgres.repository.slavedb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Entity
@Getter @Setter
public class EntityStub{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Integer id;

    public EntityStub(){

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
            EntityStub other = (EntityStub)object;
            return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + this.getId() + ")";
    }
}
