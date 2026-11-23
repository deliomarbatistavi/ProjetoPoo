import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cardapio {

        private List<Produto> produtos;

        private List<Adicional> adicionais;

        public Cardapio() {
            this.produtos = new ArrayList<>();
            this.adicionais = new ArrayList<>();
        }
        public void cadastrarProduto(Produto produto) {
            if (produto != null) {
                produtos.add(produto);
                System.out.println("Produto cadastrado com sucesso!");
            }
        }

        public List<Produto> listarProdutos() {
            if (produtos.isEmpty()) {
                System.out.println("Cardápio vazio!");
            } else {
                System.out.println("\n========== CARDÁPIO ==========");
                for (Produto p : produtos) {
                    System.out.println(p);
                }
            }
            return produtos;
        }

        public Produto buscarPorId(int id) {
            for (Produto p : produtos) {
                if (p.getId() == id) {
                    return p;
                }
            }
            return null;
        }

        public boolean editarProduto(int id, String novoNome, String novaCategoria, double novoPreco, String novaDescricao, boolean disponivel) {
            Produto p = buscarPorId(id);
            if (p != null) {
                p.setNome(novoNome);
                p.setCategoria(novaCategoria);
                p.setPreco(novoPreco);
                p.setDescricao(novaDescricao);
                p.setDisponivel(disponivel);
                return true;
            }
            return false;
        }

    public boolean excluirProduto(int id) {
        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

        public void cadastrarAdicional(Adicional adicional) {
            if (adicional != null) {
                adicionais.add(adicional);
                System.out.println("Adicional cadastrado com sucesso!");
            }
        }

        public List<Adicional> listarAdicionais() {
            if (adicionais.isEmpty()) {
                System.out.println("Nenhum adicional cadastrado!");
            } else {
                System.out.println("-------- ADICIONAIS --------");
                for (Adicional a : adicionais) {
                    System.out.println(a);
                }
            }
            return adicionais;
        }

    public boolean excluirAdicional(String nome) {
        Iterator<Adicional> iterator = adicionais.iterator();
        while (iterator.hasNext()) {
            Adicional adicional = iterator.next();
            if (adicional.getNome().equalsIgnoreCase(nome)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    }























    }















