package com.example.server.mapper;
import java.util.Collection;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.model.entity.MobilePhone;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MobilePhoneMapper extends BaseMapper<MobilePhone> {
    MobilePhone getMobilePhoneUserByIMEI(String IMEI);

    List<MobilePhone> getMobilePhoneUserList();

    MobilePhone getMobilePhoneUserById(Integer id);

    int countByImei(@Param("imei") String imei);

    int countById(@Param("id") Integer id);

    @Operation(summary = "判断用户修改的IMEI是否重复")
    int countByImeiAndIdNotIn(@Param("imei") String imei, @Param("idList") Collection<Integer> idList);

    int deleteById(@Param("id") Integer id);
}
