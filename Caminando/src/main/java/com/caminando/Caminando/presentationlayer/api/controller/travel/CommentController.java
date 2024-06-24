package com.caminando.Caminando.presentationlayer.api.controller.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.CommentDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.CommentService;
import com.caminando.Caminando.datalayer.entities.travel.Comment;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.presentationlayer.api.models.travel.CommentModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    public Mapper<User, RegisteredUserDTO> mapUserEntity2RegisteredUser;

    @PostMapping("/step/{stepId}")
    public ResponseEntity<?> createComment(@PathVariable Long stepId, @RequestBody @Validated CommentModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            return ResponseEntity.badRequest().body(validator.getAllErrors());
        }
        try {
            Comment createdComment = commentService.saveComment(stepId,model);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getById(id);
        if (comment != null) {
            CommentDTO commentDTO = commentService.mapEntityToDTO(comment);
            return new ResponseEntity<>(commentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Comment>> getAllComments(Pageable pageable) {
        Page<Comment> comments = commentService.getAll(pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/step/{stepId}")
    public ResponseEntity<Page<Comment>> getCommentsByStepId(@PathVariable Long stepId, Pageable pageable) {
        Page<Comment> comments = commentService.getCommentsByStepId(stepId, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody @Validated CommentModel commentModel, BindingResult validator) {
        if (validator.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Comment updatedComment = commentService.update(id, Comment.builder().withText(commentModel.text()).build());
        if (updatedComment != null) {
            CommentDTO updatedCommentDTO = commentService.mapEntityToDTO(updatedComment);
            return new ResponseEntity<>(updatedCommentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        Comment deletedComment = commentService.delete(id);
        if (deletedComment != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
