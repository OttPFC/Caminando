package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.PositionService;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.datalayer.repositories.travel.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

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
}
