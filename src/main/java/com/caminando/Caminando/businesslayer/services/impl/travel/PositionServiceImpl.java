package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.PositionResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.PositionService;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.datalayer.repositories.travel.PositionRepository;
import com.caminando.Caminando.presentationlayer.api.models.travel.PositionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<User, RegisteredUserDTO> userEntityToUserDTOMapper;

    @Autowired
    private Mapper<PositionRequestDTO, Position> positionDTOToEntityMapper;

    @Autowired
    private Mapper<Position, PositionResponseDTO> positionEntityToResponseMapper;

    @Autowired
    private Mapper<Position, PositionRequestDTO> mapPositionEntityToRequestDTOMapper;

    @Override
    public Page<PositionResponseDTO> getAll(Pageable pageable) {
        Page<Position> positions = positionRepository.findAll(pageable);
        return positions.map(positionEntityToResponseMapper::map);
    }

    @Override
    public PositionResponseDTO getById(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + id));
        return positionEntityToResponseMapper.map(position);
    }

    @Override
    @Transactional
    public PositionResponseDTO update(Long id, PositionRequestDTO positionRequestDTO) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + id));

        position.setLatitude(positionRequestDTO.getLatitude());
        position.setLongitude(positionRequestDTO.getLongitude());
        position.setTimestamp(positionRequestDTO.getTimestamp());
        position.setNomeLocalita(positionRequestDTO.getNomeLocalita());

        Position updatedPosition = positionRepository.save(position);
        return positionEntityToResponseMapper.map(updatedPosition);
    }

    @Override
    @Transactional
    public PositionResponseDTO delete(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + id));
        positionRepository.delete(position);
        return positionEntityToResponseMapper.map(position);
    }

    @Override
    public PositionRequestDTO getNomeLocalitaAndTimestampById(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + id));
        return mapPositionEntityToRequestDTOMapper.map(position);
    }

    @Override
    @Transactional
    public PositionResponseDTO save(PositionRequestDTO positionRequestDTO) {
        Position position = positionDTOToEntityMapper.map(positionRequestDTO);
        Position savedPosition = positionRepository.save(position);
        return positionEntityToResponseMapper.map(savedPosition);
    }

    @Override
    @Transactional
    public PositionResponseDTO savePosition(PositionRequestDTO positionRequestDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        RegisteredUserDTO userDTO = userEntityToUserDTOMapper.map(user);

        PositionRequestDTO newPositionRequestDTO = PositionRequestDTO.builder()
                .withLatitude(positionRequestDTO.getLatitude())
                .withLongitude(positionRequestDTO.getLongitude())
                .withTimestamp(LocalDate.now())
                .withNomeLocalita(positionRequestDTO.getNomeLocalita())
                .withUser(userDTO)
                .build();

        Position newPosition = positionDTOToEntityMapper.map(newPositionRequestDTO);
        Position savedPosition = positionRepository.save(newPosition);
        return positionEntityToResponseMapper.map(savedPosition);
    }
}


