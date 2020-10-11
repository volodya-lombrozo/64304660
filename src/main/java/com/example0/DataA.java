package com.example0;

import javax.persistence.*;

@Entity
public class DataA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private OtherEntity otherEntity;

    public long getId() {
        return id;
    }

    public OtherEntity getOtherEntity() {
        return otherEntity;
    }

    public void setOtherEntity(OtherEntity otherEntity) {
        this.otherEntity = otherEntity;
    }

    @Override
    public String toString() {
        return "DataA{" +
                "id=" + id +
                ", otherEntity=" + otherEntity +
                '}';
    }
}
