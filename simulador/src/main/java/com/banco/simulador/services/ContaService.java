package com.banco.simulador.services;

import com.banco.simulador.model.Conta;
import com.banco.simulador.model.Usuarios;
import com.banco.simulador.repository.ContaRepository;
import com.banco.simulador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;


@Service
public class ContaService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private HistoricoService historicoService;

    public void transferir(Long idOrigem,String cpfDestino,double valorTranferencia) throws Exception {

        if((idOrigem == null) || (cpfDestino.isEmpty())){
            throw new Exception("Id nulo");
        }

        Usuarios usuarioOrigem = usuarioRepository.findById(idOrigem).get();
        Usuarios usuarioDestino = usuarioRepository.findByCpf(cpfDestino);

        if((usuarioOrigem == null) || (usuarioDestino == null)){
            throw new Exception("Usuario nulo");
        }

        if(valorTranferencia <= Double.parseDouble(usuarioOrigem.getConta().getSaldo().toString())){
            BigDecimal saldoDestinatario = usuarioDestino.getConta().getSaldo();
            BigDecimal saldoOrigem = usuarioOrigem.getConta().getSaldo();
            BigDecimal valorTransferido = BigDecimal.valueOf(valorTranferencia);
            usuarioDestino.getConta().setSaldo(valorTransferido.add(saldoDestinatario));
            usuarioOrigem.getConta().setSaldo(saldoOrigem.subtract(valorTransferido));
            usuarioDestino = usuarioRepository.save(usuarioDestino);
            usuarioOrigem = usuarioRepository.save(usuarioOrigem);
            historicoService.salvarHistorico(usuarioOrigem,usuarioDestino,valorTransferido);

        }else{
            throw new Exception("Saldo insuficiente");
        }

    }

    public Usuarios inicializarConta(Usuarios usuario){
        if(usuario != null ){
            Conta conta = new Conta();
            conta.setSaldo(new BigDecimal(1000));
            usuario.setConta(salvarConta(conta));
            return usuario;
        }

        return null;
    }

    public Conta salvarConta (Conta conta){
        if(conta != null){
            return contaRepository.save(conta);
        }
        return  null;
    }


}
