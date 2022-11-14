package com.city.list.repository.specification;

import com.city.list.entity.schema.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Represent the specification which includes city related operations.
 * */
@Repository
public interface ICityRepository extends PagingAndSortingRepository<City, Long> {

    // Find cities by city name
    public abstract List<City> findAllByCityName(String cityName, Pageable pageable);

    // Update city name by id
    @Query("UPDATE cities c SET c.city_name = ?1 WHERE (c.id=?2)")
    public abstract int updateCityNameById(String name, Long cityId);


    // Update photo url by id
    @Query("UPDATE cities c SET c.photourl = ?1 WHERE (c.id=?2)")
    public abstract int updateCityImageById(String name, Long cityId);

    // Get city by id
    public abstract Optional<City> findById(Long id);

}
