package com.oie.cms.services;

import com.oie.cms.dtos.team.AddTeamMemberReqDto;
import com.oie.cms.dtos.team.AddTeamReqDto;
import com.oie.cms.dtos.team.AddTeamResDto;
import com.oie.cms.dtos.team.ReadTeamResDto;
import com.oie.cms.entities.employee.TeamLead;
import com.oie.cms.entities.team.TeamMembership;
import com.oie.cms.enums.EmployeeRole;
import com.oie.cms.exceptions.ConflictBusinessException;
import com.oie.cms.exceptions.NotFoundBusinessException;
import com.oie.cms.mappers.TeamMapper;
import com.oie.cms.repositories.department.TeamBasedDepartmentRepository;
import com.oie.cms.repositories.employee.EmployeeRepository;
import com.oie.cms.repositories.employee.TeamLeadRepository;
import com.oie.cms.repositories.team.TeamMembershipRepository;
import com.oie.cms.repositories.team.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class TeamService {
    private final TeamMapper teamMapper;
    private final TeamRepository teamRepository;
    private final TeamBasedDepartmentRepository teamBasedDepartmentRepository;
    private final TeamLeadRepository teamLeadRepository;
    private final TeamMembershipRepository teamMembershipRepository;
    private final EmployeeRepository employeeRepository;

    public ReadTeamResDto getTeamById(Long id) {
        return teamRepository.findById(id)
                .map(teamMapper::mapToDto)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no team with id %d", id)));
    }

    public List<ReadTeamResDto> getTeamsByDeptId(Long deptId) {
        var dept = teamBasedDepartmentRepository.findById(deptId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no team based department with id %s", deptId)));
        return teamMapper.mapToDto(dept.getTeams());
    }

    public AddTeamResDto addTeamToDepartment(Long deptId, AddTeamReqDto addTeamDto) {
        var dept = teamBasedDepartmentRepository.findById(deptId)
                .orElseThrow(() ->
                        new NotFoundBusinessException(
                                format("There is team based department with id %d", deptId))
                );

        var team = teamMapper.mapToEntity(addTeamDto);
        team.setDepartment(dept);
        teamRepository.save(team);

        var leadId = addTeamDto.getLeadId();
        if (leadId != null) {
            var lead = teamLeadRepository.findById(leadId)
                    .orElseThrow(() -> new NotFoundBusinessException(
                            format("There is no lead with id %d", leadId)));

            if (lead.getDepartment() != null && !lead.getDepartment().equals(dept)) {
                throw new ConflictBusinessException(
                        format("Team lead %s (%d) is assigned to another department",
                                lead.getName(),
                                lead.getId()));
            }

            lead.setDepartment(dept);

            if (!lead.getTeams().isEmpty()) {
                throw new ConflictBusinessException(
                        format("Team lead %s (%d) already exist in another team",
                                lead.getName(),
                                lead.getId()));
            }

            var tma = TeamMembership.builder()
                    .team(team)
                    .member(lead)
                    .build();
            teamMembershipRepository.save(tma);

            team.setLead(lead);
        }

        return AddTeamResDto.builder().id(team.getId()).build();
    }

    public void addTeamMember(Long teamId, AddTeamMemberReqDto addTeamMemberDto) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no team with id %d", teamId)));

        var empId = addTeamMemberDto.getMemberId();
        var emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no employee with id %s", empId)));

        var notAllowedRoles = Stream.of(EmployeeRole.MANAGER);
        if (notAllowedRoles.anyMatch(role -> role.equals(emp.getRole()))) {
            throw new ConflictBusinessException(
                    format("Employee with role %s can not be a team member", emp.getRole()));
        }

        if (team.getMembers().stream().anyMatch(m -> m.getId().equals(empId))) {
            throw new ConflictBusinessException(
                    format("%s already exists in %s team", emp.getName(), team.getName()));
        }

        if (emp.getDepartment() != null
                && !emp.getDepartment().getId().equals(team.getDepartment().getId())) {
            throw new ConflictBusinessException(
                    "Employee is not belong to the same team department");
        }

        if (emp instanceof TeamLead lead) {
            if (team.getMembers().stream().anyMatch(m -> m instanceof TeamLead)) {
                throw new ConflictBusinessException(
                        format("There is already a team lead for %s team", team.getName()));
            }

            if (lead.getTeams() != null && !lead.getTeams().isEmpty()) {
                throw new ConflictBusinessException(
                        format("%s already leads another team", lead.getName()));
            }

            team.setLead((TeamLead) emp);
        }

        emp.setDepartment(team.getDepartment());

        var tma = TeamMembership.builder()
                .team(team)
                .member(emp)
                .build();
        teamMembershipRepository.save(tma);
    }

    public void deleteTeamById(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new NotFoundBusinessException(format("There is no team with id %d", teamId));
        }
        teamRepository.deleteById(teamId);
    }
}
