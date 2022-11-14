package com.city.list.service.specification;

import com.city.list.dto.CityDTO;
import com.city.list.enums.CityFilter;
import org.springframework.data.domain.Pageable;


import java.util.List;

/**
 * Represent the specification which includes city related business logic operations.
 * */
public interface ICityService {

    /**
     * Save new city
     * @param city City
     * @return CityDTO
     * */
    public abstract CityDTO addNewCity(CityDTO city);

    /**
     * Get all cities with pagination
     * @param pageNumber Integer
     * @param numItems Integer
     * @param cityName String
     * @return List<CityDTO>
     * */
    public abstract List<CityDTO> findAllCitiesWithSorting(Integer pageNumber, Integer numItems, String cityName);

    /**
     * Update city name by id
     * @param name String
     * @param id Long
     * @return Integer
     * */
    public abstract Integer updateCityNameById(String name, Long id);

    /**
     * Update city photo url by id
     * @param url String
     * @param id Long
     * @return Integer
     * */
    public abstract Integer updateCityPhotoUrlById(String url, Long id);

    /**
     * Update city name and photo url by id
     * @param name String
     * @param url String
     * @param id Long
     * @return CityDTO
     * */
    public abstract CityDTO updateCityById(String name, String url, Long id);

}
