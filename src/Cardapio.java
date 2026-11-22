import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cardapio {

        private List<Produto> produtos;

        public Cardapio() {
            produtos = new ArrayList<>();
        }

    public void cadastrarProduto(Produto produto) {

        if (produto == null) {
            System.out.println("Produto inválido!");
            return;
        }

        produtos.add(produto);

        System.out.println("Produto cadastrado com sucesso!");
    }

    public void listarProdutos() {

        if (produtos.isEmpty()) {
            System.out.println("Cardápio vazio!");
            return;
        }

        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    public boolean excluirProduto(int id) {

        Iterator<Produto> Iterator = produtos.iterator();

        while (Iterator.hasNext()) {

            Produto produto = Iterator.next();

            if (produto.getId() == id) {
                Iterator.remove();
                return true;
            }
        }

        return false;
    }
}























    }















