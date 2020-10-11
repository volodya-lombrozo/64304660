package com.example0;

import javax.persistence.*;
import java.util.List;

@Entity
class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<DataA> lazyCollectionA;

    @OneToMany
    private List<DataB> lazyCollectionB;


    public BaseModel() {
    }

    public BaseModel(long id, List<DataA> lazyCollectionA, List<DataB> lazyCollectionB) {
        this.id = id;
        this.lazyCollectionA = lazyCollectionA;
        this.lazyCollectionB = lazyCollectionB;
    }

    public long getId() {
        return id;
    }

    public List<DataA> getLazyCollectionA() {
        return lazyCollectionA;
    }

    public List<DataB> getLazyCollectionB() {
        return lazyCollectionB;
    }

    public void setLazyCollectionA(List<DataA> lazyCollectionA) {
        this.lazyCollectionA = lazyCollectionA;
    }

    public void setLazyCollectionB(List<DataB> lazyCollectionB) {
        this.lazyCollectionB = lazyCollectionB;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", lazyCollectionA=" + lazyCollectionA +
                ", lazyCollectionB=" + lazyCollectionB +
                '}';
    }
}
