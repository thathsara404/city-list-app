package com.city.list.controller;

import com.city.list.dto.CityDTO;
import com.city.list.dto.CityDTOPatch;
import com.city.list.service.specification.ICityService;
import com.sun.istack.NotNull;
import com.city.list.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * City specific Endpoints
 * */
@RestController
@RequestMapping("/cities")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:80")
public class CityController {

    private ICityService cityService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<CityDTO>> addCity (@RequestBody @NotNull CityDTO city) {

        CityDTO savedCityDTO = cityService.addNewCity(city);
        savedCityDTO.add(linkTo(methodOn(CityController.class).addCity(city)).withSelfRel());
        ResponseDTO responseDTO = new ResponseDTO(Arrays.asList(savedCityDTO), null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<CityDTO>> getAllCitiesWithPagination (@RequestParam @NotNull Integer pageNumber,
                                                                   @RequestParam @NotNull Integer pageSize,
                                                                   @RequestParam @NotNull String cityName) {

        List<CityDTO> cityList = cityService.findAllCitiesWithSorting(pageNumber, pageSize, cityName);

        for (CityDTO city : cityList) {
            city.add(linkTo(methodOn(CityController.class).getAllCitiesWithPagination(pageNumber, pageSize, cityName)).withSelfRel());
        }

        ResponseDTO responseDTO = new ResponseDTO(cityList, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @PutMapping(path = "{cityId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCity (@RequestBody @NotNull CityDTO cityDTO,
                                      @PathVariable @NotNull Long cityId) {
        cityService.updateCityById(cityDTO.getName(), cityDTO.getPhotoURL(), cityId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "{cityId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patchCity (@RequestBody @NotNull CityDTOPatch cityDTO,
                                      @PathVariable @NotNull Long cityId) {
        cityService.patchCityById(cityDTO, cityId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<CityDTO>> getCityById (@PathVariable @NotNull Long cityId) {
        CityDTO cityDTO = cityService.getCity(cityId);
        cityDTO.add(linkTo(methodOn(CityController.class).getCityById(cityId)).withSelfRel());
        ResponseDTO responseDTO = new ResponseDTO(Arrays.asList(cityDTO), null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
