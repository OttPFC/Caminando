package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.CommentDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.StepDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.CommentService;
import com.caminando.Caminando.datalayer.entities.travel.Comment;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.travel.CommentRepository;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.datalayer.repositories.travel.StepRepository;
import com.caminando.Caminando.presentationlayer.api.models.travel.CommentModel;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private Mapper<Step, StepDTO> stepToDTO;
    @Autowired
    private Mapper<User, RegisteredUserDTO> userEntityToUserDTOMapper;
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



    @Override
    @Transactional
    public Comment saveComment(Long stepId, CommentModel model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        RegisteredUserDTO userDTO = userEntityToUserDTOMapper.map(user);

        Step step = stepRepository.findById(stepId).orElseThrow(() -> new RuntimeException("Not Found"));

        CommentDTO commentDTO = CommentDTO.builder()
                .withText(model.text())
                .withDate(LocalDate.now())
                .withStep(stepToDTO.map(step))
                .withUser(userDTO)
                .build();

        Comment savedComment = commentRepository.save(commentDTOToEntityMapper.map(commentDTO));
        return savedComment;

    }
}
