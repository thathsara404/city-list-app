package com.city.list.entity.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="cities")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class City {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "cityName", nullable = false)
    private String cityName;

    @Column(name = "photoURL", nullable = false, length = 1000)
    private String photoURL;

}
