package com.epam.esm.model.dao;

import com.epam.esm.model.domain.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @NonNull
    @Override
    Optional<User> findById(@NonNull final Long id);
}