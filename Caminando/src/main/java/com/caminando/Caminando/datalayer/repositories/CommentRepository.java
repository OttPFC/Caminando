package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.travel.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
