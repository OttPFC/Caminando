package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.CommentDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.CommentService;
import com.caminando.Caminando.datalayer.entities.travel.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Override
    public Page<Comment> getAll(Pageable p) {
        return null;
    }

    @Override
    public Comment getById(Long id) {
        return null;
    }

    @Override
    public Comment save(CommentDTO e) {
        return null;
    }

    @Override
    public Comment update(Long id, Comment e) {
        return null;
    }

    @Override
    public Comment delete(Long id) {
        return null;
    }
}
