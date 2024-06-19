package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.StepDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.StepService;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.repositories.travel.StepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private Mapper<StepDTO, Step> stepDTOToEntityMapper;

    @Autowired
    private Mapper<Step, StepDTO> stepEntityToDTOMapper;

    @Override
    public Page<Step> getAll(Pageable pageable) {
        return stepRepository.findAll(pageable);
    }

    @Override
    public Step getById(Long id) {
        Optional<Step> step = stepRepository.findById(id);
        return step.orElse(null);
    }

    @Override
    @Transactional
    public Step save(StepDTO stepDTO) {
        Step step = stepDTOToEntityMapper.map(stepDTO);
        return stepRepository.save(step);
    }

    @Override
    @Transactional
    public Step update(Long id, Step updatedStep) {
        Optional<Step> optionalStep = stepRepository.findById(id);
        if (optionalStep.isPresent()) {
            Step existingStep = optionalStep.get();
            existingStep.setDescription(updatedStep.getDescription());
            existingStep.setLikes(updatedStep.getLikes());
            existingStep.setArrivalDate(updatedStep.getArrivalDate());
            existingStep.setDepartureDate(updatedStep.getDepartureDate());
            return stepRepository.save(existingStep);
        }
        return null;
    }

    @Override
    @Transactional
    public Step delete(Long id) {
        Optional<Step> optionalStep = stepRepository.findById(id);
        if (optionalStep.isPresent()) {
            Step step = optionalStep.get();
            stepRepository.delete(step);
            return step;
        }
        return null;
    }

    @Override
    public StepDTO mapEntityToDTO(Step step) {
        return null;
    }
}
