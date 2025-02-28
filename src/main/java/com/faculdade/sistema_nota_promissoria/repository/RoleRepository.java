package com.faculdade.sistema_nota_promissoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculdade.sistema_nota_promissoria.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByNome(String name);
}
