import Pepeu.pedido.ItemPedido;
import Pepeu.pedido.Pedido;
import Pepeu.pedido.TelaPedido;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class TesteTelaPedido {
    private static Object campo(TelaPedido tela, String nome) throws Exception {
        Field campo = TelaPedido.class.getDeclaredField(nome);
        campo.setAccessible(true);
        return campo.get(tela);
    }

    private static void executar(TelaPedido tela, String nome) throws Exception {
        Method metodo = TelaPedido.class.getDeclaredMethod(nome);
        metodo.setAccessible(true);
        metodo.invoke(tela);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TelaPedido tela = null;
            try {
                List<Pedido> pedidos = new ArrayList<>();
                tela = new TelaPedido(Arrays.asList("Ana"),
                        Arrays.asList(new ItemPedido("Suco", 8, 1, "")), pedidos::add);
                ((JComboBox<?>) campo(tela, "cliente")).setSelectedItem("Ana");
                ((JComboBox<?>) campo(tela, "produto")).setSelectedIndex(1);
                ((JSpinner) campo(tela, "quantidade")).setValue(2);
                ((JTextField) campo(tela, "observacao")).setText("Sem gelo");
                executar(tela, "adicionarItem");
                ((JComboBox<?>) campo(tela, "tipo")).setSelectedItem("Delivery");
                ((JTextField) campo(tela, "taxa")).setText("4");
                if (!((JLabel) campo(tela, "total")).getText().contains("20,00"))
                    throw new AssertionError("Total não foi atualizado.");
                ((JComboBox<?>) campo(tela, "forma")).setSelectedItem("Pix");
                Timer fecharDialogo = new Timer(300, e -> {
                    for (java.awt.Window janela : java.awt.Window.getWindows())
                        if (janela instanceof JDialog) janela.dispose();
                });
                fecharDialogo.setRepeats(false);
                fecharDialogo.start();
                executar(tela, "finalizarPedido");
                if (pedidos.size() != 1 || pedidos.get(0).getTotal() != 20 ||
                        !"Ana".equals(pedidos.get(0).getCliente()) ||
                        !"Sem gelo".equals(pedidos.get(0).getItens().get(0).getObservacao()))
                    throw new AssertionError("Pedido não foi entregue corretamente.");
                System.out.println("Teste da tela passou.");
            } catch (Exception e) { throw new RuntimeException(e); }
            finally { if (tela != null) tela.dispose(); }
        });
    }
}
