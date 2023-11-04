package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RoleMapper {

    Role getRoleInfo(@Param("role") String role);

}