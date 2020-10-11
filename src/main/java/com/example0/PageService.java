package com.example0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PageService {

    @Autowired
    private EntityManager em;

    @Transactional
    public BaseModel saveEntities() {
        BaseModel baseModel = new BaseModel();
        baseModel.setLazyCollectionA(initACollection());
        baseModel.setLazyCollectionB(initBCollection());
        em.persist(baseModel);
        return baseModel;
    }

    private List<DataA> initACollection() {
        return Stream.generate(this::createDataA).limit(2).collect(Collectors.toList());
    }

    private DataA createDataA() {
        DataA data = new DataA();
        data.setOtherEntity(createOtherEntity());
        em.persist(data);
        return data;
    }

    private OtherEntity createOtherEntity() {
        OtherEntity otherEntity = new OtherEntity();
        otherEntity.setLazyCollectionC(initCCollection());
        em.persist(otherEntity);
        return otherEntity;
    }

    private List<DataC> initCCollection() {
        return Stream.generate(DataC::new).limit(2).peek(em::persist).collect(Collectors.toList());
    }

    private List<DataB> initBCollection() {
        return Stream.generate(DataB::new).limit(2).peek(em::persist).collect(Collectors.toList());
    }

    @Transactional
    public List<BaseModel> getAllAndJoinAll_makesMultipleBagFetchException() {
        return em.createQuery(
                "select m from BaseModel m " +
                        "left join fetch m.lazyCollectionB b " +
                        "left join fetch m.lazyCollectionA a " +
                        "left join fetch a.otherEntity o " +
                        "left join fetch o.lazyCollectionC " +
                        "where m.id in (:ids) ", BaseModel.class)
                .setParameter("ids", Arrays.asList(1L, 2L))
                .getResultList();
    }

    @Transactional
    public List<BaseModel> getAllByTwoQueries_collectionAlazilyInitializeCollectionException() {
        List<Long> ids = Arrays.asList(1L);
        List<BaseModel> first = em.createQuery(
                "select distinct m from BaseModel m " +
                        "left join fetch m.lazyCollectionB " +
                        "where m.id in (:ids) ", BaseModel.class)
                .setParameter("ids", ids)
                .getResultList();
        em.createQuery(
                "select distinct a from BaseModel m " +
                        "left join m.lazyCollectionA a " +
                        "left join fetch a.otherEntity o " +
                        "left join fetch o.lazyCollectionC " +
                        "where m in (:models) ", DataA.class)
                .setParameter("models", first).getResultList();
        return first;
    }

    @Transactional
    public List<BaseModel> getAllByThreeQueries() {
        List<Long> ids = Arrays.asList(1L);
        List<BaseModel> first = em.createQuery(
                "select distinct m from BaseModel m " +
                        "left join fetch m.lazyCollectionB " +
                        "where m.id in (:ids) ", BaseModel.class)
                .setParameter("ids", ids)
                .getResultList();
        List<BaseModel> second = em.createQuery(
                "select distinct m from BaseModel m " +
                        "left join fetch m.lazyCollectionA a " +
                        "left join fetch a.otherEntity o " +
                        "where m in (:models) ", BaseModel.class)
                .setParameter("models", first)
                .getResultList();
        em.createQuery("select distinct a from BaseModel m " +
                "left join m.lazyCollectionA a " +
                "left join fetch a.otherEntity o " +
                "left join fetch o.lazyCollectionC " +
                "where m in (:models) ", DataA.class)
                .setParameter("models", second)
                .getResultList();
        return second;
    }

}
