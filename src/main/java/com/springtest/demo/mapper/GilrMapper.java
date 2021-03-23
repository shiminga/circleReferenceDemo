package com.springtest.demo.mapper;

import com.springtest.demo.entities.Girl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
public interface GilrMapper {

    @Select("select * from girl where id=#{id}")
    Girl getGirlById(Long id);
}
