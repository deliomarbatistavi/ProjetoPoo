package Pepeu.pedido;

import java.util.ArrayList;

public class Pedido {

    private ArrayList<ItemPedido> itens = new ArrayList<>();
    private String tipoPedido;
    private double taxaEntrega;

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void removerItem(int indice) {
        itens.remove(indice);
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

}