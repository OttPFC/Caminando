package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.PlaceToStayDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.PlaceToStayResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.PlaceToStayService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
import com.caminando.Caminando.datalayer.repositories.itinerary.PlaceToStayRepository;
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
public class PlaceToStayServiceImpl implements PlaceToStayService {

    @Autowired
    private PlaceToStayRepository repo;

    @Autowired
    private Mapper<PlaceToStayDTO, PlaceToStay> mapper;

    @Autowired
    private Mapper<PlaceToStay, PlaceToStayResponseDTO> placeToStayEntityToResponseMapper;

    @Autowired
    private EntityUtils utils;

    @Override
    public Page<PlaceToStayResponseDTO> getAll(Pageable pageable) {
        Page<PlaceToStay> placesToStay = repo.findAll(pageable);
        return placesToStay.map(placeToStayEntityToResponseMapper::map);
    }

    @Override
    public PlaceToStayResponseDTO getById(Long id) {
        Optional<PlaceToStay> placeToStay = repo.findById(id);
        return placeToStay.map(placeToStayEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public PlaceToStayResponseDTO save(PlaceToStayDTO placeToStayDTO) {
        PlaceToStay placeToStay = mapper.map(placeToStayDTO);
        PlaceToStay savedPlaceToStay = repo.save(placeToStay);
        return placeToStayEntityToResponseMapper.map(savedPlaceToStay);
    }

    @Override
    @Transactional
    public PlaceToStayResponseDTO update(Long id, PlaceToStayDTO placeToStayDTO) {
        Optional<PlaceToStay> optionalPlaceToStay = repo.findById(id);
        if (optionalPlaceToStay.isPresent()) {
            PlaceToStay existingPlaceToStay = optionalPlaceToStay.get();
            utils.copy(placeToStayDTO, existingPlaceToStay);
            PlaceToStay updatedPlaceToStay = repo.save(existingPlaceToStay);
            return placeToStayEntityToResponseMapper.map(updatedPlaceToStay);
        }
        return null;
    }

    @Override
    @Transactional
    public PlaceToStayResponseDTO delete(Long id) {
        Optional<PlaceToStay> optionalPlaceToStay = repo.findById(id);
        if (optionalPlaceToStay.isPresent()) {
            PlaceToStay placeToStay = optionalPlaceToStay.get();
            repo.delete(placeToStay);
            return placeToStayEntityToResponseMapper.map(placeToStay);
        }
        return null;
    }
}
