package com.caminando.Caminando.presentationlayer.api;

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
    private UserService user;

    @GetMapping
    public ResponseEntity<Page<User>> getEvents (Pageable p) {
        var allUsers = user.getAll(p);
        var headers = new HttpHeaders();
        headers.add("Totale", String.valueOf(allUsers.getTotalElements()));
        return new ResponseEntity<>(allUsers, headers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RegisteredUserDTO> getEvent (@PathVariable Long id) {
        var u = user.getById(id);
        return new ResponseEntity<>(u, HttpStatus.FOUND);
    }
    @PostMapping
    public ResponseEntity<RegisteredUserDTO> register(@RequestBody @Validated RegisterUserModel model, BindingResult validator){
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }

        var registeredUser = user.register(
                RegisterUserDTO.builder()
                        .withFirstName(model.firstName())
                        .withLastName(model.lastName())
                        .withUsername(model.username())
                        .withEmail(model.email())
                        .withPassword(model.password())
                        .build());

        return  new ResponseEntity<> (registeredUser, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw  new ApiValidationException(validator.getAllErrors());
        }
        return new ResponseEntity<>(user.login(model.username(), model.password()).orElseThrow(), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<RegisteredUserDTO> updateUser (
            @PathVariable Long id,
            @RequestParam("username") String username
    ){
        var u = user.update(id, username);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PatchMapping("{id}/addToRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RegisteredUserDTO> addUserRole (
            @PathVariable Long id,
            @RequestParam("role") String role
    ){
        var u = user.addRole(id, role);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PatchMapping("{id}/removeToRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RegisteredUserDTO> removeUserRole (
            @PathVariable Long id,
            @RequestParam("role") String role
    ){
        var u = user.removeRole(id, role);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RegisteredUserDTO> deleteUser (
            @PathVariable Long id
    ) {
        var e = user.delete(id);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @PatchMapping("/{id}/profile-image")
    public ResponseEntity<User> uploadProfileImage(@PathVariable long id, @RequestParam("file") MultipartFile file) {
        try {
            User profile = user.saveProfileImage(id, file);
            return ResponseEntity.ok(profile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
