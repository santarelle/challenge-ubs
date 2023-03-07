package com.ubs.batch.repository;

import com.ubs.batch.domain.entity.FileImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileImportRepository extends JpaRepository<FileImport, Long> {

    Optional<FileImport> findByName(String name);

}
