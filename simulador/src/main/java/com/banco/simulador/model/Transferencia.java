package com.banco.simulador.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transferencia {

    private Long idOrigem;
    private  String cpfDestinario;
    private double valor;
}
