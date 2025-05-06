package it.tdgc.turnstile.controller;


import it.tdgc.turnstile.dto.RoleDTO;
import it.tdgc.turnstile.model.Role;
import it.tdgc.turnstile.service.RoleService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping(path = "/role")
public class RoleController {

    final private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RoleDTO>>> getAllRoles() {
        return roleService.getAllRoles();

    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ApiResponse<RoleDTO>> getRoleById(@PathVariable("id") Integer id) {
        return roleService.getRoleById(id);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse<RoleDTO>> deleteRoleById(@PathVariable("id")  Integer id) {
        return roleService.deleteRoleById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<RoleDTO>> updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<RoleDTO>> insertRole(@RequestBody RoleDTO role) {
        return roleService.insertRole(role);
    }

}
