package com.codewithus.siteservice.Service;

import com.codewithus.siteservice.Dto.SiteDto;

import java.util.List;

public interface SiteService {

    SiteDto createSite(SiteDto siteDto);

    SiteDto getSiteById(Long siteId);

    List<SiteDto> getAllSites();

    SiteDto updateSite(Long id, SiteDto siteDto);

    void deleteSite(Long id);

    SiteDto getSitesByName(String name);

    List<SiteDto> getSitesByAddress(String address);

    List<SiteDto> getSitesByIsParalympic(boolean isParalympic);

    List<SiteDto> createSites(List<SiteDto> siteDtos);

    SiteDto updateSiteField(Long id, String fieldName, Object fieldValue);
}
