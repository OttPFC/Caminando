package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.CommentDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.travel.Comment;
import com.caminando.Caminando.presentationlayer.api.models.travel.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService extends CRUDService<Comment, CommentDTO> {

    Page<Comment> getCommentsByStepId(Long stepId, Pageable pageable);
    CommentDTO mapEntityToDTO(Comment comment);

    Comment saveComment(Long stepId, CommentModel model);
}
