package com.example.coursach.repository.custom.impl;

import com.example.coursach.entity.User;
import com.example.coursach.entity.User_;
import com.example.coursach.entity.enums.AccountStatus;
import com.example.coursach.repository.custom.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private static final String LOADGRAPH = "javax.persistence.loadgraph";

    private final EntityManager em;

    @Override
    public Page<User> findAllByStatusAndEmailOrNickname(String queryStr, AccountStatus accountStatus, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
                .where(
                        getFindByStatusAndEmailOrFirstNameOrLastNamePredicate(cb, root, queryStr, accountStatus)
                );

        TypedQuery<User> query = em.createQuery(cq);
        query.setHint(LOADGRAPH, getEntityGraphWithFetchToProfile());
        List<User> users = query
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();

        if (CollectionUtils.isEmpty(users)) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> userRootCount = countQuery.from(User.class);

        countQuery.select(cb.count(userRootCount))
                .where(
                        getFindByStatusAndEmailOrFirstNameOrLastNamePredicate(cb, userRootCount, queryStr, accountStatus)
                );

        TypedQuery<Long> tq = em.createQuery(countQuery);
        return new PageImpl<>(users, pageable, tq.getSingleResult());
    }

    @Override
    public Optional<User> findUserByIdWithFetchProfile(String uuid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
                .where(
                        cb.equal(root.get(User_.ID), uuid)
                );

        TypedQuery<User> query = em.createQuery(cq);
        query.setHint(LOADGRAPH, getEntityGraphWithFetchToProfile());
        List<User> resultList = query.getResultList();
        return resultList.stream().findFirst();
    }

    @Override
    public Optional<User> findUserByEmailWithFetchProfile(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
                .where(
                        cb.equal(root.get(User_.EMAIL), email)
                );

        TypedQuery<User> query = em.createQuery(cq);
        query.setHint(LOADGRAPH, getEntityGraphWithFetchToProfile());
        List<User> resultList = query.getResultList();
        return resultList.stream().findFirst();
    }

    @Override
    public Optional<User> findUserByIdWithFetchNotification(String userUuid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
                .where(
                        cb.equal(root.get(User_.ID), userUuid)
                );

        TypedQuery<User> query = em.createQuery(cq);
        query.setHint(LOADGRAPH, getEntityGraphWithFetchToNotification());
        List<User> resultList = query.getResultList();
        return resultList.stream().findFirst();
    }

    @Override
    public List<User> findAllByIds(List<String> userIds) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
                .where(
                        root.get(User_.ID).in(userIds)
                );

        TypedQuery<User> query = em.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public Optional<User> findUserByEmailExcludedInvitedWithFetchProfile(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
                .where(
                        cb.equal(root.get(User_.EMAIL), email),
                        cb.notEqual(root.get(User_.ACCOUNT_STATUS), AccountStatus.INVITED)
                );

        TypedQuery<User> query = em.createQuery(cq);
        query.setHint(LOADGRAPH, getEntityGraphWithFetchToProfile());
        List<User> resultList = query.getResultList();
        return resultList.stream().findFirst();
    }

    private EntityGraph<User> getEntityGraphWithFetchToProfile() {
        EntityGraph<User> entityGraph = em.createEntityGraph(User.class);
        entityGraph.addAttributeNodes(User_.PROFILE);
        return entityGraph;
    }
    private EntityGraph<User> getEntityGraphWithFetchToNotification() {
        EntityGraph<User> entityGraph = em.createEntityGraph(User.class);
        entityGraph.addAttributeNodes(User_.NOTIFICATIONS);
        return entityGraph;
    }
    private Predicate getFindByStatusAndEmailOrFirstNameOrLastNamePredicate(
            CriteriaBuilder cb, Root<User> root, String queryStr, AccountStatus accountStatus) {
        String percentStr = "%";
        return cb.and(
                cb.or(
                        cb.like(cb.lower(root.get(User_.EMAIL)), queryStr.toLowerCase() + percentStr)
                ),
                cb.equal(root.get(User_.ACCOUNT_STATUS), accountStatus)
        );
    }

}
