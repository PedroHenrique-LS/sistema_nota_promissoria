package com.faculdade.sistema_nota_promissoria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculdade.sistema_nota_promissoria.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String userName);
}
