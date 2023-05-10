package com.blackburn.dao;

import com.blackburn.model.Cat;
import com.blackburn.model.CatOwner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class CatDaoFragmentImpl implements CatDaoFragment {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Cat> findByName(String name) {
        CriteriaBuilder cb_ = em.getCriteriaBuilder();
        CriteriaQuery<Cat> cq = cb_.createQuery(Cat.class);
        Root<Cat> root_ = cq.from(Cat.class);
        cq.select(root_).where(cb_.like(root_.get("name"), name));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Cat> findByDate(Date date) {
        CriteriaBuilder cb_ = em.getCriteriaBuilder();
        CriteriaQuery<Cat> cq = cb_.createQuery(Cat.class);
        Root<Cat> root_ = cq.from(Cat.class);
        cq.select(root_).where(cb_.equal(root_.get("dateOfBirth"), date));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Cat> findByBreed(String breed) {
        CriteriaBuilder cb_ = em.getCriteriaBuilder();
        CriteriaQuery<Cat> cq = cb_.createQuery(Cat.class);
        Root<Cat> root_ = cq.from(Cat.class);
        cq.select(root_).where(cb_.like(root_.get("breed"), breed));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Cat> findByColor(String color) {
        CriteriaBuilder cb_ = em.getCriteriaBuilder();
        CriteriaQuery<Cat> cq = cb_.createQuery(Cat.class);
        Root<Cat> root_ = cq.from(Cat.class);
        cq.select(root_).where(cb_.like(root_.get("color"), color));
        return em.createQuery(cq).getResultList();
    }

}
