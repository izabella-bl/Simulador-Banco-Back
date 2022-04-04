package com.banco.simulador.services;


import br.com.caelum.stella.validation.CPFValidator;
import com.banco.simulador.model.Usuarios;
import com.banco.simulador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String criptografarSenha(Usuarios model){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senha = passwordEncoder.encode(model.getPassword());

        return senha;
    }


    public boolean isValidaCpf(String cpf){
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean isValidarEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }

    public  boolean isUsuarioExiste(Usuarios usuario){
        Usuarios usuarioExiste  =   usuarioRepository.findByCpf(usuario.getCpf());
        if (usuarioExiste == null){
            return true;
        }else{
            return  false;
        }
    }




}
