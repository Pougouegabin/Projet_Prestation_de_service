package com.example.backend.controller;

import com.example.backend.Repository.UsersRepostory;
import com.example.backend.model.Users;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {
    private final UsersRepostory users;
    private final PasswordEncoder encoder;
    public CompteController(UsersRepostory users, PasswordEncoder encoder) { this.users=users; this.encoder=encoder; }

    public record Inscription(@NotBlank String nom, String prenom, @Email @NotBlank String email,
                              @NotBlank @Size(min=8) String motDePasse, String telephone) {}
    public record CompteCree(Long id, String nom, String prenom, String email, String telephone) {}

    @PostMapping("/inscription") @ResponseStatus(HttpStatus.CREATED)
    public CompteCree inscrire(@Valid @RequestBody Inscription dto) {
        if(users.existsByEmailIgnoreCase(dto.email())) throw new ResponseStatusException(HttpStatus.CONFLICT,"Cette adresse email est déjà utilisée.");
        Users u=new Users(); u.setName(dto.nom()); u.setPrenom(dto.prenom()); u.setEmail(dto.email().trim().toLowerCase());
        u.setPassword(encoder.encode(dto.motDePasse())); u.setTelephone(dto.telephone());
        u=users.save(u); return new CompteCree(u.getId(),u.getName(),u.getPrenom(),u.getEmail(),u.getTelephone());
    }
}
