package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.model.entity.Event;
import com.example.server.model.vo.IncidentDetailVO;
import com.example.server.model.vo.IncidentRecordVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EventMapper extends BaseMapper<Event> {
    List<Event> selectByTime(LocalDateTime startTime, LocalDateTime endTime);

    List<String> incidentStates();

    List<Event> selectByTimeAndState(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotBlank String incidentsState);

    List<IncidentRecordVO> getIncidentRecordList(LocalDateTime startTime, LocalDateTime endTime, String incidentState, String incidentType);

    IncidentDetailVO getIncidentDetail(Integer id);

    List<IncidentRecordVO> getDepartmentIncidentRecordList(LocalDateTime startTime, LocalDateTime endTime, String incidentState, String incidentType, Integer userId);
}
