# Tela de pedido

A tela usa Swing, que já vem com o Java. Para executar a versão independente no IntelliJ, rode o método `main` de `Pepeu.pedido.TelaPedido`. Nessa versão, use os campos de nome e preço para informar os itens; os pedidos ficam apenas em memória enquanto o programa estiver aberto.

Para integrar com o restante do sistema, crie `TelaPedido` passando uma lista com os nomes dos clientes, uma lista de `Pepeu.pedido.ItemPedido` para o cardápio (nome e preço são usados pela seleção) e uma função que receba o pedido finalizado. Por exemplo:

```java
new TelaPedido(nomesClientes, itensCardapio, pedidos::add).setVisible(true);
```

O cliente “Consumidor” é incluído automaticamente. A função de destino deve guardar o pedido na lista compartilhada do sistema; a tela independente não grava pedidos em arquivo ou banco de dados.
