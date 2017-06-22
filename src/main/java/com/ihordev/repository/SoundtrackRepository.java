package com.ihordev.repository;

import com.ihordev.domain.Soundtrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundtrackRepository extends JpaRepository<Soundtrack, Long>, SoundtrackRepositoryCustom {

}
