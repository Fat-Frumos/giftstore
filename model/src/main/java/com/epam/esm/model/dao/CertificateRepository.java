package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface CertificateRepository extends CrudRepository<Certificate, Long> {

    @EntityGraph(attributePaths = "tags")
    Page<Certificate> findAllBy(Pageable pageable);
}
