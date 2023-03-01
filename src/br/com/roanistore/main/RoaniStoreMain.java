package br.com.roanistore.main;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.dao.CategoriaDAO;
import br.com.roanistore.dao.ProdutoDAO;
import br.com.roanistore.factory.ConnectionFactory;
import br.com.roanistore.modelo.Categoria;
import br.com.roanistore.modelo.Produto;

public class RoaniStoreMain
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RoaniStoreMain.class);
	
	public static void main(String[] args)
	{
		ConnectionFactory factory = new ConnectionFactory();
		ProdutoDAO produtoDAO = new ProdutoDAO(factory.getConnection());
		CategoriaDAO categoriaDAO = new CategoriaDAO(factory.getConnection());

		Categoria categoria = new Categoria("Eletrônicos");
		Produto produto = new Produto("Notebook Samsung", "Notebook S224 Samsung 4GB RAM, Processador Inter Celeron A4", categoria);
		
		try
		{
			LOGGER.info("-------------- Teste: salvar categoria ----------------");
			categoriaDAO.salvar(categoria);
			
			LOGGER.info("-------------- Teste: salvar produto ----------------");
			produtoDAO.salvar(produto);
			
			LOGGER.info("-------------- Teste: listar categorias ----------------");
			List<Categoria> categorias = categoriaDAO.listar();
			categorias.forEach(c -> LOGGER.info(c.getNome()));
			
			LOGGER.info("-------------- Teste: listar produto ----------------");
			List<Produto> produtosLista = produtoDAO.listar();
			produtosLista.forEach(p -> LOGGER.info(p.getNome()));
			
			LOGGER.info("-------------- Teste: buscar produto por categoria ----------------");
			List<Produto> produtosBusca = produtoDAO.buscar(categoria);
			produtosBusca.forEach(p -> LOGGER.info(p.getNome()));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
