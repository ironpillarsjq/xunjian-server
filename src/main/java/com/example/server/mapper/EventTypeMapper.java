package com.example.server.mapper;
import com.example.server.model.vo.IncidentRecordVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.model.entity.EventType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventTypeMapper extends BaseMapper<EventType> {
    List<String> selectEventType();

    List<Integer> selectIdByEventType(@Param("eventType") String eventType);

}
