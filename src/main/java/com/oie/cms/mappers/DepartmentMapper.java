package com.oie.cms.mappers;

import com.oie.cms.dtos.department.AddDepartmentReqDto;
import com.oie.cms.dtos.department.ReadDepartmentResDto;
import com.oie.cms.entities.department.Department;
import com.oie.cms.entities.department.TeamBasedDepartment;
import com.oie.cms.entities.department.FlatDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(target = "managerName", source = "manager.name")
    ReadDepartmentResDto mapToDto(Department dept);

    List<ReadDepartmentResDto> mapToDto(List<Department> depts);

    FlatDepartment mapToFlatDepartment(AddDepartmentReqDto dto);

    TeamBasedDepartment mapToTeamBasedDepartment(AddDepartmentReqDto dto);

    default Department mapToEntity(AddDepartmentReqDto dto) {
        return switch (dto.getType()) {
            case FLAT -> mapToFlatDepartment(dto);
            case TEAM_BASED -> mapToTeamBasedDepartment(dto);
        };
    }
}
