package com.ihordev.repository;

import com.ihordev.domain.Artist;
import com.ihordev.domainprojections.ArtistAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArtistRepository extends JpaRepository<Artist, Long>, ArtistRepositoryCustom {

}
