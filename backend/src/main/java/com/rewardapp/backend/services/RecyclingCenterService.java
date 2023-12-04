package com.rewardapp.backend.services;

import com.rewardapp.backend.dto.RecyclingCenterDTO;
import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.mappers.Mapper;
import com.rewardapp.backend.mappers.RecyclingCenterMapper;
import com.rewardapp.backend.repositories.RecyclingCenterLocationRepository;
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
    private final RecyclingCenterLocationRepository recyclingCenterLocationRepository;
    private final RecyclingCenterMaterialRepository recyclingCenterMaterialRepository;
    private final Mapper<RecyclingCenter, RecyclingCenterDTO> recyclingCenterMapper;

    public RecyclingCenterService(RecyclingCenterRepository recyclingCenterRepository, RecyclingCenterLocationRepository recyclingCenterLocationRepository, RecyclingCenterMaterialRepository recyclingCenterMaterialRepository, RecyclingCenterMapper recyclingCenterMapper) {
        this.recyclingCenterRepository = recyclingCenterRepository;
        this.recyclingCenterLocationRepository = recyclingCenterLocationRepository;
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
        RecyclingCenter updatedRecyclingCenter = recyclingCenterMapper.mapToEntity(recyclingCenterDTO);

        if (updatedRecyclingCenter.getName() != null)
            recyclingCenter.setName(updatedRecyclingCenter.getName());
        if (updatedRecyclingCenter.getStartingTime() != null)
            recyclingCenter.setStartingTime(updatedRecyclingCenter.getStartingTime());
        if (updatedRecyclingCenter.getEndTime() != null)
            recyclingCenter.setEndTime(updatedRecyclingCenter.getEndTime());
        if (updatedRecyclingCenter.getRecyclingCenterMaterials() != null) {
            recyclingCenter.getRecyclingCenterMaterials()
                    .stream()
                    .forEach(x -> recyclingCenterMaterialRepository.delete(x));

            recyclingCenter.setRecyclingCenterMaterials(
                    updatedRecyclingCenter.getRecyclingCenterMaterials()
            );
        }
        if (updatedRecyclingCenter.getRecyclingCenterLocation() != null) {
            recyclingCenterLocationRepository.delete(
                    recyclingCenter.getRecyclingCenterLocation()
            );

            recyclingCenter.setRecyclingCenterLocation(
                    updatedRecyclingCenter.getRecyclingCenterLocation()
            );
        }

        recyclingCenterRepository.save(recyclingCenter);
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
