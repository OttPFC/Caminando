package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.CommentDTO;
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

import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<CommentDTO, Comment> commentDTOToEntityMapper;

    @Autowired
    private Mapper<Comment, CommentDTO> commentEntityToDTOMapper;

    @Override
    public Page<Comment> getAll(Pageable p) {
        return commentRepository.findAll(p);
    }

    @Override
    public Comment getById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    @Override
    public Comment save(CommentDTO commentDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = commentDTOToEntityMapper.map(commentDTO);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long id, Comment updatedComment) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setText(updatedComment.getText());
            comment.setDate(updatedComment.getDate());
            return commentRepository.save(comment);
        }
        return null;
    }

    @Override
    public Comment delete(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            commentRepository.delete(comment);
            return comment;
        }
        return null;
    }

    @Override
    public Page<Comment> getCommentsByStepId(Long stepId, Pageable pageable) {
        return commentRepository.findByStepId(stepId, pageable);
    }

    @Override
    public CommentDTO mapEntityToDTO(Comment comment) {
        return commentEntityToDTOMapper.map(comment);
    }
}
