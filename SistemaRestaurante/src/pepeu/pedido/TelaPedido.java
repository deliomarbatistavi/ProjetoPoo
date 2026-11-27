package Pepeu.pedido;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/** Tela independente; as listas e o destino dos pedidos podem vir do restante do sistema. */
public class TelaPedido extends JFrame {
    private final JComboBox<String> cliente = new JComboBox<>();
    private final JComboBox<String> tipo = new JComboBox<>(new String[]{"Balcão", "Mesa", "Delivery"});
    private final JTextField mesa = new JTextField(8);
    private final JTextField taxa = new JTextField("0", 8);
    private final JTextField endereco = new JTextField(20);
    private final JComboBox<ItemPedido> produto = new JComboBox<>();
    private final JTextField nome = new JTextField(12);
    private final JTextField preco = new JTextField(7);
    private final JSpinner quantidade = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
    private final JTextField observacao = new JTextField(12);
    private final DefaultTableModel tabelaModelo = new DefaultTableModel(
            new String[]{"Item", "Preço", "Quantidade", "Observação", "Subtotal"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    private final JTable tabela = new JTable(tabelaModelo);
    private final JComboBox<String> forma = new JComboBox<>(new String[]{"Dinheiro", "Pix", "Cartão"});
    private final JTextField recebido = new JTextField(8);
    private final JLabel total = new JLabel();
    private final JLabel troco = new JLabel();
    private final NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final Consumer<Pedido> aoFinalizar;
    private Pedido pedido = new Pedido();

    public TelaPedido(List<String> clientes, List<ItemPedido> cardapio, Consumer<Pedido> aoFinalizar) {
        super("Pedido");
        this.aoFinalizar = aoFinalizar;
        cliente.addItem("Consumidor");
        for (String nomeCliente : clientes) if (!"Consumidor".equals(nomeCliente)) cliente.addItem(nomeCliente);
        produto.addItem(null);
        for (ItemPedido item : cardapio) produto.addItem(item);
        produto.setRenderer(new DefaultListCellRenderer() {
            @Override public java.awt.Component getListCellRendererComponent(JList<?> lista, Object valor,
                    int indice, boolean selecionado, boolean foco) {
                ItemPedido item = (ItemPedido) valor;
                return super.getListCellRendererComponent(lista, item == null ? "Selecionar do cardápio" :
                        item.getNome() + " - " + moeda.format(item.getPreco()), indice, selecionado, foco);
            }
        });
        produto.addActionListener(e -> {
            ItemPedido item = (ItemPedido) produto.getSelectedItem();
            if (item != null) { nome.setText(item.getNome()); preco.setText(String.valueOf(item.getPreco())); }
        });

        JPanel dados = new JPanel(new GridLayout(0, 2, 6, 6));
        adicionarCampo(dados, "Cliente", cliente);
        adicionarCampo(dados, "Tipo", tipo);
        adicionarCampo(dados, "Número da mesa", mesa);
        adicionarCampo(dados, "Taxa de entrega", taxa);
        adicionarCampo(dados, "Endereço de entrega (opcional)", endereco);
        JPanel itemPainel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemPainel.setBorder(BorderFactory.createTitledBorder("Adicionar item"));
        itemPainel.add(produto); itemPainel.add(new JLabel("Nome")); itemPainel.add(nome);
        itemPainel.add(new JLabel("Preço")); itemPainel.add(preco);
        itemPainel.add(new JLabel("Quantidade")); itemPainel.add(quantidade);
        itemPainel.add(new JLabel("Observação")); itemPainel.add(observacao);
        JButton adicionar = new JButton("Adicionar");
        itemPainel.add(adicionar);
        JPanel topo = new JPanel(new BorderLayout());
        topo.add(dados, BorderLayout.NORTH); topo.add(itemPainel, BorderLayout.SOUTH);

        JButton remover = new JButton("Remover item selecionado");
        JPanel pagamentos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pagamentos.add(remover); pagamentos.add(total); pagamentos.add(new JLabel("Pagamento"));
        pagamentos.add(forma); pagamentos.add(new JLabel("Valor recebido")); pagamentos.add(recebido);
        pagamentos.add(troco);
        JButton finalizar = new JButton("Finalizar pedido"); pagamentos.add(finalizar);
        add(topo, BorderLayout.NORTH); add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(pagamentos, BorderLayout.SOUTH);
        tipo.addActionListener(e -> atualizarCampos());
        forma.addActionListener(e -> atualizarCampos());
        adicionar.addActionListener(e -> adicionarItem());
        remover.addActionListener(e -> removerItem());
        finalizar.addActionListener(e -> finalizarPedido());
        DocumentListener atualizar = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { atualizarValores(); }
            public void removeUpdate(DocumentEvent e) { atualizarValores(); }
            public void changedUpdate(DocumentEvent e) { atualizarValores(); }
        };
        taxa.getDocument().addDocumentListener(atualizar);
        recebido.getDocument().addDocumentListener(atualizar);
        atualizarCampos();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 550);
        setLocationRelativeTo(null);
    }

    private static void adicionarCampo(JPanel painel, String rotulo, JComponent campo) {
        painel.add(new JLabel(rotulo)); painel.add(campo);
    }

    private double numero(String texto) {
        try {
            double valor = Double.parseDouble(texto.trim().replace(',', '.'));
            if (!Double.isFinite(valor) || valor < 0) throw new NumberFormatException();
            return valor;
        } catch (NumberFormatException e) { throw new IllegalArgumentException("Informe um valor numérico válido."); }
    }

    private void atualizarCampos() {
        boolean delivery = "Delivery".equals(tipo.getSelectedItem());
        mesa.setEnabled("Mesa".equals(tipo.getSelectedItem()));
        taxa.setEnabled(delivery); endereco.setEnabled(delivery);
        recebido.setEnabled("Dinheiro".equals(forma.getSelectedItem()));
        atualizarValores();
    }

    private void atualizarValores() {
        double valor = pedido.calcularSubtotal();
        try {
            if ("Delivery".equals(tipo.getSelectedItem())) valor += numero(taxa.getText());
            total.setText("Total: " + moeda.format(valor));
            if (recebido.isEnabled() && !recebido.getText().trim().isEmpty()) {
                double diferenca = numero(recebido.getText()) - valor;
                troco.setText(diferenca < 0 ? "Valor insuficiente" : "Troco: " + moeda.format(diferenca));
            } else troco.setText("");
        } catch (IllegalArgumentException e) { total.setText("Verifique os valores"); troco.setText(""); }
    }

    private void adicionarItem() {
        try {
            pedido.adicionarItem(new ItemPedido(nome.getText(), numero(preco.getText()),
                    (Integer) quantidade.getValue(), observacao.getText()));
            nome.setText(""); preco.setText(""); observacao.setText(""); quantidade.setValue(1);
            produto.setSelectedIndex(0);
            atualizarTabela();
        } catch (IllegalArgumentException e) { erro(e); }
    }

    private void removerItem() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { erro(new IllegalArgumentException("Selecione um item para remover.")); return; }
        pedido.removerItem(tabela.convertRowIndexToModel(linha));
        atualizarTabela();
    }

    private void atualizarTabela() {
        tabelaModelo.setRowCount(0);
        for (ItemPedido item : pedido.getItens()) tabelaModelo.addRow(new Object[]{item.getNome(),
                moeda.format(item.getPreco()), item.getQuantidade(), item.getObservacao(), moeda.format(item.getSubtotal())});
        atualizarValores();
    }

    private void finalizarPedido() {
        try {
            pedido.setCliente((String) cliente.getSelectedItem());
            pedido.setTipoPedido((String) tipo.getSelectedItem());
            if ("Mesa".equals(tipo.getSelectedItem())) {
                try { pedido.setNumeroMesa(Integer.parseInt(mesa.getText().trim())); }
                catch (NumberFormatException e) { throw new IllegalArgumentException("Informe o número da mesa."); }
            }
            if ("Delivery".equals(tipo.getSelectedItem())) {
                pedido.setTaxaEntrega(numero(taxa.getText()));
                if (!endereco.getText().trim().isEmpty()) pedido.setEnderecoEntrega(endereco.getText());
            }
            String pagamento = (String) forma.getSelectedItem();
            pedido.finalizar(pagamento, "Dinheiro".equals(pagamento) ? numero(recebido.getText()) : 0);
        } catch (IllegalArgumentException | IllegalStateException e) { erro(e); return; }
        Pedido concluido = pedido;
        pedido = new Pedido();
        if (aoFinalizar != null) aoFinalizar.accept(concluido);
        JOptionPane.showMessageDialog(this, "Pedido finalizado! Total: " + moeda.format(concluido.getTotal()) +
                ("Dinheiro".equals(concluido.getFormaPagamento()) ? " | Troco: " + moeda.format(concluido.getTroco()) : ""));
        cliente.setSelectedIndex(0); tipo.setSelectedIndex(0); forma.setSelectedIndex(0);
        mesa.setText(""); taxa.setText("0"); endereco.setText(""); recebido.setText("");
        atualizarTabela();
    }

    private void erro(Exception e) { JOptionPane.showMessageDialog(this, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Pedido> pedidos = new ArrayList<>();
            new TelaPedido(new ArrayList<>(), new ArrayList<>(), pedidos::add).setVisible(true);
        });
    }
}
