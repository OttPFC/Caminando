package com.caminando.Caminando.presentationlayer.api.controller;

import com.caminando.Caminando.businesslayer.services.dto.user.LoginResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisterUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.UserService;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.exceptions.NotFoundException;
import com.caminando.Caminando.presentationlayer.api.models.user.LoginModel;
import com.caminando.Caminando.presentationlayer.api.models.user.RegisterUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(Pageable pageable) {
        Page<User> allUsers = userService.getAll(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Totale", String.valueOf(allUsers.getTotalElements()));
        return new ResponseEntity<>(allUsers, headers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RegisteredUserDTO> getUser(@PathVariable Long id) {
        try {
            RegisteredUserDTO userDTO = userService.getById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RegisteredUserDTO> register(@RequestBody @Validated RegisterUserModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }

        var registeredUser = userService.register(
                RegisterUserDTO.builder()
                        .withFirstName(model.firstName())
                        .withLastName(model.lastName())
                        .withUsername(model.username())
                        .withEmail(model.email())
                        .withPassword(model.password())
                        .build());

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        return userService.login(model.username(), model.password())
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PutMapping("{id}")
    public ResponseEntity<RegisteredUserDTO> update(@PathVariable Long id, @RequestParam("username") String username) {
        var u = userService.update(id, username);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PatchMapping("{id}/add-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RegisteredUserDTO> addUserRole(@PathVariable Long id, @RequestParam("role") String role) {
        try {
            RegisteredUserDTO updatedUser = userService.addRole(id, role);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("{id}/remove-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RegisteredUserDTO> removeUserRole(@PathVariable Long id, @RequestParam("role") String role) {
        try {
            RegisteredUserDTO updatedUser = userService.removeRole(id, role);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RegisteredUserDTO> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("{id}/profile-image")
    public User uploadProfileImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            return userService.saveProfileImage(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
