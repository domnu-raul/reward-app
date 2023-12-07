package com.rewardapp.backend.models.assemblers;

import com.rewardapp.backend.entities.Material;
import com.rewardapp.backend.entities.RcLocation;
import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.models.LocationModel;
import com.rewardapp.backend.models.RecyclingCenterModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RecyclingCenterModelAssembler implements RepresentationModelAssembler<RecyclingCenter, RecyclingCenterModel> {
    private static LocationModel getLocation(RecyclingCenter recyclingCenter) {
        RcLocation rcLocation = recyclingCenter.getLocation();

        return new LocationModel(
                rcLocation.getCounty(),
                rcLocation.getCity(),
                rcLocation.getAddress(),
                rcLocation.getZipcode(),
                rcLocation.getLongitude(),
                rcLocation.getLatitude()
        );
    }

    @Override
    public RecyclingCenterModel toModel(RecyclingCenter recyclingCenter) {
        return new RecyclingCenterModel(
                recyclingCenter.getId(),
                recyclingCenter.getName(),
                recyclingCenter.getMaterials().stream().map(Material::getName).toList(),
                getLocation(recyclingCenter),
                recyclingCenter.getStartingTime(),
                recyclingCenter.getEndTime()
        );
    }
}
