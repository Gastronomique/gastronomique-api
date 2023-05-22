package com.ifpr.gastronomique.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpr.gastronomique.security.models.User;

public interface AdminRepository extends JpaRepository<User, Long>{

}
