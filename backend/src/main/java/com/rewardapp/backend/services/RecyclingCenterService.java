package com.rewardapp.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewardapp.backend.dao.RecyclingCenterDAO;
import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.models.LocationModel;
import com.rewardapp.backend.models.RecyclingCenterModel;
import com.rewardapp.backend.models.assemblers.RecyclingCenterModelAssembler;
import com.rewardapp.backend.models.processors.RecyclingCenterModelProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecyclingCenterService {
    private final RecyclingCenterDAO recyclingCenterDAO;
    private final RecyclingCenterModelAssembler recyclingCenterModelAssembler;
    private final RecyclingCenterModelProcessor recyclingCenterModelProcessor;

    private static Double[] getCoordinates(LocationModel locationModel) {
        String address = locationModel.getAddress() + ", " + locationModel.getCity() + ", " + locationModel.getCounty() + ", " + locationModel.getZipcode();
        address = address.replace(" ", "+");
        address = address.replace(",", "%2C");
        String GOOGLE_API_KEY = "AIzaSyDmV5M3u4CcccC-VFtqDMLtzvpiOTw0lE0";
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", address, GOOGLE_API_KEY);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root;
        try {
            root = objectMapper.readTree(responseEntity.getBody());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JsonNode location = root.path("results").get(0).path("geometry").path("location");
        Double lng = location.path("lng").asDouble();
        Double lat = location.path("lat").asDouble();

        System.out.println(lat);

        return new Double[]{lat, lng};
    }

    public RecyclingCenterModel create(RecyclingCenterModel recyclingCenterModel) {
        LocationModel locationModel = recyclingCenterModel.getLocation();

        if (locationModel.getLatitude() == null || locationModel.getLongitude() == null) {
            Double[] coordinates = getCoordinates(locationModel);
            locationModel.setLongitude(coordinates[1]);
            locationModel.setLatitude(coordinates[0]);
        }

        RecyclingCenter recyclingCenter = recyclingCenterDAO.save(recyclingCenterModel);
        RecyclingCenterModel model = recyclingCenterModelAssembler.toModel(recyclingCenter);
        return recyclingCenterModelProcessor.process(model);
    }

    public RecyclingCenterModel findRecyclingCenterById(Long id) {
        RecyclingCenter recyclingCenter = recyclingCenterDAO.getRecyclingCenterById(id);

        RecyclingCenterModel recyclingCenterModel = recyclingCenterModelAssembler.toModel(recyclingCenter);
        return recyclingCenterModelProcessor.process(recyclingCenterModel);
    }

    public Boolean deleteById(Long id) {
        return recyclingCenterDAO.deleteById(id);
    }

    public RecyclingCenterModel update(Long id, RecyclingCenterModel recyclingCenterModel) {
        LocationModel locationModel = recyclingCenterModel.getLocation();

        if (locationModel.getLatitude() == null || locationModel.getLongitude() == null) {
            Double[] coordinates = getCoordinates(locationModel);
            locationModel.setLongitude(coordinates[1]);
            locationModel.setLatitude(coordinates[0]);
        }

        RecyclingCenter updatedRecyclingCenter = recyclingCenterDAO.update(id, recyclingCenterModel);
        RecyclingCenterModel model = recyclingCenterModelAssembler.toModel(updatedRecyclingCenter);
        return recyclingCenterModelProcessor.process(model);
    }

    public List<RecyclingCenterModel> getAll() {
        return recyclingCenterDAO.getAll()
                .stream()
                .map(recyclingCenterModelAssembler::toModel)
                .map(recyclingCenterModelProcessor::process)
                .toList();
    }
}
