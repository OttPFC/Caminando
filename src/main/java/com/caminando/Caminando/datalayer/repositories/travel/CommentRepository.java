package com.caminando.Caminando.datalayer.repositories.travel;


import com.caminando.Caminando.datalayer.entities.travel.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>,
        PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findByStepId(Long stepId, Pageable pageable);
}
