package com.example.crud2.backend.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud2.backend.model.Pessoa;
import com.example.crud2.backend.repositories.PessoaRepository;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/pessoas")
public class PessoaResource {
    
    private PessoaRepository pessoaRepository;

    public PessoaResource(PessoaRepository pessoaRepository) {
        super();
        this.pessoaRepository = pessoaRepository;
    }
    //Arqui temos o C do nosso CRUD
    //ou seja temos o CREATE, no spring é o save
    @PostMapping
    public ResponseEntity<Pessoa> save(@RequestBody Pessoa pessoa){
        pessoaRepository.save(pessoa);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    //Arqui temos o R do nosso CRUD
    //neste caso temos o RETRIEVED de todos
    @GetMapping
    public ResponseEntity<List<Pessoa>> getAll(){
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas = pessoaRepository.findAll();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    //Arqui temos o R do nosso CRUD
    //neste caso temos o RETRIEVED de apenas 1
    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Pessoa>> getById(@PathVariable Integer id){
        Optional<Pessoa> pessoa;
        try {
            pessoa = pessoaRepository.findById(id);
            return new ResponseEntity<Optional<Pessoa>>(pessoa, HttpStatus.OK);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<Optional<Pessoa>>(HttpStatus.NOT_FOUND);
        }
    }

    //aqui teremos o D do nosso CRUD
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Pessoa>> deleteById(@PathVariable Integer id){
        Optional<Pessoa> pessoa;
        try {
            pessoaRepository.deleteById(id);
            return new ResponseEntity<Optional<Pessoa>>(HttpStatus.OK);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<Optional<Pessoa>>(HttpStatus.NOT_FOUND);
        }
    }

    //aqui teremos o U do nosso CRUD
    @PutMapping(value="/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Integer id, @RequestBody Pessoa newPessoa){
        return pessoaRepository.findById(id)
        .map(pessoa->{
            pessoa.setNome(newPessoa.getNome());
            pessoa.setIdade(newPessoa.getIdade());
            Pessoa perssoaAtualizada = pessoaRepository.save(pessoa);
            return ResponseEntity.ok().body(perssoaAtualizada);
        }).orElse(ResponseEntity.notFound().build());
    }


    
}
