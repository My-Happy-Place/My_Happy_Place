package com.estagio2.myhappyplace.service;

import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.entities.User;
import com.estagio2.myhappyplace.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        Optional obj = userRepository.findById(id);
        if(obj.isPresent()){
            User user = (User) obj.get();
            return new UserDTO(user);
        }
        return new UserDTO();
    }

    @Transactional
    public UserDTO insert(UserDTO userDTO){
        User user = new User(null, userDTO.getName(), null, null);
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    public Optional<User> thereIsUser(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    public UserDTO update(UserDTO userDTO){
        User obj = userRepository.findById(userDTO.getId()).get();
        obj.setName(userDTO.getName());
        obj = userRepository.save(obj);
        return new UserDTO(obj);
    }

    @Transactional
    public String delete(Long id){
        Optional<User> obj = userRepository.findById(id);
        if(obj.isPresent()){
            userRepository.deleteById(id);
            return "Usuario excluido com sucesso!";
        }
        return "Usuario não encontrado!";
    }
}
