package com.example.todolistcrud.repository;

import com.example.todolistcrud.Models.Tutorials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorilasRepo extends JpaRepository<Tutorials,Long> {


    List<Tutorials> findByPublished(Boolean published);
    List<Tutorials> findByTitleContaining(String title);
}
