package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
@Embeddable
public class Preco {
	private BigDecimal valor;
	private TipoPreco tipo;
}