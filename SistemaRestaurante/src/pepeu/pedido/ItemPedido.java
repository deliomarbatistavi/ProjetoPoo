package Pepeu.pedido;

public class ItemPedido {

    private String nome;
    private double preco;
    private int quantidade;
    private String observacao;

    public ItemPedido(String nome, double preco, int quantidade, String observacao) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }
    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getObservacao() {
        return observacao;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public double getSubtotal() {
        return preco * quantidade;
    }

}