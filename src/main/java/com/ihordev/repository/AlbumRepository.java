package com.ihordev.repository;

import com.ihordev.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumRepositoryCustom {

}
