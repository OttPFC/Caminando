package com.caminando.Caminando.presentationlayer.api.controller.travel;


import com.caminando.Caminando.businesslayer.services.dto.travel.CommentRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.CommentResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.CommentService;
import com.caminando.Caminando.presentationlayer.api.models.travel.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody @Validated CommentModel commentModel, BindingResult validator) {
        if (validator.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CommentRequestDTO commentRequestDTO = CommentRequestDTO.builder()
                .withText(commentModel.text())
                .build();

        CommentResponseDTO createdComment = commentService.save(commentRequestDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable Long id) {
        CommentResponseDTO comment = commentService.getById(id);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDTO>> getAllComments(Pageable pageable) {
        Page<CommentResponseDTO> comments = commentService.getAll(pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/step/{stepId}")
    public ResponseEntity<Page<CommentResponseDTO>> getCommentsByStepId(@PathVariable Long stepId, Pageable pageable) {
        Page<CommentResponseDTO> comments = commentService.getCommentsByStepId(stepId, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long id, @RequestBody @Validated CommentModel commentModel, BindingResult validator) {
        if (validator.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CommentRequestDTO updatedCommentRequestDTO = CommentRequestDTO.builder()
                .withText(commentModel.text())
                .build();

        CommentResponseDTO updatedComment = commentService.update(id, updatedCommentRequestDTO);
        if (updatedComment != null) {
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        CommentResponseDTO deletedComment = commentService.delete(id);
        if (deletedComment != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

