package com.city.list.dto;

import com.city.list.entity.schema.City;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityDTO extends RepresentationModel implements ISuccessResponse{

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String photoURL;

    public CityDTO (City city) {
        this.setId(city.getId());
        this.setName(city.getCityName());
        this.setPhotoURL(city.getPhotoURL());
    }

}
