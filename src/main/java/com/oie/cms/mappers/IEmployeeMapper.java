package com.oie.cms.mappers;

import com.oie.cms.dtos.employee.AddEmployeeReqDto;
import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.employee.ReadEmployeeResDto;
import com.oie.cms.entities.employee.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper {
    @Mapping(target = "departmentName", source = "department.name")
    ReadEmployeeResDto mapToDto(Employee emp);

    List<ReadEmployeeResDto> mapToDto(List<Employee> emps);

    default PaginationResDto<ReadEmployeeResDto> mapToDto(Page<Employee> empsPage) {
        var paginationRes = new PaginationResDto<ReadEmployeeResDto>();
        paginationRes.setItemsTotalCount(empsPage.getTotalElements());
        paginationRes.setItems(empsPage.map(this::mapToDto).getContent());
        return paginationRes;
    }

    NormalEmployee mapToNormalEmployee(AddEmployeeReqDto dto);

    Admin mapToAdmin(AddEmployeeReqDto dto);

    Manager mapToManger(AddEmployeeReqDto dto);

    TeamLead mapToTeamLead(AddEmployeeReqDto dto);

    Hr mapToHr(AddEmployeeReqDto dto);

    ProductOwner mapToProductOwner(AddEmployeeReqDto dto);

    default Employee mapToEntity(AddEmployeeReqDto dto) {
        return switch (dto.getRole()) {
            case NORMAL -> mapToNormalEmployee(dto);
            case ADMIN -> mapToAdmin(dto);
            case HR -> mapToHr(dto);
            case MANAGER -> mapToManger(dto);
            case TEAM_LEAD -> mapToTeamLead(dto);
            case PO -> mapToProductOwner(dto);
        };
    }
}
