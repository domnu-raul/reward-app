package com.rewardapp.backend.services;

import com.rewardapp.backend.dto.RecyclingCenterDTO;
import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.mappers.Mapper;
import com.rewardapp.backend.mappers.RecyclingCenterMapper;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecyclingCenterService {
    private final RecyclingCenterRepository recyclingCenterRepository;
    private final Mapper<RecyclingCenter, RecyclingCenterDTO> recyclingCenterMapper;
    public RecyclingCenterService(RecyclingCenterRepository recyclingCenterRepository, RecyclingCenterMapper recyclingCenterMapper) {
        this.recyclingCenterRepository = recyclingCenterRepository;
        this.recyclingCenterMapper = recyclingCenterMapper;
    }

    @Transactional
    public RecyclingCenterDTO create(RecyclingCenterDTO recyclingCenterDTO) {
        RecyclingCenter recyclingCenter = recyclingCenterMapper.mapToEntity(recyclingCenterDTO);
        return recyclingCenterMapper.mapToDTO(recyclingCenterRepository.save(recyclingCenter));
    }

    public RecyclingCenterDTO getRecyclingCenterById(Long id) {
        RecyclingCenter recyclingCenter = recyclingCenterRepository.getRecyclingCenterById(id);
        return recyclingCenterMapper.mapToDTO(recyclingCenter);
    }

    public List<RecyclingCenter> getAll() {
        return recyclingCenterRepository.getAll();
    }
}
