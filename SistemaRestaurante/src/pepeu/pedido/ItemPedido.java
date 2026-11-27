package Pepeu.pedido;

public class ItemPedido {

    private String nome;
    private double preco;
    private int quantidade;
    private String observacao;

    public ItemPedido(String nome, double preco, int quantidade, String observacao) {
        setNome(nome);
        setPreco(preco);
        setQuantidade(quantidade);
        setObservacao(observacao);
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
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Informe o nome do item.");
        this.nome = nome.trim();
    }

    public void setPreco(double preco) {
        if (!Double.isFinite(preco) || preco < 0)
            throw new IllegalArgumentException("Informe um preço válido.");
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0)
            throw new IllegalArgumentException("Informe uma quantidade válida.");
        this.quantidade = quantidade;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public double getSubtotal() {
        return preco * quantidade;
    }

}
