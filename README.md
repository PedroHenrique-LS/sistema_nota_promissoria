[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[JWT]: https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens


<h1  align="center" style="font-weight: bold; margin: 50px 0px 50px 0px;">Sistema de notas promissórias 💻</h1>


![java][JAVA_BADGE]
![spring][SPRING_BADGE]
![jwt][JWT]



<p>
Este projeto implementa o backend de um sistema para gerenciamento de notas promissórias, permitindo que os usuários controlem suas notas, criem novas, registrem pagamentos, parcelem valores e realizem outras operações essenciais.

Desenvolvido como parte de uma disciplina do curso de graduação em Sistemas de Informação, o projeto foi elaborado para atender aos requisitos estabelecidos pelo professor.
</p>


<h2>🚀 requisitos estabelecidos pelo professor</h2>


<h3>🙋🏽 Cliente</h3>
 
- **Rota para criar novo cliente**
  `POST /cliente`
- **Rota para listar Todos os clientes (com Paginação)**
  `GET /cliente?page={número}&pageSize={quantidade}`
- **Rota para editar nome ou cpf do cliente**
  `PUT /cliente/{id}`
- **Rota para exibir detalhes de um cliente**
  `GET /cliente/{id}`

<h3>📄 Nota</h3>

- **Criar uma Nota**
  `POST /v1/nota` 

- Rota para Criar uma nota. Para criar uma nota, precisamos dos seguintes dados:
  - Descrição 
  - Valor Total
  - Juros - Não é obrigatório, se não enviar, 5%, se enviar valor a ser salvo.
  - Data Emissão
  - Quantidade Parcelas 
  - Data Primeira Parcela 
  - Data Fechamento 
  - Status - Enum (Aberto) - Coloca toda vez que criar uma nota
  - Cliente (id do cliente)
  
  > 📌 **OBS:** No momento da criação da nota, as parcelas serão geradas automaticamente, com vencimento sempre no mesmo dia da primeira parcela.

- **Listar Todas as Notas de um Cliente**  
  `GET v1/cliente/{id}/nota?status={opcional}`  
  > ✅ Possibilidade de filtrar por status da nota.

- **Detalhes de uma Nota**  
  `GET /v1/nota/{id}`  
  > ✅ Se a nota estiver atrasada, exibir o valor atualizado com multa.

- **Dar Baixa em uma Nota**  
  `POST /v1/nota/{id}/baixa`  
  **Validações:**  
  - Apenas a parcela mais recente pode ser paga.  
  - Não é permitido pagar um valor menor do que o da parcela.  
  - Caso haja atraso, o pagamento deve incluir juros e multa.  

<h3>🔐 Autenticação</h3> 

- **Autenticação com JWT**  
  > O sistema utiliza JSON Web Token (JWT) para autenticação e autorização das rotas protegidas.  
- **Criar um usuário do sistema**
  `POST /v1/user`
- **Login**
  `POST /v1/login`

<h2>🚀 Getting started</h2>

<h3>Cloning</h3>

```bash
git clone https://github.com/PedroHenrique-LS/sistema_nota_promissoria
```

<h4>Prerequisites</h4>

- [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)

