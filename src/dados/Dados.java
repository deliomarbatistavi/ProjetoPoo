package dados;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;

import java.util.ArrayList;
import java.util.List;

public class Dados {

    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Produto> produtos = new ArrayList<>();
    private static final List<Pedido> pedidos = new ArrayList<>();

    private static int proximoNumeroPedido = 1;

    public static final Cliente CONSUMIDOR =
            new Cliente("Consumidor", "", "", "", "");

    static {
        carregarCardapio();
        carregarClientes();
    }

    private Dados() {
    }

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

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public static void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public static Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    public static List<Cliente> getClientesParaPedido() {
        List<Cliente> lista = new ArrayList<>();
        lista.add(CONSUMIDOR);
        lista.addAll(clientes);
        return lista;
    }

    public static List<Produto> getProdutos() {
        return produtos;
    }

    public static void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public static void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    public static List<Produto> getProdutosDisponiveis() {
        List<Produto> lista = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.isDisponivel()) {
                lista.add(p);
            }
        }
        return lista;
    }

    public static List<Produto> getProdutosPorCategoria(String categoria) {
        List<Produto> lista = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                lista.add(p);
            }
        }
        return lista;
    }

    public static List<Pedido> getPedidos() {
        return pedidos;
    }

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
