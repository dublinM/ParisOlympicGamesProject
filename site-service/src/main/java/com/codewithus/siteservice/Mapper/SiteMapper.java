package com.codewithus.siteservice.Mapper;

import com.codewithus.siteservice.Dto.SiteDto;
import com.codewithus.siteservice.Model.Site;

public class SiteMapper {

    public static SiteDto mapToSiteDto(Site site) {
        return new SiteDto(
                site.getId(),
                site.getName(),
                site.isParalympic(),
                site.getAddress()
        );

    }

    public static Site mapToSite(SiteDto siteDto) {
        return new Site(
          siteDto.getId(),
          siteDto.getName(),
          siteDto.isParalympic(),
          siteDto.getAddress()
        );
    }
}
