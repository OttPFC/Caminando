package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.PositionService;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.datalayer.repositories.travel.PositionRepository;
import com.caminando.Caminando.presentationlayer.api.models.travel.PositionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<User, RegisteredUserDTO> userEntityToUserDTOMapper;
    @Autowired
    private Mapper<PositionDTO, Position> positionDTOToEntityMapper;

    @Autowired
    private Mapper<Position, PositionDTO> positionEntityToDTOMapper;

    @Override
    public Page<Position> getAll(Pageable pageable) {
        return positionRepository.findAll(pageable);
    }

    @Override
    public Position getById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + id));
    }

    @Override
    @Transactional
    public Position save(PositionDTO positionDTO) {
        Position position = positionDTOToEntityMapper.map(positionDTO);
        return positionRepository.save(position);
    }

    @Override
    @Transactional
    public Position update(Long id, Position positionDetails) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + id));

        position.setLatitude(positionDetails.getLatitude());
        position.setLongitude(positionDetails.getLongitude());
        position.setTimestamp(positionDetails.getTimestamp());
        position.setNomeLocalita(positionDetails.getNomeLocalita());

        return positionRepository.save(position);
    }

    @Override
    @Transactional
    public Position delete(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + id));
        positionRepository.delete(position);
        return position;
    }

    @Override
    public PositionDTO getNomeLocalitaAndTimestampById(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Position not found with id: " + id));
        return positionEntityToDTOMapper.map(position);
    }

    @Override
    public PositionDTO mapEntityToDTO(Position position) {
        return positionEntityToDTOMapper.map(position);
    }

    @Override
    public Position save(PositionModel model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        RegisteredUserDTO userDTO = userEntityToUserDTOMapper.map(user);

        PositionDTO positionDTO = PositionDTO.builder()
                .withLatitude(model.latitude())
                .withLongitude(model.longitude())
                .withTimestamp(Instant.now())
                .withNomeLocalita(model.nomeLocalita())
                .withUser(userDTO)
                .build();

        Position newPosition = positionDTOToEntityMapper.map(positionDTO);

        return positionRepository.save(newPosition);
    }
}
