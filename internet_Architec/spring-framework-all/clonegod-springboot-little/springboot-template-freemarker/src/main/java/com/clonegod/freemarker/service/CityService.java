package com.clonegod.freemarker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.clonegod.freemarker.bean.City;

@Service
public class CityService implements ICityService {

    @Autowired
    private JdbcTemplate jtm;

    @Override
    public List<City> findAll() {

        String sql = "SELECT * FROM CITIES";

        List<City> cities = jtm.query(sql, new BeanPropertyRowMapper<City>(City.class));

        return cities;
    }
}