package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.user.LoginResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisterUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.UserService;
import com.caminando.Caminando.datalayer.entities.travel.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public RegisteredUserDTO register(RegisterUserDTO user) {
        return null;
    }

    @Override
    public Optional<LoginResponseDTO> login(String username, String password) {
        return Optional.empty();
    }

    @Override
    public RegisteredUserDTO getById(long id) {
        return null;
    }

    @Override
    public Page<User> getAll(Pageable p) {
        return null;
    }

    @Override
    public RegisteredUserDTO update(long id, String username) {
        return null;
    }

    @Override
    public RegisteredUserDTO delete(Long id) {
        return null;
    }

    @Override
    public RegisteredUserDTO addRole(Long id, String role) {
        return null;
    }

    @Override
    public RegisteredUserDTO removeRole(Long id, String role) {
        return null;
    }

    @Override
    public User saveProfileImage(long id, MultipartFile file) throws IOException {
        return null;
    }
}
