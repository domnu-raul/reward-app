package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.entities.RecyclingCenterLocation;
import com.rewardapp.backend.entities.RecyclingCenterMaterial;
import com.rewardapp.backend.entities.dto.LocationDTO;
import com.rewardapp.backend.entities.dto.RecyclingCenterDTO;
import com.rewardapp.backend.mappers.RecyclingCenterMapper;
import com.rewardapp.backend.repositories.MaterialRepository;
import com.rewardapp.backend.repositories.RecyclingCenterMaterialRepository;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecyclingCenterService {
    private final RecyclingCenterRepository recyclingCenterRepository;
    private final MaterialRepository materialRepository;
    private final RecyclingCenterMaterialRepository recyclingCenterMaterialRepository;
    private final RecyclingCenterMapper recyclingCenterMapper;

    public RecyclingCenterService(RecyclingCenterRepository recyclingCenterRepository, MaterialRepository materialRepository, RecyclingCenterMaterialRepository recyclingCenterMaterialRepository, RecyclingCenterMapper recyclingCenterMapper) {
        this.recyclingCenterRepository = recyclingCenterRepository;
        this.materialRepository = materialRepository;
        this.recyclingCenterMaterialRepository = recyclingCenterMaterialRepository;
        this.recyclingCenterMapper = recyclingCenterMapper;
    }

    public RecyclingCenterDTO create(RecyclingCenterDTO recyclingCenterDTO) {
        RecyclingCenter recyclingCenter = recyclingCenterMapper.mapToEntity(recyclingCenterDTO);
        return recyclingCenterMapper.mapToDTO(recyclingCenterRepository.save(recyclingCenter));
    }

    public RecyclingCenterDTO getRecyclingCenterDTOById(Long id) {
        RecyclingCenter recyclingCenter = getRecyclingCenterById(id);
        return recyclingCenterMapper.mapToDTO(recyclingCenter);
    }

    public RecyclingCenter getRecyclingCenterById(Long id) {
        return recyclingCenterRepository.getRecyclingCenterById(id);
    }

    public void delete(RecyclingCenter recyclingCenter) {
        recyclingCenterRepository.delete(recyclingCenter);
    }

    public RecyclingCenterDTO update(Long id, RecyclingCenterDTO recyclingCenterDTO) {
        RecyclingCenter recyclingCenter = recyclingCenterRepository.getRecyclingCenterById(id);

        if (recyclingCenterDTO.name() != null)
            recyclingCenter.setName(recyclingCenterDTO.name());
        if (recyclingCenterDTO.startingTime() != null)
            recyclingCenter.setStartingTime(recyclingCenterDTO.startingTime());
        if (recyclingCenterDTO.endTime() != null)
            recyclingCenter.setEndTime(recyclingCenterDTO.endTime());

        recyclingCenterRepository.save(recyclingCenter);

        if (recyclingCenterDTO.materials() != null) {
            List<String> materialNames = recyclingCenterDTO.materials();

            List<RecyclingCenterMaterial> currentMaterials = recyclingCenter.getRecyclingCenterMaterials();
            List<RecyclingCenterMaterial> removed = currentMaterials
                    .stream()
                    .filter(recyclingCenterMaterial -> (!materialNames.contains(recyclingCenterMaterial.getMaterial().getName())))
                    .peek(x -> System.out.println(x.getId() + " " + x.getMaterial().getName()))
                    .toList();

            List<RecyclingCenterMaterial> additions = materialNames
                    .stream()
                    .filter(name -> (!currentMaterials.stream().map(x -> x.getMaterial().getName()).toList().contains(name)))
                    .map(name -> (materialRepository.findMaterialByName(name)
                            .orElseThrow(() -> new RuntimeException("Material not found"))))
                    .map(material -> {
                        RecyclingCenterMaterial recyclingCenterMaterial = new RecyclingCenterMaterial();
                        recyclingCenterMaterial.setRecyclingCenter(recyclingCenter);
                        recyclingCenterMaterial.setMaterial(material);
                        return recyclingCenterMaterial;
                    })
                    .toList();

            currentMaterials.removeAll(removed);
            currentMaterials.addAll(additions);

            recyclingCenterMaterialRepository.deleteAll(removed);
            recyclingCenterMaterialRepository.saveAll(additions);
        }

        if (recyclingCenterDTO.location() != null) {
            RecyclingCenterLocation location = recyclingCenter.getRecyclingCenterLocation();
            LocationDTO newLocationDTO = recyclingCenterDTO.location();

            if (newLocationDTO.address() != null)
                location.setAddress(newLocationDTO.address());
            if (newLocationDTO.city() != null)
                location.setCity(newLocationDTO.city());
            if (newLocationDTO.county() != null)
                location.setCounty(newLocationDTO.county());
            if (newLocationDTO.zipcode() != null)
                location.setZipcode(newLocationDTO.zipcode());
            if (newLocationDTO.latitude() != null)
                location.setLatitude(newLocationDTO.latitude());
            if (newLocationDTO.longitude() != null)
                location.setLongitude(newLocationDTO.longitude());

            recyclingCenterRepository.updateLocation(recyclingCenter);
        }

        return recyclingCenterMapper.mapToDTO(recyclingCenter);
    }

    public List<RecyclingCenterDTO> getAll() {
        return recyclingCenterRepository
                .getAll()
                .stream()
                .map(x -> recyclingCenterMapper.mapToDTO(x))
                .collect(Collectors.toList());
    }
}
