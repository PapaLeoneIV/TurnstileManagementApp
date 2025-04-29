package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.RoleDTO;
import it.tdgc.turnstile.repository.RoleRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import it.tdgc.turnstile.model.Role;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final MapperInterface mapperInterface;


    //TODO finish to implement controller and postman tests


    public RoleService(RoleRepository roleRepository, MapperInterface mapperInterface) {
        this.roleRepository = roleRepository;
        this.mapperInterface = mapperInterface;
    }


    @Transactional
    public ResponseEntity<ApiResponse<List<RoleDTO>>> getAllRoles(){
        List<Role> r = roleRepository.findAll();
        if(r.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.OK, "No role available", null);
        }
        List<RoleDTO> roleDTOS = r.stream().map(mapperInterface::toRoleDTO).toList();
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", roleDTOS);
    }

    @Transactional
    public ResponseEntity<ApiResponse<RoleDTO>> getRoleById(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "Role ID not found", null);
        }

        RoleDTO roleDTO = mapperInterface.toRoleDTO(role.get());
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", roleDTO);
    }

    @Transactional
    public ResponseEntity<ApiResponse<RoleDTO>> deleteRoleById(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "Role ID not found", null);
        }
        roleRepository.deleteById(id);
        RoleDTO roleDTO = mapperInterface.toRoleDTO(role.get());

        return responseBuilder.buildResponse(HttpStatus.OK, "Role successfully deleted", roleDTO);
    }

    @Transactional
    public ResponseEntity<ApiResponse<RoleDTO>> updateRole(Role role) {
        Optional<Role> existingRole = roleRepository.findById(role.getId());
        if(existingRole.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "Role ID not found", null);
        }
        existingRole.get().setLevel(role.getLevel());
        existingRole.get().setDescription(role.getDescription());

        Role updatedRole = roleRepository.save(existingRole.get());
        RoleDTO roleDTO = mapperInterface.toRoleDTO(updatedRole);

        return responseBuilder.buildResponse(HttpStatus.OK, "Role updated successfully", roleDTO);
    }

    @Transactional
    public ResponseEntity<ApiResponse<RoleDTO>> insertRole(RoleDTO role) {
        if(role.getDescription().isEmpty()){
            return  responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Description is empty", null);
        }
        if(roleRepository.findByLevel(role.getLevel()).isPresent()){
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, "Level already exists", null);
        }
        if(roleRepository.findByDescription(role.getDescription()).isPresent()){
            return  responseBuilder.buildResponse(HttpStatus.CONFLICT, "Description already exists", null);
        }

        Role newRole = new Role();

        newRole.setLevel(role.getLevel());
        newRole.setDescription(role.getDescription());
        Role savedRole = roleRepository.save(newRole);
        RoleDTO roleDTO = mapperInterface.toRoleDTO(savedRole);

        return responseBuilder.buildResponse(HttpStatus.OK, "Role created successfully", roleDTO);
    }
}
