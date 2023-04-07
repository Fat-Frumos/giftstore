package com.epam.esm.dao;

import com.epam.esm.core.model.domain.User;
import com.epam.esm.core.model.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}