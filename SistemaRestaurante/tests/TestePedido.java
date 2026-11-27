import Pepeu.pedido.ItemPedido;
import Pepeu.pedido.Pedido;

public class TestePedido {
    private static void conferir(boolean condicao) {
        if (!condicao) throw new AssertionError("Teste de pedido falhou.");
    }

    private static void deveFalhar(Runnable acao) {
        try {
            acao.run();
        } catch (IllegalArgumentException | IllegalStateException esperado) {
            return;
        }
        throw new AssertionError("Era esperada uma validação.");
    }

    public static void main(String[] args) {
        deveFalhar(() -> new ItemPedido("", 10, 1, ""));
        deveFalhar(() -> new ItemPedido("Pizza", -1, 1, ""));
        deveFalhar(() -> new ItemPedido("Pizza", 10, 0, ""));

        Pedido pedido = new Pedido();
        ItemPedido item = new ItemPedido("Pizza", 20, 2, "Sem cebola");
        pedido.adicionarItem(item);
        item.setQuantidade(10);
        pedido.getItens().get(0).setPreco(1);
        conferir(pedido.calcularQuantidadeItens() == 2 && pedido.calcularSubtotal() == 40);
        pedido.setTipoPedido("Delivery");
        pedido.setTaxaEntrega(5);
        deveFalhar(() -> pedido.finalizar("Dinheiro", 50));
        pedido.setEnderecoEntrega(" Rua A, 10 ");
        deveFalhar(() -> pedido.finalizar("Dinheiro", 44));
        pedido.finalizar("Dinheiro", 50);
        conferir(pedido.isFinalizado() && pedido.getTotal() == 45 && pedido.getTroco() == 5);
        conferir("Rua A, 10".equals(pedido.getEnderecoEntrega()));
        pedido.getPagamento().calcularTroco(1);
        conferir(pedido.getTroco() == 5);
        deveFalhar(() -> pedido.removerItem(0));

        Pedido mesa = new Pedido();
        mesa.setTipoPedido("Mesa");
        mesa.adicionarItem(new ItemPedido("Suco", 8, 1, ""));
        deveFalhar(() -> mesa.finalizar("Pix", 0));
        mesa.setNumeroMesa(3);
        mesa.finalizar("Pix", 0);
        conferir(mesa.isFinalizado() && mesa.getTroco() == 0);
        System.out.println("Testes de pedido passaram.");
    }
}
