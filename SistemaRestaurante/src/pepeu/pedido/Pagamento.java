package Pepeu.pedido;

public class Pagamento {

       private String tipoPagamento;
       private double valorRecebido;
       private double troco;

       public Pagamento(String tipoPagamento, double valorRecebido) {
           this.tipoPagamento = tipoPagamento;
           this.valorRecebido = valorRecebido;
       }

       public double calcularTroco(double valorTotal) {
           troco = valorRecebido - valorTotal;
           return troco;
       }

       public String getTipoPagamento() {
           return tipoPagamento;
       }

       public double getValorRecebido() {
           return valorRecebido;
       }

       public double getTroco() {
           return troco;
       }
   }
