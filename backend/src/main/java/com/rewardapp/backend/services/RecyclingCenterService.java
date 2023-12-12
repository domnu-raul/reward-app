package com.rewardapp.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewardapp.backend.dao.RecyclingCenterDAO;
import com.rewardapp.backend.models.Location;
import com.rewardapp.backend.models.RecyclingCenter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecyclingCenterService {
    private static final String GOOGLE_API_KEY;

    static {
        Map<String, Object> map = null;
        try {
            JSONParser parser = new JSONParser(new FileReader("src/main/resources/data.json"));
            map = parser.parseObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GOOGLE_API_KEY = (String) map.get("apiKey");
    }

    private final RecyclingCenterDAO recyclingCenterDAO;

    private static Double[] getCoordinates(Location locationModel) {
        String address = locationModel.getAddress() + ", " + locationModel.getCity() + ", " + locationModel.getCounty() + ", " + locationModel.getZipcode();
        address = address.replace(" ", "+");
        address = address.replace(",", "%2C");
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

    public RecyclingCenter save(RecyclingCenter recyclingCenter) {
        Location location = recyclingCenter.getLocation();

        if (location.getLatitude() == null || location.getLongitude() == null) {
            Double[] coordinates = getCoordinates(location);
            location.setLongitude(coordinates[1]);
            location.setLatitude(coordinates[0]);
        }

        return recyclingCenterDAO.save(recyclingCenter);
    }

    public RecyclingCenter findRecyclingCenterById(Long id) {
        return recyclingCenterDAO.getRecyclingCenterById(id);
    }

    public Boolean deleteById(Long id) {
        return recyclingCenterDAO.deleteById(id);
    }

    public RecyclingCenter update(Long id, RecyclingCenter recyclingCenter) {
        Location location = recyclingCenter.getLocation();

        if (location.getLatitude() == null || location.getLongitude() == null) {
            Double[] coordinates = getCoordinates(location);
            location.setLongitude(coordinates[1]);
            location.setLatitude(coordinates[0]);
        }

        return recyclingCenterDAO.update(id, recyclingCenter);
    }

    public List<RecyclingCenter> getAll(List<String> materials, String search, String order, Boolean reverse, Boolean open, Integer page) {
        return recyclingCenterDAO.getAll(materials, search, order, reverse, open, page);
    }

    public List<RecyclingCenter> getClosest(Double lat, Double lng, Integer page) {
        return recyclingCenterDAO.getClosest(lat, lng, page);
    }
}
