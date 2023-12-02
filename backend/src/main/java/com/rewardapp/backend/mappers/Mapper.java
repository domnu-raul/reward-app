package com.rewardapp.backend.mappers;

public interface Mapper<Entity, DTO> {
    public DTO mapToDTO(Entity e);

    public Entity mapToEntity(DTO d);
}
