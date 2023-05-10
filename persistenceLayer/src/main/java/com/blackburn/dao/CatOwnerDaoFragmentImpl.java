package com.blackburn.dao;

import com.blackburn.model.Cat;
import com.blackburn.model.CatOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class CatOwnerDaoFragmentImpl implements CatOwnerDaoFragment {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<CatOwner> findByName(String name) {
        CriteriaBuilder cb_ = em.getCriteriaBuilder();
        CriteriaQuery<CatOwner> cq = cb_.createQuery(CatOwner.class);
        Root<CatOwner> root_ = cq.from(CatOwner.class);
        cq.select(root_).where(cb_.like(root_.get("name"), name));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<CatOwner> findByDate(Date date) {
        CriteriaBuilder cb_ = em.getCriteriaBuilder();
        CriteriaQuery<CatOwner> cq = cb_.createQuery(CatOwner.class);
        Root<CatOwner> root_ = cq.from(CatOwner.class);
        Path<Date> dateOfBirth = root_.get("dateOfBirth");

        cq.select(root_).where(cb_.equal(dateOfBirth, date));
        return em.createQuery(cq).getResultList();
    }
}
