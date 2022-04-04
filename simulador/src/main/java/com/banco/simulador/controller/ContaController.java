package com.banco.simulador.controller;


import com.banco.simulador.model.Transferencia;
import com.banco.simulador.services.ContaService;
import com.banco.simulador.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ContaController {

    @Autowired
    private ContaService contaService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/transferencia")
    public String tranferencia(@RequestBody Transferencia transferenciaConta) throws Exception {
        contaService.transferir(transferenciaConta.getIdOrigem(),transferenciaConta.getCpfDestinario(),transferenciaConta.getValor());
        return "Tranferencia feita com sucesso!";
    }


}
