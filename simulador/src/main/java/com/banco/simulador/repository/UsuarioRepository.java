package com.banco.simulador.repository;

import com.banco.simulador.model.Usuarios;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuarios,Long> {

    Usuarios findByCpf(String cpf);
}
