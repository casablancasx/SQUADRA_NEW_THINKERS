# Squadra New Thinkers - Java Bootcamp 2024

Este projeto foi desenvolvido como parte do desafio do programa de trainee **Java New Thinkers** da **Squadra Digital**.

## Tecnologias e Dependências

- **JPA (Java Persistence API):** Utilizado para criação e manipulação de tabelas, permitindo a persistência e o gerenciamento de dados no banco de dados.
- **Oracle Database:** Banco de dados utilizado para armazenamento e consulta dos dados da aplicação.
- **MapStruct:** Ferramenta para mapeamento de objetos, facilitando a conversão entre entidades e DTOs (Data Transfer Objects).
- **Lombok:** Reduz o código boilerplate (getter, setter, toString, etc.), tornando o código mais limpo e de fácil manutenção.
- **Validation:** Utilizado para validação dos dados de entrada, garantindo consistência e integridade no sistema.

## Estrutura do Projeto

O projeto segue uma arquitetura modular e bem organizada, promovendo escalabilidade e facilidade de manutenção. As camadas principais incluem:

1. **Camada de Entidade** - Define as entidades que representam as tabelas no banco de dados, utilizando JPA para mapeamento objeto-relacional.
2. **Camada de Repositório** - Gerencia a interação direta com o banco de dados, fornecendo métodos para operações de CRUD.
3. **Camada de Serviço** - Contém a lógica de negócios da aplicação, garantindo uma interface clara para o uso dos dados.
4. **Camada de DTO** - Utiliza o MapStruct para converter entidades em DTOs, promovendo uma separação entre o modelo de dados e a representação da API.
5. **Camada de Controlador** - Exponibiliza os endpoints da API e cuida da interação com os clientes, validando os dados de entrada e retornando respostas apropriadas.
6. **Camada de Mapper** - Contém as interfaces que definem as conversões entre DTOs e entidades e vice-versa, utilizando o MapStruct para automatizar o mapeamento de dados.
7. **Camada Infraestrutura (infra)** - Gerencia a configuração de tratamento de erros da aplicação:
    - **ExceptionHandler:** Responsável por interceptar e tratar exceções lançadas durante a execução, garantindo respostas personalizadas.
    - **StandardError:** Classe que padroniza o formato das respostas de erro.
    - **Pacote Exceptions:** Contém exceções personalizadas para tratar casos específicos de erro de forma mais clara e organizada.

## Instalação e Configuração

1. **Clone o repositório**:
   ```bash
   git clone <URL do repositório>

2. **Banco de dados**:
   - Crie um banco de dados Oracle e configure as credenciais no arquivo `application.properties`.

3. **Construa o projeto**:
   ```bash
   mvn clean install
   
4. **Execute a aplicação**:
   ```bash
   mvn spring-boot:run