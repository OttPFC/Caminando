package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.user.LoginResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisterUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.UserService;
import com.caminando.Caminando.config.EmailConfig;
import com.caminando.Caminando.config.security.JwtUtils;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.RoleEntityRepository;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.presentationlayer.api.exceptions.InvalidLoginException;
import com.caminando.Caminando.presentationlayer.api.exceptions.NotFoundException;
import com.caminando.Caminando.presentationlayer.api.exceptions.duplicated.DuplicateEmailException;
import com.caminando.Caminando.presentationlayer.api.exceptions.duplicated.DuplicateUsernameException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    private RoleEntityRepository roles;

    @Autowired
    private Pageable defaultPageable;

    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private JwtUtils jwt;

    @Autowired
    Mapper<RegisterUserDTO, User> mapEntity;


    @Autowired
    Mapper<User, RegisteredUserDTO> mapRegisteredUser;

    @Autowired
    Mapper<User, LoginResponseDTO> mapLogin;


    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;

    @Autowired
    EmailConfig emailConfig;

    @Override
    @Transactional
    public RegisteredUserDTO register(RegisterUserDTO newUser) {
        var emailDuplicated = usersRepository.findByEmail(newUser.getEmail());
        var usernameDuplicated = usersRepository.findOneByUsername(newUser.getUsername());

        if (emailDuplicated.isPresent()) {
            throw new DuplicateEmailException(newUser.getEmail());
        } else if (usernameDuplicated.isPresent()) {
            throw new DuplicateUsernameException(newUser.getUsername());
        } else {
            try {
                var userEntity = mapEntity.map(newUser);
                var p = encoder.encode(newUser.getPassword());
                log.info("Password encrypted: {}", p);
                userEntity.setPassword(p);
                var totalUsers = this.getAll(defaultPageable);

                // Assicurati che la lista dei ruoli sia inizializzata
                if (userEntity.getRoles() == null) {
                    userEntity.setRoles(new ArrayList<>());
                }

                if (totalUsers.getTotalElements() == 0) {
                    userEntity.getRoles().add(
                            RoleEntity.builder()
                                    .withRoleType("ADMIN")
                                    .build()
                    );
                } else {
                    userEntity.getRoles().add(
                            RoleEntity.builder()
                                    .withRoleType("USER")
                                    .build()
                    );
                }
                userEntity.setEnabled(true);
                userEntity.setFollow(0L);
                userEntity.setFollowers(0L);

                var u = mapRegisteredUser.map(usersRepository.save(userEntity));
                emailConfig.sendMail(newUser.getEmail(), "Welcome!", "Thank you for registering!");
                return u;
            } catch (Exception e) {
                log.error(String.format("Exception saving user %s", usersRepository), e);
                throw new RuntimeException();
            }
        }
    }


    @Override
    public Optional<LoginResponseDTO> login(String username, String password) {
        try {
            var a = auth.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(a);

            var dto = mapLogin.map(usersRepository.findOneByUsername(username).orElse(null));
            dto.setAccessToken(jwt.generateToken(a));
            return Optional.of(dto);
        } catch (NoSuchElementException e) {
            log.error("User not found", e);
            throw new InvalidLoginException(username, password);
        } catch (AuthenticationException e) {
            log.error("Authentication failed", e);
            throw new InvalidLoginException(username, password);
        }
    }

    @Override
    public RegisteredUserDTO getById(long id) {
        return mapRegisteredUser.map(usersRepository.findById(id).orElseThrow(()-> new NotFoundException(id)));
    }

    @Override
    public Page<RegisteredUserDTO> getAll(Pageable pageable) {
        return usersRepository.findAll(pageable).map(mapRegisteredUser::map);
    }

    @Override
    public RegisteredUserDTO update(Long id, RegisteredUserDTO userDto) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // Aggiorna solo i campi presenti in userDto
        if (userDto.getFirstName() != null) user.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null) user.setLastName(userDto.getLastName());
        if (userDto.getUsername() != null) user.setUsername(userDto.getUsername());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getCity() != null) user.setCity(userDto.getCity());
        if (userDto.getBio() !=null) user.setBio(userDto.getBio());
        if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());
        if (userDto.getRoles() != null) user.setRoles(userDto.getRoles());
        user.setEnabled(userDto.isEnabled());

        User updatedUser = usersRepository.save(user);

        return mapRegisteredUser.map(updatedUser);
    }

    @Override
    public RegisteredUserDTO delete(Long id) {
        try {
            var u = usersRepository.findById(id).orElseThrow();
            usersRepository.delete(u);
            return mapRegisteredUser.map(u);
        } catch (NoSuchElementException e) {
            log.error(String.format("Cannot find user with id = %s", id), e);
            throw new RuntimeException("Cannot find user...");
        } catch (Exception e) {
            log.error(String.format("Error deleting user with id = %s", id), e);
            throw new RuntimeException();
        }
    }

    @Override
    public RegisteredUserDTO addRole(Long id, String role) {
        var roleEntity = roles.findOneByRoleType(role);
        var user = usersRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
        if (roleEntity.isEmpty()) {
            throw new RuntimeException("Il ruolo che stai tentando di aggiungere non esiste");
        } else if (user.getRoles().contains(roleEntity.get())) {
            throw new RuntimeException("Il ruolo che stai tentando di aggiungere è gia presente");
        } else {
            user.getRoles().add(roleEntity.get());
            return mapRegisteredUser.map(usersRepository.save(user));
        }
    }

    @Override
    public RegisteredUserDTO removeRole(Long id, String role) {
        var roleEntity = roles.findOneByRoleType(role);
        var user = usersRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
        if (roleEntity.isEmpty()) {
            throw new RuntimeException("Il ruolo che stai tentando di rimuovere non esiste");
        }
        if (!user.getRoles().contains(roleEntity.get())) {
            throw new RuntimeException("Il ruolo che stai tentando di rimuovere non è presente");
        }
        if (Objects.equals(role, "ADMIN")) {
            var adminUsers = usersRepository.findByRoles_RoleType(role);
            if (adminUsers.size() == 1) {
                throw new RuntimeException("Deve esistere almeno un admin sul database");
            }
        }
        if (user.getRoles().size() == 1 && user.getRoles().contains(roleEntity.get())) {
            throw new RuntimeException("Un utente deve avere almeno un ruolo");
        }
        user.getRoles().remove(roleEntity.get());
        return mapRegisteredUser.map(usersRepository.save(user));
    }

    @Override
    public RegisteredUserDTO saveProfileImage(long id, MultipartFile file) throws IOException {
        var user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String imageUrl = (String) uploadResult.get("url");

        Image image = new Image();
        image.setImageURL(imageUrl);

        user.setProfileImage(image);


        User savedUser = usersRepository.save(user);


        return mapRegisteredUser.map(savedUser);
    }

}
