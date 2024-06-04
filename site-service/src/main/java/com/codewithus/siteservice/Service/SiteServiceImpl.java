package com.codewithus.siteservice.Service;

import com.codewithus.siteservice.Dto.SiteDto;
import com.codewithus.siteservice.Exception.ResourceNotFoundException;
import com.codewithus.siteservice.Mapper.SiteMapper;
import com.codewithus.siteservice.Model.Site;
import com.codewithus.siteservice.Repository.SiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService{

    private SiteRepository siteRepository;

    @Override
    public SiteDto createSite(SiteDto siteDto) {

        Site site = SiteMapper.mapToSite(siteDto);
        Site savedSite = siteRepository.save(site);
        return SiteMapper.mapToSiteDto(savedSite);
    }

    @Override
    public SiteDto getSiteById(Long siteId) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("Site does not exist with given id : " + siteId));
        return SiteMapper.mapToSiteDto(site);
    }

    @Override
    public List<SiteDto> getAllSites() {
        List<Site> sites = siteRepository.findAll();

        return sites.stream().map((site) -> SiteMapper.mapToSiteDto(site))
                .collect(Collectors.toList());
    }

    @Override
    public SiteDto updateSite(Long id, SiteDto siteDto) {
        Site site = siteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Site does not exist with the given id : " + id)
        );

        site.setName(siteDto.getName());
        site.setAddress(siteDto.getAddress());
        site.setParalympic(siteDto.isParalympic());

        Site updatedSite = siteRepository.save(site);
        return SiteMapper.mapToSiteDto(updatedSite);
    }

    @Override
    public void deleteSite(Long id) {
        Site site = siteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Site does not exist with the given id : " + id)
        );
        siteRepository.deleteById(id);
    }

    @Override
    public SiteDto getSitesByName(String name) {
        Site site = siteRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Site not found with name: " + name));
        return SiteMapper.mapToSiteDto(site);
    }

    @Override
    public List<SiteDto> getSitesByAddress(String address) {
        List<Site> sites = siteRepository.findByAddress(address);
        return sites.stream().map(SiteMapper::mapToSiteDto).collect(Collectors.toList());
    }

    @Override
    public List<SiteDto> getSitesByIsParalympic(boolean isParalympic) {
        List<Site> sites = siteRepository.findByIsParalympic(isParalympic);
        return sites.stream().map(SiteMapper::mapToSiteDto).collect(Collectors.toList());
    }

    @Override
    public List<SiteDto> createSites(List<SiteDto> siteDtos) {
        List<Site> sites = siteDtos.stream()
                .map(SiteMapper::mapToSite)
                .collect(Collectors.toList());
        sites = siteRepository.saveAll(sites);
        return sites.stream()
                .map(SiteMapper::mapToSiteDto)
                .collect(Collectors.toList());
    }

    @Override
    public SiteDto updateSiteField(Long id, String fieldName, Object fieldValue) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Site does not exist with the given id: " + id));

        switch (fieldName) {
            case "name":
                site.setName((String) fieldValue);
                break;
            case "address":
                site.setAddress((String) fieldValue);
                break;
            case "isParalympic":
                site.setParalympic((Boolean) fieldValue);
                break;
            default:
                throw new IllegalArgumentException("Field name is not valid");
        }

        Site updatedSite = siteRepository.save(site);
        return SiteMapper.mapToSiteDto(updatedSite);
    }
}
