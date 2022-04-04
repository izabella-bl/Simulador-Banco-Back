package com.banco.simulador.repository;

import com.banco.simulador.model.Historico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HistoricoRepository extends PagingAndSortingRepository<Historico,Long> {

    List<Historico> findByUsuarios_Id(Long usuarios_id);

    @Query("SELECT h FROM Historico h  WHERE MONTH(h.dataInicial) = ?1 AND h.usuarios.id = ?2")
    List<Historico> findByMes(Integer mes, Long idUsuario);
}
