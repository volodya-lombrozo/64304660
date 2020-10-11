package com.example0;

import javax.persistence.*;
import java.util.List;

@Entity
public class OtherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<DataC> lazyCollectionC;

    public long getId() {
        return id;
    }

    public List<DataC> getLazyCollectionC() {
        return lazyCollectionC;
    }

    public void setLazyCollectionC(List<DataC> lazyCollectionC) {
        this.lazyCollectionC = lazyCollectionC;
    }

    @Override
    public String toString() {
        return "OtherEntity{" +
                "id=" + id +
                ", lazyCollectionC=" + lazyCollectionC +
                '}';
    }
}
