# Desafio BTG Pactual — Order Management

Aplicação de gerenciamento de pedidos construída como desafio técnico. A proposta é receber pedidos por mensageria, armazená-los e disponibilizar consultas por uma API REST.

> **Status:** projeto em desenvolvimento. A estrutura Spring Boot, a configuração do MongoDB e os modelos de pedido estão criados. O consumidor RabbitMQ e os endpoints REST ainda não foram implementados.

## Escopo do desafio

Ao concluir, a aplicação deverá:

- consumir pedidos de uma fila RabbitMQ;
- persistir pedidos e itens no MongoDB;
- permitir consultar o valor total de um pedido;
- consultar a quantidade de pedidos por cliente;
- listar os pedidos de um cliente.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data MongoDB
- Spring AMQP (RabbitMQ)
- Maven
- Docker Compose

## Requisitos

- JDK 21
- Docker com Docker Compose

O Maven Wrapper está incluído no projeto; não é necessário instalar Maven separadamente.

## Executar localmente

Os comandos abaixo devem ser executados nesta pasta (`btg-pactual`).

1. Inicie o MongoDB e o RabbitMQ:

   ```sh
   docker compose -f local/docker-compose.yml up -d
   ```

2. Inicie a aplicação:

   ```sh
   # Windows
   .\mvnw.cmd spring-boot:run

   # Linux ou macOS
   ./mvnw spring-boot:run
   ```

3. Execute os testes:

   ```sh
   # Windows
   .\mvnw.cmd test

   # Linux ou macOS
   ./mvnw test
   ```

4. Ao terminar, pare os serviços:

   ```sh
   docker compose -f local/docker-compose.yml down
   ```

## Serviços locais

| Serviço | Endereço | Credenciais locais |
| --- | --- | --- |
| MongoDB | `localhost:27017` | usuário `admin`, senha `123` |
| RabbitMQ AMQP | `localhost:5672` | usuário `guest`, senha `guest` |
| RabbitMQ Management | [http://localhost:15672](http://localhost:15672) | usuário `guest`, senha `guest` |

As credenciais do Compose e da aplicação são apenas para desenvolvimento local. Não as utilize em ambientes compartilhados ou de produção.

## Modelo de pedido

Os pedidos são mapeados para a coleção `tb_orders`. O identificador do pedido é usado como ID do documento; o identificador do cliente possui índice. Os valores monetários são armazenados como `Decimal128`.

```json
{
  "orderId": 1001,
  "customerId": 1,
  "total": 120.00,
  "items": [
    {
      "product": "lapis",
      "quantity": 100,
      "price": 1.10
    }
  ]
}
```

O exemplo ilustra os campos do modelo atual; ainda não há endpoint ou consumidor de mensagens implementado para recebê-lo.
