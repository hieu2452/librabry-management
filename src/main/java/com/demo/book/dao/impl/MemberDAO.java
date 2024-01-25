package com.demo.book.dao.impl;

import com.demo.book.dao.BaseDaoClass.BaseRepository;
import com.demo.book.dto.UserDto;
import com.demo.book.entity.Member;
import com.demo.book.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.NotSupportedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberDAO extends BaseRepository<User,Long> {
    public static MemberDAO instance;
    public static MemberDAO getInstance() {
        if(instance==null) {
            instance = new MemberDAO();
        }
        return instance;
    }
    @Override
    public Optional<User> save(User obj) {
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
            throw e;
        } finally {
            em.close();
        }
        return Optional.of(obj);
    }

    @Override
    public Optional<User> findById(Long key) {
        return Optional.empty();
    }

    @Override
    public void delete(User obj) {

    }

    public List<UserDto> findAll() {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> select = builder.createQuery(User.class);
        Root<User> root = select.from(User.class);
        select.select(root);
        TypedQuery<User> query = em.createQuery(select);

        return query.getResultList().stream().map(
                user -> new UserDto.Builder().id(user.getId()).address(user.getAddress()).userType(user.getUserType())
                        .email(user.getEmail()).age(user.getAge()).displayName(user.getDisplayName()).fullName(user.getFullName())
                        .build()).collect(Collectors.toList());
    }

    public List<UserDto> findMembers(Integer minAge,Integer maxAge,String userType) throws NotSupportedException {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(root.get("userType"), "MEMBER"));

        if (minAge > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("age"), minAge));
        }

        if (maxAge > 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("age"), maxAge));
        }

        if (!userType.equals("")) {
            predicates.add(criteriaBuilder.equal(root.get("userType"), userType));
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        } else {
            query.select(root);
        }

        return em.createQuery(query).getResultList().stream().map(
                user -> new UserDto.Builder().id(user.getId()).address(user.getAddress()).userType(user.getUserType())
                        .email(user.getEmail()).age(user.getAge()).displayName(user.getDisplayName()).fullName(user.getFullName())
                        .build()).collect(Collectors.toList()
        );
    }
}
