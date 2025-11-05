package com.example.server.mapper;

import com.example.server.model.entity.XunJianPoint;
import com.example.server.model.vo.IspPosVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface XunJianPointMapper {
    List<IspPosVO> getNewestIspPosList();

    List<XunJianPoint> getMobilePhoneUserIspPosReplaying(long startTimeStamp, long endTimeStamp, String IMEI);

}
