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
public class CityDTOPatch extends RepresentationModel implements ISuccessResponse{

    private String name;

    private String photoURL;


}
