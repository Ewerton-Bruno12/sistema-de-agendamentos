# 📅 Sistema de Agendamentos API

Uma API REST completa, desenvolvida em **Java** e **Spring Boot**, projetada para o gerenciamento inteligente, seguro e padronizado de clientes, serviços e seus respectivos agendamentos. O projeto aplica conceitos modernos de arquitetura de software, como o isolamento completo de camadas, uso de DTOs (`records`), validações de dados rigorosas e prevenção de conflitos de agenda protegida contra inconsistências de horários.

---

## 🚀 Tecnologias Utilizadas

* **Java 21** (Uso de recursos modernos da linguagem, como Records estáveis e melhorias gerais de performance)
* **Spring Boot 4.1.1** (Aproveitando as atualizações mais recentes de performance e segurança do ecossistema)
* **Spring Data JPA** (Persistência, mapeamentos relacionais e comunicação abstrata com o banco de dados)
* **Springdoc OpenAPI 3.1.1** (Geração automatizada de documentação interativa e contratos da API com Swagger UI)
* **Jakarta Validation / Hibernate Validator** (Garantia de integridade e blindagem dos dados de entrada)
* **Lombok** (Produtividade e eliminação de códigos boilerplates através de anotações como `@RequiredArgsConstructor`)
* **MySQL** (Banco de dados relacional robusto para a persistência segura de todas as entidades do sistema)

---

## 🛠️ Funcionalidades e Diferenciais Técnicos

* **CRUD Completo de Clientes e Serviços**: Criação, listagem geral, busca por ID, atualização e deleção para ambas as entidades.
* **Busca de Agendamentos por Relacionamento**: Endpoint dedicado no controlador de agendamentos para filtrar e buscar todos os registros vinculados a um cliente específico (`/appointments/client/{clientId}`).
* **Fluxo de Cancelamento Seguro de Horários**: Endpoint de atualização parcial (`PATCH`) para modificar de forma segura o status de um agendamento para cancelado.
* **Blindagem de Regras de Negócio**: Validações customizadas no serviço de agendamentos que barram horários duplicados para o mesmo serviço, lançando a exceção `TimeSlotUnavailableException`. Também previne novas alterações em agendamentos já cancelados com a exceção `InvalidAppointmentStateException`.
* **Isolamento de Camadas com DTOs**: Uso estratégico de Java `records` para desacoplar as entidades de banco de dados (`AppointmentEntity`, `ClientEntity` e `ServiceEntity`) das estruturas de requisição (`RequestDto`) e resposta (`ResponseDto`).
* **Tratamento Global de Erros**: Captura centralizada de exceções através de um `@RestControllerAdvice` corporativo. Garante que erros de validação de parâmetros (`MethodArgumentNotValidException`), conflitos de dados exclusivos (`EmailAlreadyExistsException`, `ServiceNameAlreadyExistsException`) ou IDs não encontrados (`ResourceNotFoundException`) retornem estruturas limpas e padronizadas no formato RFC 7807 (`ProblemDetail`).

---

## 🗺️ Endpoints da API (V1)

### Clientes (`/v1/clients`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/v1/clients` | Cria um novo cliente no sistema | `201 Created` |
| `GET` | `/v1/clients` | Lista todos os clientes cadastrados | `200 OK` |
| `GET` | `/v1/clients/{id}` | Busca um cliente específico pelo ID | `200 OK` |
| `PUT` | `/v1/clients/{id}` | Atualiza todas as informações do cliente | `200 OK` |
| `DELETE` | `/v1/clients/{id}` | Exclui um cliente do sistema | `204 No Content` |

### Serviços (`/v1/services`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/v1/services` | Cria um novo serviço no catálogo | `201 Created` |
| `GET` | `/v1/services` | Lista todos os serviços cadastrados | `200 OK` |
| `GET` | `/v1/services/{id}` | Busca um serviço específico pelo ID | `200 OK` |
| `PUT` | `/v1/services/{id}` | Atualiza todas as informações cadastrais do serviço | `200 OK` |
| `DELETE` | `/v1/services/{id}` | Exclui um serviço do sistema | `204 No Content` |

### Agendamentos (`/v1/appointments`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/v1/appointments` | Cria um novo agendamento (status inicializado como `SCHEDULED`) | `201 Created` |
| `GET` | `/v1/appointments` | Lista todos os agendamentos cadastrados | `200 OK` |
| `GET` | `/v1/appointments/{id}` | Busca um agendamento específico pelo ID | `200 OK` |
| `GET` | `/v1/appointments/client/{clientId}` | Filtra e lista todos os agendamentos de um cliente | `200 OK` |
| `PATCH` | `/v1/appointments/{id}/cancel` | Cancela de forma segura um agendamento ativo | `200 OK` |

---

## 📖 Documentação da API (Swagger UI)

A API conta com documentação automatizada e viva via **Swagger**, permitindo visualizar, mapear esquemas de validação e testar todas as requisições em tempo real.

* **Swagger UI (Painel Interativo):** `http://localhost:8080/swagger-ui/index.html`
* **OpenAPI Specs (JSON da especificação):** `http://localhost:8080/v3/api-docs`

---

## 📋 Exemplos de Payload (JSON)

### Criar Agendamento (`POST /v1/appointments`)

**Corpo da Requisição (Request Body):**
```json
{
  "clientId": 1,
  "serviceId": 1,
  "dateTime": "2026-12-25T14:30:00"
}
```

**Corpo da Resposta (Response Body):**
```json
{
  "id": 1,
  "clientName": "Ewerton Bruno",
  "serviceName": "Corte de Cabelo Masculino",
  "price": 45.00,
  "dateTime": "2026-12-25T14:30:00",
  "status": "SCHEDULED"
}
```

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* Ter o **Java 21** instalado em sua máquina.
* Ter o **MySQL** instalado e rodando localmente.

### Passos para execução

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/Ewerton-Bruno12/sistema-de-agendamentos
   ```

2. **Configurar o banco de dados:**
   Abra o arquivo `src/main/resources/application.yml` e ajuste os parâmetros com o nome do seu banco e insira as suas credenciais do MySQL substituindo as variáveis de ambiente correspondentes:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/appointment?createDatabaseIfNotExist=True
       username: seu_usuario
       password: sua_senha
     jpa:
       hibernate:
         ddl-auto: update
   ```

3. **Entrar na pasta raiz do projeto:**
   ```bash
   cd sistema-de-agendamentos
   ```

4. **Executar a aplicação via Maven Wrapper:**
   ```bash
   ./mvnw spring-boot:run
   ```
