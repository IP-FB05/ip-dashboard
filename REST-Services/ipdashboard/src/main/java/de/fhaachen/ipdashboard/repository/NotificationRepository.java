package de.fhaachen.ipdashboard.repository;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

import de.fhaachen.ipdashboard.model.Notification;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    public Boolean existsByUsername(String username);

    @Transactional
    Long deleteByUsername(String username);

}