package com.banco.simulador.repository;

import com.banco.simulador.model.Conta;
import org.springframework.data.repository.CrudRepository;

public interface ContaRepository  extends CrudRepository<Conta,Long> {

}