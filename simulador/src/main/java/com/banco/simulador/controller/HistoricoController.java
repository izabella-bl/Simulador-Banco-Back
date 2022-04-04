package com.banco.simulador.controller;


import com.banco.simulador.model.Historico;
import com.banco.simulador.model.HistoricoMensal;
import com.banco.simulador.repository.HistoricoRepository;
import com.banco.simulador.services.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class HistoricoController {

    @Autowired
    private HistoricoRepository historicoRepository;
    @Autowired
    private HistoricoService historicoService;



    @GetMapping("/historico")
    public List<Historico> historicos(@RequestParam Long idUsuario) {
        return (List<Historico>) historicoRepository.findByUsuarios_Id(idUsuario);
    }

    @GetMapping("/historico/mes")
    public List<Historico> historicoMes(@RequestParam int mes, @RequestParam Long idUsuario) {
        return  historicoService.buscarHistoricoMes(mes, idUsuario);
    }
}
