package com.aldoj.orca_plus_api.services;


import com.aldoj.orca_plus_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {


    @Autowired
    private UserRepository repository;

    // ESSA CLASSE É CHAMADA PELO SPRING PARA VALIDAR SE UM NOVO USUARIO JÁ ESTÁ REGISTRADO NO BANCO DE DADOS
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }
}
