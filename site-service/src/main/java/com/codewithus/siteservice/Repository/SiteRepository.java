package com.codewithus.siteservice.Repository;

import com.codewithus.siteservice.Model.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findByName(String name);
    List<Site> findByAddress(String address);
    List<Site> findByIsParalympic(boolean isParalympic);

}
