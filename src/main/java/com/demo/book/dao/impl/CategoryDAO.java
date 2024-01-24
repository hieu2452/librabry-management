package com.demo.book.dao.impl;

import com.demo.book.dao.BaseDaoClass.BaseRepository;
import com.demo.book.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Objects;
import java.util.Optional;

public class CategoryDAO extends BaseRepository<Category,Long> {
    public static CategoryDAO instance;
    public static CategoryDAO getInstance() {
        if(instance==null) {
            instance = new CategoryDAO();
        }
        return instance;
    }
    @Override
    public Optional<Category> save(Category obj) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            if (!Objects.isNull(obj.getId())) {
                em.merge(obj);
            } else {
                em.persist(obj);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e; // or display error message
        } finally {
            em.close();
        }
        return Optional.of(obj);
    }

    @Override
    public Optional<Category> findById(Long key) {
        return Optional.empty();
    }

    @Override
    public void delete(Category obj) {

    }
}
