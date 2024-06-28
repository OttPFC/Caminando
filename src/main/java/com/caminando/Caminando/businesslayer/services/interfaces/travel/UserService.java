package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.user.LoginResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisterUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.datalayer.entities.travel.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService{
    RegisteredUserDTO register(RegisterUserDTO user);

    Optional<LoginResponseDTO> login(String username, String password);

    RegisteredUserDTO getById(long id);

    Page<User> getAll(Pageable p);

    RegisteredUserDTO update(Long id, RegisteredUserDTO userDto);

    RegisteredUserDTO delete(Long id);

    RegisteredUserDTO addRole(Long id, String role);
    RegisteredUserDTO removeRole(Long id, String role);

    User saveProfileImage(long id, MultipartFile file) throws IOException;
}
