package com.city.list.service;

import com.city.list.consts.ErrorConst;
import com.city.list.dto.CityDTO;
import com.city.list.entity.schema.City;
import com.city.list.repository.specification.ICityRepository;
import com.city.list.exception.UnExpectedErrorOccurredException;
import com.city.list.service.specification.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of ICityService
 * */
@Service
@AllArgsConstructor
public class CityServiceImpl implements ICityService {

    private ICityRepository repository;

    /**
     * Save new city and return safely copied CityDTO
     * @param city CityDTO
     * @return CityDTO
     * */
    @Override
    public CityDTO addNewCity(final CityDTO city) {
        CityDTO citySavedDTO = null;
        try {
            City cityInsert = new City();
            cityInsert.setCityName(city.getName());
            cityInsert.setPhotoURL(city.getPhotoURL());
            City ciySaved = repository.save(cityInsert);

            citySavedDTO = new CityDTO(ciySaved);
            return citySavedDTO;
        } catch (DataIntegrityViolationException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new UnExpectedErrorOccurredException(ErrorConst.UNEXPECTED_ERROR_OCCURRED_MESSAGE,
                    exception.getCause());
        }
    }

    /**
     * Get all cities with pagination & sort
     * @param pageNumber Integer
     * @param numItems Integer
     * @param cityName String
     * @return List<CityDTO>
     * */
    @Override
    public List<CityDTO> findAllCitiesWithSorting(Integer pageNumber, Integer numItems, String cityName) {
        String sortField = "cityName";
        List<CityDTO> cities = null;
        try {
            Pageable sortedByPriceDesc =
                    PageRequest.of(pageNumber, numItems, Sort.by(sortField).descending());
            List<City> searchResult = repository.findAllByCityName(cityName, sortedByPriceDesc);
            cities = searchResult.stream().map(entity -> new CityDTO(entity)).collect(Collectors.toList());
            return cities;
        } catch (DataIntegrityViolationException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new UnExpectedErrorOccurredException(ErrorConst.UNEXPECTED_ERROR_OCCURRED_MESSAGE,
                exception.getCause());
        }
    }

    /**
     * Update city name by id
     * @param name String
     * @param id Long
     * @return Integer
     * */
    @Override
    public Integer updateCityNameById(String name, Long id) {
        try {
            Integer result = repository.updateCityNameById(name, id);
            return result;
        } catch (DataIntegrityViolationException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new UnExpectedErrorOccurredException(ErrorConst.UNEXPECTED_ERROR_OCCURRED_MESSAGE,
                exception.getCause());
        }
    }

    /**
     * Update city photo url by id
     * @param url String
     * @param id Long
     * @return Integer
     * */
    @Override
    public Integer updateCityPhotoUrlById(String url, Long id) {
        try {
            Integer result = repository.updateCityImageById(url, id);
            return result;
        } catch (DataIntegrityViolationException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new UnExpectedErrorOccurredException(ErrorConst.UNEXPECTED_ERROR_OCCURRED_MESSAGE,
                    exception.getCause());
        }
    }

    /**
     * Update city name and photo url by id
     * @param name String
     * @param url String
     * @param id Long
     * @return CityDTO
     * */
    @Override
    public CityDTO updateCityById(String name, String url, Long id) {
        try {
            Optional<City> city = repository.findById(id);
            city.ifPresent(c -> {
                c.setCityName(name);
                c.setPhotoURL(url);
            });
            City updatedCity= repository.save(city.get());
            CityDTO dto = new CityDTO(updatedCity);
            return dto;
        } catch (DataIntegrityViolationException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new UnExpectedErrorOccurredException(ErrorConst.UNEXPECTED_ERROR_OCCURRED_MESSAGE,
                    exception.getCause());
        }
    }

}
