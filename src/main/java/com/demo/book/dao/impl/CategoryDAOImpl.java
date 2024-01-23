package com.demo.book.dao.impl;

import com.demo.book.dao.BaseDaoClass.Dao;
import com.demo.book.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Objects;
import java.util.Optional;


public class CategoryDAOImpl {
//    @Override
//    public Optional<Category> save(Category category) {
//        Objects.requireNonNull(category, "Person must not be null");
//
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = null;
//        try {
//            tx = em.getTransaction();
//            tx.begin();
//            Category newCategory = new Category();
//            newCategory.setCategoryName(category.getCategoryName());
//            em.persist(category);
//
//            tx.commit();
//        } catch (RuntimeException e) {
//            if (tx != null && tx.isActive()) {
//                tx.rollback();
//            }
//            throw e; // or display error message
//        } finally {
//            em.close();
//        }
//        return Optional.of(category);
//    }
//
//    Optional<Category> findById(long key) {
//        return null;
//    }
//
//     void delete(Category obj) {
//
//     }
}
