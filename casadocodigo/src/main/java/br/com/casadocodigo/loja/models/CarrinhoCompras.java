package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class CarrinhoCompras {
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();
	
	
	public void add(CarrinhoItem item){
		itens.put(item, getQuantidade(item)+1);
	}
	
	private int getQuantidade(CarrinhoItem item){
		if(!itens.containsKey(item)){
			itens.put(item, 0);
		}
		
		return itens.get(item);
		
	}
	
	public int getQuantidade(){
		Integer contador = 0; 
		for(Map.Entry<CarrinhoItem, Integer> entry : itens.entrySet()){
			Integer value = entry.getValue();
			contador += value;
		}
		return contador;
		
//		Expressão modificada para Java 7 devido eu não ter conseguido fazer o projeto rodar em Java 8 com tomcat 7 no LInux
//		return itens.values().stream().reduce(0, 
//				(proximo,acumulador) -> proximo + acumulador);	
	}
	
	public Collection<CarrinhoItem> getItem(){
		return itens.keySet();
	}
	
	public BigDecimal getTotal(CarrinhoItem item){
		return item.getTotal(getQuantidade(item))
	}
}
