package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.CommentRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.CommentResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.CommentService;
import com.caminando.Caminando.datalayer.entities.travel.Comment;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.travel.CommentRepository;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<CommentRequestDTO, Comment> commentDTOToEntityMapper;

    @Autowired
    private Mapper<Comment, CommentResponseDTO> commentEntityToResponseMapper;

    @Autowired
    private Mapper<Comment, CommentRequestDTO> commentEntityToRequestDTOMapper;

    @Override
    public Page<CommentResponseDTO> getAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(commentEntityToResponseMapper::map);
    }

    @Override
    public CommentResponseDTO getById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(commentEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public CommentResponseDTO save(CommentRequestDTO commentRequestDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = commentDTOToEntityMapper.map(commentRequestDTO);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return commentEntityToResponseMapper.map(savedComment);
    }

    @Override
    @Transactional
    public CommentResponseDTO update(Long id, CommentRequestDTO updatedCommentRequestDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment existingComment = optionalComment.get();
            existingComment.setText(updatedCommentRequestDTO.getText());
            existingComment.setDate(updatedCommentRequestDTO.getDate());
            Comment updatedComment = commentRepository.save(existingComment);
            return commentEntityToResponseMapper.map(updatedComment);
        }
        return null;
    }

    @Override
    @Transactional
    public CommentResponseDTO delete(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            commentRepository.delete(comment);
            return commentEntityToResponseMapper.map(comment);
        }
        return null;
    }

    @Override
    public Page<CommentResponseDTO> getCommentsByStepId(Long stepId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByStepId(stepId, pageable);
        return comments.map(commentEntityToResponseMapper::map);
    }

    @Override
    public CommentRequestDTO mapEntityToDTO(Comment comment) {
        return commentEntityToRequestDTOMapper.map(comment);
    }
}

