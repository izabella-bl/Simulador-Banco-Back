package com.banco.simulador.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.banco.simulador.config.JwtTokenUtil;
import com.banco.simulador.model.Usuarios;
import com.banco.simulador.repository.UsuarioRepository;

@Service
public class SecurityService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    CustomUserDetailService userDetails;
    
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public Usuarios authWithAuthManager(String cpf, String password) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                cpf, password);

        Authentication authentication = authenticationManager.authenticate(authRequest);

        UserDetails user = userDetails.loadUserByUsername(cpf);
        final String tokenJwt = jwtTokenUtil.generateToken(user);
        
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        Usuarios usuario = usuarioRepository.findByCpf(cpf);
        usuario.setJwt(tokenJwt);
        return usuario;
    }

    public boolean isLoggedIn(String cpf) {
        if(SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase(cpf)) return true;
        else return false;

    }
}