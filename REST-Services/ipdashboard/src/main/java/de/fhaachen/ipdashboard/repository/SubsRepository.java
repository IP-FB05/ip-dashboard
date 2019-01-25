package de.fhaachen.ipdashboard.repository;

import org.springframework.data.repository.CrudRepository;

import de.fhaachen.ipdashboard.model.Subs;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SubsRepository extends CrudRepository<Subs, Integer> {

}