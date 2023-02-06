package br.com.lojavirutal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lojavirutal.modelo.Categoria;
import br.com.lojavirutal.modelo.Produto;

public class ProdutoDAO
{
	private Connection connection;
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaDAO.class);

	public ProdutoDAO(Connection connection)
	{
		this.connection = connection;
	}

	public void salvar(Produto produto) throws SQLException
	{
		LOGGER.info("Iniciando o método salvar do produto {}", produto.getNome());
		String sql = "INSERT INTO produto (nome, descricao) VALUES (?, ?)";

		try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			stm.setString(1, produto.getNome());
			stm.setString(2, produto.getDescricao());

			stm.execute();

			try (ResultSet result = stm.getGeneratedKeys())
			{
				produto.setId(result.getInt(1));
				LOGGER.info("Produto salvo com sucesso!");
			}
		}
	}
	
	public List<Produto> listar() throws SQLException
	{
		LOGGER.info("Iniciando listagem de produtos");
		List<Produto> produtos = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p.id, p.nome, p.descricao, c.id, c.nome \n ");
		sb.append("FROM produto p \n ");
		sb.append("INNER JOIN categoria c ON p.categoria_id = c.id \n ");
		String sql = sb.toString();
		
		try (PreparedStatement stm = connection.prepareStatement(sql))
		{
			stm.execute();
			try (ResultSet result = stm.getResultSet())
			{
				while (result.next())
				{
					produtos.add(new Produto(
									result.getInt(1), 
									result.getString(2), 
									result.getString(3), 
									new Categoria(
											result.getInt(4), 
											result.getString(5))));
				}
			}
		}
		
		return produtos;
	}

	public List<Produto> buscar(Categoria categoria) throws SQLException
	{
		LOGGER.info("Buscando produtos pela categoria {}", categoria.getNome());
		List<Produto> produtos = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p.id, p.nome, p.descricao, c.id, c.nome \n ");
		sb.append("FROM produto p \n ");
		sb.append("INNER JOIN categoria c ON p.categoria_id = c.id \n ");
		sb.append("WHERE categoria_id = ? \n ");
		String sql = sb.toString();
		
		try (PreparedStatement stm = connection.prepareStatement(sql))
		{
			stm.setInt(1, categoria.getId());
			stm.execute();
			try (ResultSet result = stm.getResultSet())
			{
				while (result.next())
				{
					produtos.add(new Produto(
							result.getInt(1), 
							result.getString(2), 
							result.getString(3), 
							new Categoria(
									result.getInt(4), 
									result.getString(5))));
				}
			}
		}
		
		return produtos;
	}
}
