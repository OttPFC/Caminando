package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.CommentRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.CommentResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.travel.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService extends CRUDService<CommentResponseDTO, CommentRequestDTO> {

    Page<CommentResponseDTO> getCommentsByStepId(Long stepId, Pageable pageable);
    CommentRequestDTO mapEntityToDTO(Comment comment);
}
