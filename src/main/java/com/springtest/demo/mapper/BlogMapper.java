package com.springtest.demo.mapper;

import com.springtest.demo.entities.Blog;
import org.apache.ibatis.annotations.Select;

public interface BlogMapper {

//    @Select("select * from blog where id = #{id}")
    Blog selectBlog(int id);
}
