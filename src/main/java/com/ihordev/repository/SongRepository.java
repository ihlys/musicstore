package com.ihordev.repository;

import com.ihordev.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {

}
