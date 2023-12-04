package com.rewardapp.backend.services;

import com.rewardapp.backend.dto.RecyclingCenterDTO;
import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.mappers.Mapper;
import com.rewardapp.backend.mappers.RecyclingCenterMapper;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecyclingCenterService {
    private final RecyclingCenterRepository recyclingCenterRepository;
    private final Mapper<RecyclingCenter, RecyclingCenterDTO> recyclingCenterMapper;

    public RecyclingCenterService(RecyclingCenterRepository recyclingCenterRepository, RecyclingCenterMapper recyclingCenterMapper) {
        this.recyclingCenterRepository = recyclingCenterRepository;
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
        RecyclingCenterDTO oldRecyclingCenterDTO = recyclingCenterMapper.mapToDTO(recyclingCenter);
        recyclingCenterRepository.delete(recyclingCenter);

        RecyclingCenterDTO newRecyclingCenterDTO = new RecyclingCenterDTO(
                id,
                recyclingCenterDTO.name() != null ? recyclingCenterDTO.name() : oldRecyclingCenterDTO.name(),
                recyclingCenterDTO.materials() != null ? recyclingCenterDTO.materials() : oldRecyclingCenterDTO.materials(),
                recyclingCenterDTO.location() != null ? recyclingCenterDTO.location() : oldRecyclingCenterDTO.location(),
                recyclingCenterDTO.startingTime() != null ? recyclingCenterDTO.startingTime() : oldRecyclingCenterDTO.startingTime(),
                recyclingCenterDTO.endTime() != null ? recyclingCenterDTO.endTime() : oldRecyclingCenterDTO.endTime()
        );
        recyclingCenter = recyclingCenterMapper.mapToEntity(newRecyclingCenterDTO);
        recyclingCenter.setId(id);

        recyclingCenterRepository.save(recyclingCenter);
        return newRecyclingCenterDTO;
    }

    public List<RecyclingCenterDTO> getAll() {
        return recyclingCenterRepository
                .getAll()
                .stream()
                .map(x -> recyclingCenterMapper.mapToDTO(x))
                .collect(Collectors.toList());
    }
}
