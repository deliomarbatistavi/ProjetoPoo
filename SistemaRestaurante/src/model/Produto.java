package model;

public class Produto {

    private String nome;
    private String categoria;
    private double preco;
    private String descricao;
    private boolean disponivel;

    public Produto(String nome, String categoria, double preco,
                   String descricao, boolean disponivel) {

        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
        this.disponivel = disponivel;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}