package br.com.roanistore.modelo;

import java.util.HashSet;
import java.util.Set;

public class Categoria
{
	private Integer id;
	private String nome;
	private Set<Produto> produtos = new HashSet<>();
	
	public Categoria(Integer id, String nome)
	{
		this.id = id;
		this.nome = nome;
	}
	
	public Categoria(String nome)
	{
		this.nome = nome;
	}
	
	public void adicionar(Produto produto)
	{
		produtos.add(produto);
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Set<Produto> getProdutos()
	{
		return produtos;
	}

	public void setProdutos(Set<Produto> produtos)
	{
		this.produtos = produtos;
	}
}
