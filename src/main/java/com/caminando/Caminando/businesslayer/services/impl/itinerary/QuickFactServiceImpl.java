package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.QuickFactsDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.QuickFactsResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.QuickFactService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.QuickFacts;
import com.caminando.Caminando.datalayer.repositories.itinerary.QuickFatcsRepository;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class QuickFactServiceImpl implements QuickFactService {

    @Autowired
    private QuickFatcsRepository repo;

    @Autowired
    private Mapper<QuickFactsDTO, QuickFacts> mapper;

    @Autowired
    private Mapper<QuickFacts, QuickFactsResponseDTO> quickFactsEntityToResponseMapper;

    @Autowired
    private EntityUtils utils;

    @Override
    public Page<QuickFactsResponseDTO> getAll(Pageable pageable) {
        Page<QuickFacts> quickFacts = repo.findAll(pageable);
        return quickFacts.map(quickFactsEntityToResponseMapper::map);
    }

    @Override
    public QuickFactsResponseDTO getById(Long id) {
        Optional<QuickFacts> quickFact = repo.findById(id);
        return quickFact.map(quickFactsEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public QuickFactsResponseDTO save(QuickFactsDTO quickFactsDTO) {
        QuickFacts quickFact = mapper.map(quickFactsDTO);
        QuickFacts savedQuickFact = repo.save(quickFact);
        return quickFactsEntityToResponseMapper.map(savedQuickFact);
    }

    @Override
    @Transactional
    public QuickFactsResponseDTO update(Long id, QuickFactsDTO quickFactsDTO) {
        Optional<QuickFacts> optionalQuickFact = repo.findById(id);
        if (optionalQuickFact.isPresent()) {
            QuickFacts existingQuickFact = optionalQuickFact.get();
            utils.copy(quickFactsDTO, existingQuickFact);
            QuickFacts updatedQuickFact = repo.save(existingQuickFact);
            return quickFactsEntityToResponseMapper.map(updatedQuickFact);
        }
        return null;
    }

    @Override
    @Transactional
    public QuickFactsResponseDTO delete(Long id) {
        Optional<QuickFacts> optionalQuickFact = repo.findById(id);
        if (optionalQuickFact.isPresent()) {
            QuickFacts quickFact = optionalQuickFact.get();
            repo.delete(quickFact);
            return quickFactsEntityToResponseMapper.map(quickFact);
        }
        return null;
    }
}
