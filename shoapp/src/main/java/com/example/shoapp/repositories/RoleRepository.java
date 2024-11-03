package com.example.shoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoapp.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
