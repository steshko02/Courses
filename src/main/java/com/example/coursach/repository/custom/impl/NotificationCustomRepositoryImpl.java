package com.example.coursach.repository.custom.impl;

import com.example.coursach.entity.Notification;
import com.example.coursach.repository.custom.NotificationCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationCustomRepositoryImpl implements NotificationCustomRepository {

    private final EntityManager em;

    @Override
    public Page<Notification> getAllNotificationsOfUser(String userUuid, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);
        Root<Notification> root = cq.from(Notification.class);

        cq.select(root)
                .where(
                        getAllNotificationsOfUserPredicate(cb, root, userUuid)
                );

        TypedQuery<Notification> query = em.createQuery(cq);
        List<Notification> resultList = query
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        if (CollectionUtils.isEmpty(resultList)) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Notification> nRoot = countQuery.from(Notification.class);

        countQuery.select(cb.count(nRoot))
                .where(
                        getAllNotificationsOfUserPredicate(cb, nRoot, userUuid)
                );

        TypedQuery<Long> tq = em.createQuery(countQuery);
        return new PageImpl<>(resultList, pageable, tq.getSingleResult());
    }

    @Override
    public void deleteAllOlderThan(LocalDateTime dateTime) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Notification> delete = cb.createCriteriaDelete(Notification.class);
        Root<Notification> root = delete.from(Notification.class);

        delete.where(cb.lessThanOrEqualTo(root.get(UserAcceptInvitationNotification_.DATE), dateTime));

        em.createQuery(delete).executeUpdate();
    }

    @Override
    public Optional<Notification> findByUserUuidAndNotificationUuid(String userId, String notificationId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Notification> cqForNotification = cb.createQuery(Notification.class);
        Root<Notification> root = cqForNotification.from(Notification.class);
        cqForNotification.select(root)
                .where(
                        cb.equal(
                                root.get(Notification_.UUID),
                                notificationId
                        ),
                        cb.equal(
                                root.join(Notification_.USERS, JoinType.LEFT).get(User_.ID),
                                userId
                        )
                );

        TypedQuery<Notification> tq = em.createQuery(cqForNotification);
        List<Notification> resultList = tq.getResultList();
        return resultList.stream().findFirst();
    }

    private Predicate getAllNotificationsOfUserPredicate(CriteriaBuilder cb, Root<Notification> root, String userUuid) {
        return cb.equal(
                root.join(UserAcceptInvitationNotification_.users, JoinType.LEFT).get(User_.ID),
                userUuid
        );
    }
}