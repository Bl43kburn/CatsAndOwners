package com.blackburn.dao;

import com.blackburn.model.CatOwner;
import com.blackburn.model.TransferRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CatTransferRequestFragmentImpl implements CatTransferRequestFragment {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TransferRequest> getRequestsForSpecifiedUser(CatOwner owner) {
        CriteriaBuilder cb_ = em.getCriteriaBuilder();
        CriteriaQuery<TransferRequest> cq = cb_.createQuery(TransferRequest.class);
        Root<TransferRequest> root_ = cq.from(TransferRequest.class);


        Predicate sender = cb_.equal(root_.get("sender"), owner);
        Predicate receiver = cb_.equal(root_.get("receiver"), owner);
        cq.select(root_).where(cb_.or(sender, receiver));

        return em.createQuery(cq).getResultList();
    }
}
