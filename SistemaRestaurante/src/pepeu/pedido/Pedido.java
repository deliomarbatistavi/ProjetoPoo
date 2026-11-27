package Pepeu.pedido;

import java.util.ArrayList;

public class Pedido {
    private ArrayList<ItemPedido> itens = new ArrayList<>();
    private String cliente = "Consumidor";
    private String tipoPedido;
    private int numeroMesa;
    private double taxaEntrega;
    private String enderecoEntrega;
    private Pagamento pagamento;
    private boolean finalizado;

    public void adicionarItem(ItemPedido item) {
        verificarAberto();
        if (item == null) throw new IllegalArgumentException("Informe um item.");
        itens.add(copiarItem(item));
    }

    public void removerItem(int indice) {
        verificarAberto();
        itens.remove(indice);
    }

    public ArrayList<ItemPedido> getItens() {
        ArrayList<ItemPedido> copia = new ArrayList<>();
        for (ItemPedido item : itens) copia.add(copiarItem(item));
        return copia;
    }

    public int calcularQuantidadeItens() {
        return itens.stream()
                .mapToInt(ItemPedido::getQuantidade)
                .sum();
    }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) {
        verificarAberto();
        this.cliente = cliente == null || cliente.trim().isEmpty() ? "Consumidor" : cliente.trim();
    }

    public String getTipoPedido() { return tipoPedido; }
    public void setTipoPedido(String tipoPedido) {
        verificarAberto();
        if (!"Balcão".equals(tipoPedido) && !"Mesa".equals(tipoPedido) && !"Delivery".equals(tipoPedido))
            throw new IllegalArgumentException("Tipo de pedido inválido.");
        this.tipoPedido = tipoPedido;
        numeroMesa = 0;
        taxaEntrega = 0;
        enderecoEntrega = null;
    }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) {
        verificarAberto();
        if (!"Mesa".equals(tipoPedido) || numeroMesa <= 0)
            throw new IllegalArgumentException("Informe um número de mesa válido.");
        this.numeroMesa = numeroMesa;
    }

    public double getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(double taxaEntrega) {
        verificarAberto();
        if (!"Delivery".equals(tipoPedido) || !Double.isFinite(taxaEntrega) || taxaEntrega < 0)
            throw new IllegalArgumentException("Informe uma taxa de entrega válida.");
        this.taxaEntrega = taxaEntrega;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        verificarAberto();
        if (!"Delivery".equals(tipoPedido) || enderecoEntrega == null || enderecoEntrega.trim().isEmpty())
            throw new IllegalArgumentException("Informe um endereço de entrega válido.");
        this.enderecoEntrega = enderecoEntrega.trim();
    }

    public double getTotal() {
        return calcularTotal();
    }

    public double calcularSubtotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }

    public double calcularTotal() {
        return calcularSubtotal() + taxaEntrega;
    }

    public Pagamento getPagamento() {
        if (pagamento == null) return null;
        Pagamento copia = new Pagamento(pagamento.getTipoPagamento(), pagamento.getValorRecebido());
        if ("Dinheiro".equals(pagamento.getTipoPagamento())) copia.calcularTroco(calcularTotal());
        return copia;
    }
    public String getFormaPagamento() { return pagamento == null ? null : pagamento.getTipoPagamento(); }
    public double getValorRecebido() { return pagamento == null ? 0 : pagamento.getValorRecebido(); }
    public boolean isFinalizado() { return finalizado; }
    public double getTroco() {
        return pagamento != null && "Dinheiro".equals(pagamento.getTipoPagamento()) ? pagamento.getTroco() : 0;
    }

    public void finalizar(String formaPagamento, double valorRecebido) {
        verificarAberto();
        if (tipoPedido == null || ("Mesa".equals(tipoPedido) && numeroMesa == 0))
            throw new IllegalStateException("Informe o tipo do pedido e o número da mesa, se necessário.");
        if ("Delivery".equals(tipoPedido) && enderecoEntrega == null)
            throw new IllegalStateException("Informe o endereço de entrega.");
        if (itens.isEmpty()) throw new IllegalStateException("Adicione pelo menos um item.");
        if (!"Dinheiro".equals(formaPagamento) && !"Pix".equals(formaPagamento) && !"Cartão".equals(formaPagamento))
            throw new IllegalArgumentException("Forma de pagamento inválida.");
        if ("Dinheiro".equals(formaPagamento) && (!Double.isFinite(valorRecebido) || valorRecebido < getTotal()))
            throw new IllegalArgumentException("O valor recebido deve cobrir o total.");
        pagamento = new Pagamento(formaPagamento, "Dinheiro".equals(formaPagamento) ? valorRecebido : 0);
        if ("Dinheiro".equals(formaPagamento)) pagamento.calcularTroco(calcularTotal());
        finalizado = true;
    }

    private void verificarAberto() {
        if (finalizado) throw new IllegalStateException("O pedido já foi finalizado.");
    }

    private ItemPedido copiarItem(ItemPedido item) {
        return new ItemPedido(item.getNome(), item.getPreco(), item.getQuantidade(), item.getObservacao());
    }
}
