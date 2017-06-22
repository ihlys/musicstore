package com.ihordev.repository;


import com.ihordev.domain.ThematicCompilation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThematicCompilationRepository extends JpaRepository<ThematicCompilation, Long>,
                                                       ThematicCompilationRepositoryCustom {
}
