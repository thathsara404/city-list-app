package com.city.list.service;

import com.city.list.consts.ErrorConst;
import com.city.list.dto.CityDTO;
import com.city.list.dto.CityDTOPatch;
import com.city.list.entity.schema.City;
import com.city.list.exception.BadRequestException;
import com.city.list.repository.specification.ICityRepository;
import com.city.list.exception.UnExpectedErrorOccurredException;
import com.city.list.service.specification.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
     * Get city
     * @param cityId Long
     * @return CityDTO
     * */
    @Override
    public CityDTO getCity(Long cityId) {
        String sortField = "cityName";
        CityDTO cityDTO = null;
        try {
            City city = repository.getById(cityId).get();
            cityDTO = new CityDTO(city);
            return cityDTO;
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
     * Patch city by id
     * @param patchData CityDTOPatch
     * @param id Long
     * */
    @Override
    public void patchCityById(CityDTOPatch patchData, Long id) {
        try {
            String name = patchData.getName();
            String url = patchData.getPhotoURL();
            System.out.println("----------->>>>"+ url + " " + name);
            City city = repository.getById(id).get();
            System.out.println("-->>>>" + city);
            if (patchData != null && name != null) {
                city.setCityName(name);
                repository.save(city);
            } else if (patchData != null && url != null) {
                city.setPhotoURL(url);
                repository.save(city);
            } else if ((patchData != null && name != null) && (patchData != null && url != null)) {
                city.setCityName(name);
                city.setPhotoURL(url);
                repository.save(city);
            } else {
                throw new BadRequestException(ErrorConst.BAD_REQUEST_ERROR_MESSAGE, null);
            }
        } catch (DataIntegrityViolationException exception) {
            throw exception;
        } catch(BadRequestException exception) {
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
