package br.com.casadocodigo.loja.testes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;

public class TesteProdutoDao {

		public static void main(String[] args) {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto find = produtoDAO.find(12);
			
		}
	
}
