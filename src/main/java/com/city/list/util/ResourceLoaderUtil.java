package com.city.list.util;

import com.city.list.config.Config;
import com.city.list.dto.CityDTO;
import com.city.list.entity.schema.City;
import com.city.list.repository.specification.ICityRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResourceLoaderUtil {

    private final ResourceLoader resourceLoader;

    private final ICityRepository cityRepository;

    final Logger logger = LoggerFactory.getLogger(ResourceLoaderUtil.class);

    @PostConstruct
    public void init() throws IOException, CsvException {

        try {

            logger.info("App is initializing data.");

            File file = ResourceUtils.getFile("classpath:cities.csv");
            file.createNewFile();
            CSVReader reader=
                    new CSVReaderBuilder(new FileReader(file)).
                            withSkipLines(1). // Skiping firstline as it is header
                            build();
            List<CityCSVMapper> csv_objectList=reader.readAll().stream().map(data-> {
                CityCSVMapper csvObject= new CityCSVMapper();
                csvObject.setId(Integer.parseInt(data[0]));
                csvObject.setName(data[1]);
                csvObject.setPhoto(data[2]);
                return csvObject;
            }).collect(Collectors.toList());

            logger.info("App found {} data records and starting populate data in the DB.", csv_objectList.size());
            List<City> cityList = csv_objectList.stream().map(csv -> {
                City city = new City();
                city.setCityName(csv.getName());
                city.setPhotoURL(csv.getPhoto());
                return city;
            }).collect(Collectors.toList());
            cityRepository.saveAll(cityList);

            logger.info("App has populated data successfully.");
        } catch (Exception e) {
            throw e;
        }

    }



}
