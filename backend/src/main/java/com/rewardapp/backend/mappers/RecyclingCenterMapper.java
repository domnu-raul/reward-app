package com.rewardapp.backend.mappers;

import com.rewardapp.backend.dto.LocationDTO;
import com.rewardapp.backend.dto.RecyclingCenterDTO;
import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.entities.RecyclingCenterLocation;
import com.rewardapp.backend.entities.RecyclingCenterMaterial;
import com.rewardapp.backend.repositories.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RecyclingCenterMapper implements Mapper<RecyclingCenter, RecyclingCenterDTO> {
    private final MaterialRepository materialRepository;

    public RecyclingCenterMapper(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public RecyclingCenterDTO mapToDTO(RecyclingCenter recyclingCenter) {
        return new RecyclingCenterDTO(
                recyclingCenter.getId(),
                recyclingCenter.getName(),
                recyclingCenter.getRecyclingCenterMaterials()
                        .stream()
                        .map(recyclingCenterMaterial -> recyclingCenterMaterial.getMaterial().getName())
                        .collect(Collectors.toList()),
                getLocationDTO(recyclingCenter),
                recyclingCenter.getStartingTime(),
                recyclingCenter.getEndTime()
        );
    }

    @Override
    public RecyclingCenter mapToEntity(RecyclingCenterDTO recyclingCenterDTO) {
        RecyclingCenter recyclingCenter = new RecyclingCenter();
        recyclingCenter.setName(recyclingCenterDTO.name());
        recyclingCenter.setRecyclingCenterMaterials(recyclingCenterDTO
                .materials()
                .stream()
                .map(materialName -> materialRepository.findMaterialByName(materialName.toUpperCase()).orElseThrow())
                .map(material -> {
                    RecyclingCenterMaterial recyclingCenterMaterial = new RecyclingCenterMaterial();
                    recyclingCenterMaterial.setRecyclingCenter(recyclingCenter);
                    recyclingCenterMaterial.setMaterial(material);
                    return recyclingCenterMaterial;
                })
                .collect(Collectors.toList())
        );
        recyclingCenter.setStartingTime(recyclingCenterDTO.startingTime());
        recyclingCenter.setEndTime(recyclingCenterDTO.endTime());

        RecyclingCenterLocation location = getLocation(recyclingCenterDTO);
        recyclingCenter.setRecyclingCenterLocation(location);
        location.setRecyclingCenter(recyclingCenter);

        return recyclingCenter;
    }

    private static RecyclingCenterLocation getLocation(RecyclingCenterDTO recyclingCenterDTO) {
        RecyclingCenterLocation recyclingCenterLocation = new RecyclingCenterLocation();
        LocationDTO locationDTO = recyclingCenterDTO.location();
        recyclingCenterLocation.setCounty(locationDTO.county());
        recyclingCenterLocation.setCity(locationDTO.city());
        recyclingCenterLocation.setAddress(locationDTO.address());
        recyclingCenterLocation.setZipcode(locationDTO.zipcode());
        recyclingCenterLocation.setLongitude(locationDTO.longitude());
        recyclingCenterLocation.setLatitude(locationDTO.latitude());

        return recyclingCenterLocation;
    }

    private static LocationDTO getLocationDTO(RecyclingCenter recyclingCenter) {
        RecyclingCenterLocation recyclingCenterLocation = recyclingCenter.getRecyclingCenterLocation();
        return new LocationDTO(
                recyclingCenterLocation.getCounty(),
                recyclingCenterLocation.getCity(),
                recyclingCenterLocation.getAddress(),
                recyclingCenterLocation.getZipcode(),
                recyclingCenterLocation.getLongitude(),
                recyclingCenterLocation.getLatitude()
        );
    }
}
