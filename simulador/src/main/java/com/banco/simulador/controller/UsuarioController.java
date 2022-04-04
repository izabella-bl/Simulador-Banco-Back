package com.banco.simulador.controller;


import com.banco.simulador.model.Usuarios;
import com.banco.simulador.services.ContaService;
import com.banco.simulador.services.SecurityService;
import com.banco.simulador.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.banco.simulador.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {


    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ContaService contaService;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public Usuarios login(@RequestBody Usuarios usuarios) {

        return securityService.authWithAuthManager(usuarios.getCpf(), usuarios.getPassword());
    }

    @GetMapping("/isLogado")
    public boolean isLogado(@RequestBody String cpf) {
        return securityService.isLoggedIn(cpf);
    }

    @GetMapping("/lista")
    @PreAuthorize("hasRole('USER')")
    public List<Usuarios> listarUsuarios() {
        List<Usuarios> usuarios = (List<Usuarios>) repository.findAll();
        return usuarios;
    }

    @GetMapping("/home/{id}")
    public Optional<Usuarios> home(@PathVariable Long id) {
        Optional<Usuarios> usuarios = repository.findById(id);
        return usuarios;
    }

    @CrossOrigin(origins = "http://localhost:3000/cadastro")
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody Usuarios model) {
        boolean validarCpf = usuarioService.isValidaCpf(model.getCpf());
        boolean validarEmail = usuarioService.isValidarEmail(model.getEmail());
        boolean isExiste = usuarioService.isUsuarioExiste(model);
        if (validarCpf && validarEmail && isExiste) {
            String senha = usuarioService.criptografarSenha(model);
            model.setPassword(senha);
            model = contaService.inicializarConta(model);
            repository.save(model);
            return new ResponseEntity(HttpStatus.OK);
        } else if (!validarCpf || !validarEmail) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable long id) {
        repository.deleteById(id);
        return "deletado com sucesso";
    }

    @PutMapping("/{id}")
    public String update(@RequestBody Usuarios model, @PathVariable int id) {
        if (model.getId() == id) {
            repository.save(model);
            return "alterado com sucesso";
        }
        return "id da url diferente do body";
    }

}
