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

public class CategoriaDAO
{
	private Connection connection;
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaDAO.class);
	
	public CategoriaDAO(Connection connection)
	{
		this.connection = connection;
	}
	
	public void salvar(Categoria categoria) throws SQLException
	{
		LOGGER.info("Iniciando o método salvar da categoria {}", categoria.getNome());
		String sql = "INSERT INTO categoria (nome) VALUES (?)";

		try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			stm.setString(1, categoria.getNome());
			stm.execute();

			try (ResultSet result = stm.getGeneratedKeys())
			{
				categoria.setId(result.getInt(1));
				LOGGER.info("Categoria salva com sucesso!");
			}
		}
	}
	
	public List<Categoria> listar() throws SQLException
	{
		LOGGER.info("Iniciando listagem de categorias");
		Categoria ultima = null;
		List<Categoria> categorias = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT c.id, c.nome, p.id, p.nome, p.descricao \n ");
		sb.append("FROM categoria c \n ");
		sb.append("INNER JOIN produto p ON c.id = p.categoria_id \n ");
		String sql = sb.toString();
		
		try(PreparedStatement stm = connection.prepareStatement(sql))
		{
			stm.execute();
			
			try(ResultSet result = stm.getResultSet())
			{
				while(result.next())
				{
					if (ultima == null || !ultima.getNome().equals(result.getString(2)))
					{
						Categoria categoria = new Categoria(result.getInt(1), result.getString(2));
						ultima = categoria;
						categorias.add(categoria);
						Produto produto = new Produto(result.getInt(3), result.getString(4), result.getString(5), ultima);
						ultima.adicionar(produto);
					}
				}
			}
		}
		
		return categorias;
	}
}
