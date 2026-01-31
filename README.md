# Gerenciamento de Veículos

Esta API tem como objetivo o gerenciamento de uma frota de veículos, implementando operações de CRUD, filtros avançados, segurança por perfis de acesso e integração com serviços de câmbio.

## Requisitos Técnicos
- **Java 21**  e **Spring Boot 4.x**.
- **Segurança:** Autenticação via JWT (Bearer Token).
- **Cache:** Redis para armazenamento da cotação do dólar.
- **Persistência:** H2 Database com Hibernate 7.
- **Documentação:** Swagger/OpenAPI.

## Configuração e Execução

### Docker

Para subir o ambiente completo (API + Redis), utilize o comando:
```
docker-compose up --build
```

### Local

Caso prefira rodar localmente, é necessário um servidor Redis ativo em localhost:6379.

Bash
```
./mvnw clean install
./mvnw spring-boot:run
```
## Segurança e Perfis

A autenticação é obrigatória para todos os endpoints (exceto login e documentação). O sistema diferencia permissões por perfis:
- **USER:** Acesso restrito a métodos de consulta (GET).
- **ADMIN:** Acesso total a todas as operações.

### Credenciais padrão para teste:
- **Admin:** admin / admin123
- **User:** user / user123

### Endpoints Principais

|Método| Endpoint| Descrição|
|------|---------|----------|
|POST |/auth/login| Autenticação e geração de token JWT.|
|GET |/veiculos |Listagem com paginação e filtros (marca, ano, cor, range de preço).|
|POST |/veiculos |Cadastro de veículo. O preço enviado em BRL é convertido e salvo em USD.|
|PUT |/veiculos/{id}| Atualização integral do registro.|
|PATCH |/veiculos/{id}| Atualização parcial do registro.|
|DELETE |/veiculos/{id}| Remoção lógica (Soft Delete).|
|GET |/veiculos/relatorios/por-marca| Relatório de quantidade de veículos agrupados por marca.|


## Detalhes de Implementação
- **Armazenamento de Preço:** Conforme requisito, os valores são armazenados em dólar. A aplicação consome a AwesomeAPI para conversão em tempo real, com fallback automático para a Frankfurter API caso o serviço primário esteja indisponível.
- **Cache de Cotação:** O valor do dólar é cacheado no Redis para otimizar o tempo de resposta e evitar excesso de chamadas externas.
- **Soft Delete:** Implementado através da anotação @SoftDelete do Hibernate 7, alterando o estado do registro para ativo = false sem exclusão física do banco.
- **Tratamento de Erros:** Respostas padronizadas para erros de autenticação (401), permissão (403) e conflito de dados/placa duplicada (409).

## Documentação da API

A documentação interativa pode ser acessada em: http://localhost:8080/swagger-ui.html