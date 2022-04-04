package com.banco.simulador.model;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "historico")
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataInicial;
    private String descricao;
    private BigDecimal valor;
    private BigDecimal saldoFinal;
    private int tipo;

    @ManyToOne
    private Usuarios usuarios;

}
