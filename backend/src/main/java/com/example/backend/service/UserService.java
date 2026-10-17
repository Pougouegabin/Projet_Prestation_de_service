package com.example.backend.service;
import org.springframework.stereotype.Service;

import com.example.backend.Repository.UsersRepostory;
import com.example.backend.model.Users;
import com.example.backend.ecseption.Userecseption;

@Service
public class UserService {
    public final UsersRepostory userRepository;

    public UserService(UsersRepostory userRepository) {
        this.userRepository = userRepository;
    }
  
    public Users findById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new Userecseption("Utilisateur introuvable : " + id));
}

}
