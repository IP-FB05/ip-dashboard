package controller;

import org.springframework.data.repository.CrudRepository;

import controller.Usergroup;;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UsergroupRepository extends CrudRepository<Usergroup, Integer> {

}