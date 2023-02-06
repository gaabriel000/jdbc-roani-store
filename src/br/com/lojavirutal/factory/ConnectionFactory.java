package br.com.lojavirutal.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory
{
	public final DataSource dataSource;
	
	public ConnectionFactory()
	{
		ComboPooledDataSource combo = new ComboPooledDataSource();
		combo.setJdbcUrl("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC");
		combo.setUser("root");
		combo.setPassword("root");
		
		combo.setMaxPoolSize(15);
		
		this.dataSource = combo;
	}
	
	public Connection getConnection()
	{
		try
		{
			return this.dataSource.getConnection();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Erro ao se conectar com o banco de dados");
		}
	}
}
