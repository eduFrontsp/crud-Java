package com.test.primeiroexemplo.repository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.test.primeiroexemplo.model.Produto;
import com.test.primeiroexemplo.model.exception.ResourceNotFoundException;

@Repository
public class ProdutoRepository {

    private List<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    public List<Produto> obterTodos() {
        return produtos;
    }

    public Optional<Produto> obterPorId(Integer id) {
        return produtos
                .stream()
                .filter(produto -> produto.getId() == id)
                .findFirst();

    }

    public Produto adicionar(Produto produto) {

        ultimoId++;
        produto.setId(ultimoId);
        produtos.add(produto);

        return produto;
    }

    public void deleter(Integer id) {

        produtos.removeIf(produto -> produto.getId() == id);
    }

    public Produto atualizar(Produto produto) {

        // encontrar produto na lista
        Optional<Produto> produtoEncontrado = obterPorId(produto.getId());
        if (produtoEncontrado.isEmpty()) {

            throw new ResourceNotFoundException("produto não encontrado ou nao existe");
        }

        // remover produto antigo da lista

        deleter(produto.getId());

        // e adicionar novo produto
        produtos.add(produto);

        return produto;
    }
}
