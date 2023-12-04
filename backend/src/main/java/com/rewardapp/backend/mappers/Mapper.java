package com.rewardapp.backend.mappers;

import org.springframework.transaction.annotation.Transactional;

public interface Mapper<Entity, DTO> {
    @Transactional
    public DTO mapToDTO(Entity e);

    @Transactional
    public Entity mapToEntity(DTO d);
}
