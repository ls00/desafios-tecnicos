# Itaú Unibanco — Desafio de Programação

Desafio de desenvolvimento e engenharia de software: criar uma API REST que recebe transações e retorna estatísticas sobre elas.

A API deve ser desenvolvida em Java ou [Kotlin](https://kotlinlang.org/) com Spring Boot. 

## Executar localmente

Requisito: Java 17. O Maven Wrapper do projeto baixa e utiliza a versão necessária do Maven, sem exigir uma instalação global.

No PowerShell, a partir da raiz do repositório:

```powershell
cd .\itau-backend
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
cd itau-backend
./mvnw spring-boot:run
```

Também é possível iniciar pelo VS Code: abra a paleta com `Ctrl+Shift+P`, selecione **Tasks: Run Task** e depois **Run itau-backend**.

A API ficará disponível em `http://127.0.0.1:8080`. Ela não possui página inicial; use as rotas abaixo.

## Restrições técnicas

O projeto:

- **Deve** estar hospedado publicamente no GitHub ou GitLab. Não envie o projeto como arquivo.
- **Não deve** ser um fork de outro projeto.
- **Deve** ter pelo menos um commit por endpoint (no mínimo três commits), para demonstrar a evolução do desenvolvimento.
- **Deve** ter todos os commits feitos pelo mesmo usuário que criou o projeto.
- **Deve** implementar exatamente os endpoints e os formatos de requisição e resposta descritos neste documento.
- **Não deve** utilizar banco de dados ou cache. Todos os dados devem ser armazenados em memória.
- **Deve** aceitar e responder apenas em JSON.

## Teste rápido da API

Com a aplicação em execução, use o PowerShell para criar uma transação, consultar as estatísticas e limpar os dados:

```powershell
$baseUrl = "http://127.0.0.1:8080"
$body = @{
    valor = 123.45
    dataHora = (Get-Date).AddSeconds(-5).ToString("o")
} | ConvertTo-Json

# Deve retornar 201 Created.
Invoke-WebRequest -Method Post -Uri "${baseUrl}/transacao" `
    -ContentType "application/json" -Body $body

# Deve retornar as estatísticas em JSON.
Invoke-RestMethod -Method Get -Uri "${baseUrl}/estatistica"

# Deve retornar 200 OK e limpar as transações.
Invoke-WebRequest -Method Delete -Uri "${baseUrl}/transacao"
```

Use uma data/hora ISO 8601 no passado. Para que a transação apareça nas estatísticas, ela deve estar dentro dos últimos 60 segundos.

## Endpoints da API

### `POST /transacao` — receber uma transação

Recebe uma transação com um valor e a data e hora em que aconteceu:

```json
{
  "valor": 123.45,
  "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

| Campo        | Significado                                    | Obrigatório |
| ------------ | ---------------------------------------------- | ------------ |
| `valor`    | Valor decimal da transação                   | Sim          |
| `dataHora` | Data e hora da transação no padrão ISO 8601 | Sim          |

Uma transação só pode ser aceita se:

1. Os campos `valor` e `dataHora` estiverem preenchidos.
2. A data e hora não estiverem no futuro.
3. A transação tiver acontecido no passado.
4. O valor não for negativo; valores iguais a zero são permitidos.

Respostas esperadas:

| Status                       | Significado                                                                                        | Corpo     |
| ---------------------------- | -------------------------------------------------------------------------------------------------- | --------- |
| `201 Created`              | A transação foi validada e registrada                                                            | Sem corpo |
| `422 Unprocessable Entity` | A transação não atende a um ou mais critérios de aceitação                                   | Sem corpo |
| `400 Bad Request`          | A requisição é inválida ou não pôde ser compreendida, por exemplo, por conter JSON inválido | Sem corpo |

### `DELETE /transacao` — limpar transações

Apaga todas as transações armazenadas em memória.

| Status     | Significado                                 | Corpo     |
| ---------- | ------------------------------------------- | --------- |
| `200 OK` | As informações foram apagadas com sucesso | Sem corpo |

### `GET /estatistica` — calcular estatísticas

Retorna estatísticas das transações que aconteceram nos últimos 60 segundos:

```json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.456,
  "min": 12.34,
  "max": 123.56
}
```

| Campo     | Significado                       |
| --------- | --------------------------------- |
| `count` | Quantidade de transações        |
| `sum`   | Soma dos valores transacionados   |
| `avg`   | Média dos valores transacionados |
| `min`   | Menor valor transacionado         |
| `max`   | Maior valor transacionado         |

A resposta deve ter status `200 OK` e incluir todos os campos. Se não houver transações no período, todos os valores devem ser zero:

```json
{
  "count": 0,
  "sum": 0,
  "avg": 0,
  "min": 0,
  "max": 0
}
```

## Extras opcionais

Os itens a seguir não são obrigatórios, mas podem diferenciar a solução:

1. Testes automatizados unitários e/ou funcionais, cobrindo também situações além dos caminhos felizes.
2. Containerização da aplicação.
3. Logs úteis para diagnosticar o funcionamento da aplicação.
4. Um endpoint de verificação de saúde (healthcheck).
5. Uma estimativa do tempo de cálculo das estatísticas.
6. Tratamento de erros que informe quais problemas ocorreram.
7. Documentação da API, por exemplo, com Swagger ou RAML.
8. Documentação sobre como construir e executar o sistema.
9. Configuração do período das estatísticas, mantendo 60 segundos como padrão.
