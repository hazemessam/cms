package com.oie.cms.mappers;

import com.oie.cms.dtos.team.AddTeamReqDto;
import com.oie.cms.dtos.team.ReadTeamResDto;
import com.oie.cms.entities.team.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITeamMapper {
    @Mapping(target = "leadName", source = "lead.name")
    @Mapping(target = "departmentName", source = "department.name")
    ReadTeamResDto mapToDto(Team team);

    List<ReadTeamResDto> mapToDto(List<Team> teams);

    Team mapToEntity(AddTeamReqDto dto);
}
