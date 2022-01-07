package com.example.todolistcrud.controller;

import com.example.todolistcrud.Models.Tutorials;
import com.example.todolistcrud.repository.TutorilasRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api")
public class TutoController {

    @Autowired
    private TutorilasRepo tutorilasRepo;

    @GetMapping("/tutorils")
    public ResponseEntity<List<Tutorials>> getAllTutorials(@RequestBody(required = false) String title){

        try{
            List<Tutorials> tutorials  =new ArrayList<Tutorials>();
            if(title == null)
                tutorilasRepo.findAll().forEach(tutorials::add);
            else tutorilasRepo.findByTitleContaining(title).forEach(tutorials::add);

            if (tutorials.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return  new ResponseEntity<>(tutorials,HttpStatus.OK);
             }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorials> getTutorilsbyId(@PathVariable("id") long id){
        Optional<Tutorials> tutorialData = tutorilasRepo.findById(id);
         if(tutorialData.isPresent()){
             return new ResponseEntity<>(tutorialData.get(),HttpStatus.OK);
         }
         else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }
    @PostMapping("/tutorials")
    public ResponseEntity<Tutorials> createTutorilass(@RequestBody Tutorials tutorials) {
        try {
            Tutorials tutorial = tutorilasRepo.save(new Tutorials(tutorials.getTitle(), tutorials.getDiscription(), false));

            return new ResponseEntity<>(tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorials> updateTutorial(@PathVariable("id") long id,@RequestBody Tutorials tutorials){
        Optional<Tutorials> tutorialsData = tutorilasRepo.findById(id);

        if(tutorialsData.isPresent()){
            Tutorials tutorial = tutorialsData.get();
            tutorial.setTitle(tutorials.getTitle());
            tutorial.setDiscription(tutorials.getDiscription());
            tutorial.setPublished(tutorials.isPublished());
            return  new ResponseEntity<>(tutorilasRepo.save(tutorial),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorials(@PathVariable("id") long id){
        try {
            tutorilasRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("tutorials/")
    public ResponseEntity<HttpStatus> deleteAll(){
        try {
            tutorilasRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("tutorials/published")
    public ResponseEntity<List<Tutorials>>findbyPublished(){
        try{
            List<Tutorials> tutorials = tutorilasRepo.findByPublished(true);
            if(tutorials.isEmpty()){
                return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
