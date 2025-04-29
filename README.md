# 🛠️ Gerenciador de Atendimentos - API REST com Spring Boot

Este é um projeto de API REST desenvolvido com **Spring Boot**, cujo objetivo é permitir o **cadastro, atualização, listagem e exclusão de atendimentos** realizados por uma empresa ou serviço.

A aplicação está conectada inicialmente ao banco de dados em memória **H2**, mas está preparada para migração futura para **MySQL**.

---

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
    - Spring Web
    - Spring Data JPA
    - Spring Validation
- **H2 Database**
- **Swagger (Springdoc OpenAPI)**
- **Maven**

---

## 📦 Funcionalidades da API

- ✅ Criar um atendimento
- ✅ Listar todos os atendimentos
- ✅ Buscar atendimento por ID
- ✅ Atualizar atendimento
- ✅ Remover atendimento
- ✅ Validação automática de status via Enum
- ✅ Registro automático de data/hora na criação e atualização
- ✅ Documentação interativa com Swagger

---

## 🗃️ Modelo de Dados

### 📌 Entidade `Atendimento`

| Campo           | Tipo             | Descrição                             |
|----------------|------------------|---------------------------------------|
| `id`           | Long             | Identificador único                   |
| `cliente`      | String           | Nome do cliente                       |
| `descricao`    | String           | Descrição do atendimento              |
| `status`       | Enum             | Status do atendimento (`ABERTO`, `EM_ANDAMENTO`, `FINALIZADO`) |
| `dataCriacao`  | LocalDateTime    | Registrado automaticamente            |
| `dataAtualizacao` | LocalDateTime | Atualizado automaticamente            |

---

## 🔄 Requisições e Testes

Após rodar a aplicação, acesse o Swagger UI para testar a API:
🔗 http://localhost:8080/swagger-ui.html

### Exemplo de JSON para criar atendimento:
```json
{
  "cliente": "Maria Silva",
  "descricao": "Suporte técnico",
  "status": "ABERTO"
}
```

## 🛠️ Como executar o projeto localmente
```bash
# Clone o repositório
git clone https://github.com/seunome/gerenciador-atendimentos.git
cd gerenciador-atendimentos

# Compile o projeto
./mvnw clean install

# Rode a aplicação
./mvnw spring-boot:run
```

## 💡 Próximos Passos
 - 🔜 Migrar do banco H2 para MySQL 
 - 🔒 Implementar autenticação com Spring Security 
 - 🧪 Adicionar testes unitários e de integração 
 - 📊 Gerar relatórios por status

## 👨‍💻 Autor
Pablo Silva <br />
🔗 [https://linkedin.com/in/ppablods](LinkedIn)<br />
📧 ls8pablo@gmail.com

## 📄 Licença
Este projeto está licenciado sob a [MIT LICENSE](LICENSE).
