# Sistema de Delivery em arquitetura RestFull API.

Sistema de delivery desenvolvido com Spring Boot e Java 21.

## 🚀 Tecnologias
- **Java 21 LTS** (versão mais recente)
- **Spring Boot 3.5.x**
- **Spring Security + JWT** (Autenticação e Autorização)
- **Spring Data JPA** (Persistência)
- **H2 Database** (Banco em memória)
- **Docker** (Containerização)
- **Maven** (Gerenciamento de dependências)
- **Swagger/OpenAPI** (Documentação viva)

## ⚡ Recursos Modernos Utilizados
- **Records** (Java 14+) para DTOs
- **Text Blocks** (Java 15+)
- **Pattern Matching** (Java 17+)
- **Bean Validation** (Blindagem de API)
- **Cache** (Performance)

## 🏃‍♂️ Como executar
### Você pode rodar a aplicação de duas formas:

### Opção Localmente (Modo Dev ☕)
1. **Pré-requisitos:** JDK 21 instalado
2. Clone o repositório
3. Execute: `./mvnw spring-boot:run`
4. Acesse: http://localhost:8080/health

### Opção 2: Via Docker (Recomendado 🐳)
Ideal para não precisar instalar Java na máquina.

1. **Construir a imagem:**

   ```bash
   
   docker build -t delivery_api .
   docker run -p 8080:8080 delivery_api
   

<h2>📋 Endpoints </h2>  

## Autenticação - Endpoint público para login e obtenção de token
- POST /api/login - Realizar Login

## Usuário Logado - Informações sobre o usuário autenticado atual
- GET /api/me - Quem sou eu?

## Monitoriamento - Endopoints para verificar a saúde da aplicação
- GET /health - Status da aplicação (inclui versão Java)
- GET /info - Informações/Monitoramento da aplicação
- GET /h2-console - Console do banco H2: http://localhost:8080/h2-console;

## Restaurante - Gerenciamento de restarurantes e cardápios
- GET /api/restaurantes - Listar restaurantes cadastrados
- GET /api/restaurantes/{id} - Buscar restaurantes por id
- GET /api/restaurantes/categoria/{categoria} - Filtrar por categoria
- POST /api/restaurantes - Cadastrar restaurantes

## Pedidos - Gerenciamento de pedidos, status e histórico
- GET /api/produto - Listar todos os produtos
- GET /api/restaurantes/{restauranteId}/produtos - Cardápio do restaurante
- POST /api/produtos - Cadastrar produto

## Produtos - Gerenciamento de produtos do cardápio
- GET /api/produtos - Listar produtos
- GET /api/restaurantes/{restauranteId}/produtos Cardápio do restaurante
- POST /api/produtos - Cadstrar produto

## Clientes - Gerenciamento de clientes e perfis
- GET /api/clientes - Listar clientes
- GET /api/clientes/{id} - Buscar cliente por ID
- POST /api/clientes - Cadastrar cliente

## 🔧 Configuração
- Porta: 8080
- Banco: H2 em memória
- Profile: development
- //http://localhost:8080/swagger-ui.html;
- //http://localhost:8080/h2-console;
- //docker run -p 8080:8080 delivery_api


# por mim: Gustavo Laurindo, lawrindovsk.
Desenvolvido com JDK 21 e Spring Boot 3.2.x
