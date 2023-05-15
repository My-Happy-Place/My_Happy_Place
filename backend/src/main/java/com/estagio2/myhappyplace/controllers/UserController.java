package com.estagio2.myhappyplace.controllers;

import com.estagio2.myhappyplace.dto.MovieDTO;
import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.entities.User;
import com.estagio2.myhappyplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarPorId(@PathVariable Long id){
        UserDTO userDTO = userService.findById(id);
        return ResponseEntity.ok().body(userDTO);
    }
    @GetMapping("/{id}/findFavorites")
    public ResponseEntity<List<?>> buscarFavoritos(@PathVariable Long id,
                                                          @RequestParam(value = "descricao", required = false) String descricao){
        List<?> allFavorites = userService.findFavorites(id, descricao);
        return ResponseEntity.ok().body(allFavorites);
    }

    @PostMapping
    public ResponseEntity<UserDTO> criar(@RequestBody UserDTO userDTO){
        Optional<User> existe = userService.thereIsUser(userDTO.getId());
        if (existe.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        userDTO = userService.insert(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @PutMapping
    public ResponseEntity<UserDTO> atualizar(@RequestBody UserDTO userDTO){
        UserDTO aux = userService.findById(userDTO.getId());
        if (aux.getId() == null){
            return ResponseEntity.notFound().build();
        }
        UserDTO user = userService.update(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        UserDTO aux = userService.findById(id);
        if(aux.getId() == null){
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
