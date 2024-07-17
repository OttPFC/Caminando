package com.caminando.Caminando.presentationlayer.api.controller;

import com.caminando.Caminando.businesslayer.services.dto.user.LoginResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisterUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.UserService;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.exceptions.DisabledUserException;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<Page<RegisteredUserDTO>> getUsers(Pageable pageable) {
        System.out.println("first try");
        Page<RegisteredUserDTO> allUsers = userService.getAll(pageable);
        System.out.println(allUsers);
        System.out.println("first second");
        HttpHeaders headers = new HttpHeaders();
        System.out.println("first tres");
        headers.add("Totale", String.valueOf(allUsers.getTotalElements()));
        System.out.println("first four");
        return new ResponseEntity<>(allUsers, headers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RegisteredUserDTO> getUser(@PathVariable Long id) {
        System.out.println("first try");
        try {
            RegisteredUserDTO userDTO = userService.getById(id);
            System.out.println(userDTO);
            System.out.println("second try");
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RegisteredUserDTO> register(@RequestBody @Validated RegisterUserModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }

        RegisteredUserDTO registeredUser = userService.register(
                RegisterUserDTO.builder()
                        .withFirstName(model.firstName())
                        .withLastName(model.lastName())
                        .withUsername(model.username())
                        .withEmail(model.email())
                        .withCity(model.city())
                        .withPassword(model.password())
                        .build());

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }

        Optional<LoginResponseDTO> loginResponse = userService.login(model.username(), model.password());

        if (loginResponse.isPresent()) {
            LoginResponseDTO responseDTO = loginResponse.get();

            // Check if user is enabled before returning the response
            if (!responseDTO.getUser().isEnabled()) {
                throw new DisabledUserException(responseDTO.getUser().getUsername());
            }

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<RegisteredUserDTO>> searchUsers(@RequestParam String firstName) {
        List<RegisteredUserDTO> users = userService.searchUsersByFirstName(firstName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RegisteredUserDTO> update(@PathVariable Long id, @RequestBody RegisteredUserDTO userDto) {
        var updatedUser = userService.update(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
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
    public ResponseEntity<RegisteredUserDTO> uploadProfileImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            RegisteredUserDTO updatedUser = userService.saveProfileImage(id, file);
            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {

            return ResponseEntity.status(500).body(null);
        } catch (NotFoundException e) {

            return ResponseEntity.status(404).body(null);
        }
    }
}
