package dados;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda todos os dados do sistema enquanto o programa está aberto:
 * clientes, itens do cardápio e pedidos.
 *
 * Tudo é estático, então qualquer tela acessa as mesmas listas, por exemplo:
 *   Dados.getClientes()
 *   Dados.adicionarPedido(pedido)
 *
 * O cardápio e os clientes de exemplo são cadastrados automaticamente
 * na primeira vez que a classe é usada.
 */
public class Dados {

    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Produto> produtos = new ArrayList<>();
    private static final List<Pedido> pedidos = new ArrayList<>();

    private static int proximoNumeroPedido = 1;

    // Cliente usado nos pedidos de balcão, sem cadastro
    public static final Cliente CONSUMIDOR =
            new Cliente("Consumidor", "", "", "", "");

    static {
        carregarCardapio();
        carregarClientes();
    }

    // Não é para criar objetos desta classe
    private Dados() {
    }

    // ===================== Dados de exemplo =====================

    private static void carregarCardapio() {
        produtos.add(new Produto("X-Burger", "Lanches", 18.00,
                "Pão, hambúrguer, queijo e molho da casa", true));
        produtos.add(new Produto("X-Salada", "Lanches", 20.00,
                "Pão, hambúrguer, queijo, alface, tomate e maionese", true));
        produtos.add(new Produto("X-Bacon", "Lanches", 24.00,
                "Pão, hambúrguer, queijo, bacon e molho da casa", true));
        produtos.add(new Produto("Batata frita", "Porções", 15.00,
                "Porção de batata frita crocante (300 g)", true));
        produtos.add(new Produto("Refrigerante", "Bebidas", 6.00,
                "Lata 350 ml", true));
        produtos.add(new Produto("Suco", "Bebidas", 8.00,
                "Suco natural de laranja (400 ml)", true));
    }

    private static void carregarClientes() {
        clientes.add(new Cliente("Ana Souza", "123.456.789-09", "(11) 98765-4321",
                "ana.souza@email.com", "Rua das Flores, 120 - Centro"));
        clientes.add(new Cliente("Bruno Lima", "987.654.321-00", "(11) 97654-3210",
                "bruno.lima@email.com", "Av. Brasil, 455 - Jardim América"));
        clientes.add(new Cliente("Carla Mendes", "111.444.777-35", "(11) 96543-2109",
                "carla.mendes@email.com", "Rua São João, 78 - Vila Nova"));
        clientes.add(new Cliente("Diego Santos", "246.813.579-28", "(11) 95432-1098",
                "diego.santos@email.com", "Rua Tiradentes, 300 - Bela Vista"));
        clientes.add(new Cliente("Eduarda Rocha", "135.792.468-28", "(11) 94321-0987",
                "eduarda.rocha@email.com", "Av. Paulista, 1500 - Bela Vista"));
    }

    // ===================== Clientes =====================

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public static void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    /** Procura um cliente pelo CPF. Retorna null se não encontrar. */
    public static Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    /** Lista para a tela de Pedido: "Consumidor" primeiro, depois os clientes cadastrados. */
    public static List<Cliente> getClientesParaPedido() {
        List<Cliente> lista = new ArrayList<>();
        lista.add(CONSUMIDOR);
        lista.addAll(clientes);
        return lista;
    }

    // ===================== Cardápio =====================

    public static List<Produto> getProdutos() {
        return produtos;
    }

    public static void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public static void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    /** Só os itens marcados como disponíveis (para a tela de Pedido). */
    public static List<Produto> getProdutosDisponiveis() {
        List<Produto> lista = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.isDisponivel()) {
                lista.add(p);
            }
        }
        return lista;
    }

    /** Itens de uma categoria, ex.: "Lanches". */
    public static List<Produto> getProdutosPorCategoria(String categoria) {
        List<Produto> lista = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                lista.add(p);
            }
        }
        return lista;
    }

    // ===================== Pedidos =====================

    public static List<Pedido> getPedidos() {
        return pedidos;
    }

    /** Número que o próximo pedido deve usar (1, 2, 3...). */
    public static int gerarNumeroPedido() {
        return proximoNumeroPedido++;
    }

    public static void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public static void removerPedido(Pedido pedido) {
        pedidos.remove(pedido);
    }
}
