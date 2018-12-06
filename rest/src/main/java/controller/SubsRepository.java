package controller;

import org.springframework.data.repository.CrudRepository;

import controller.Subs;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SubsRepository extends CrudRepository<Subs, Integer> {

}