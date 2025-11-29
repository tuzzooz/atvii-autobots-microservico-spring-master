
**Pré-requisitos**
- **Java 17** instalado.
- `mvnw.cmd` (Maven wrapper) está presente no projeto; use-o para garantir versão compatível.
- (Opcional) Plugin Lombok na IDE para evitar warnings ao editar (build do Maven já funciona com Lombok como dependência opcional).

**Como rodar (PowerShell)**

```powershell
cd "c:\Users\peter\Downloads\atvii-autobots-microservico-spring-master\atvii-autobots-microservico-spring-master\automanager"
.\mvnw.cmd test           # executa testes
.\mvnw.cmd spring-boot:run  # inicia a aplicação em http://localhost:8080
```

**Build jar**

```powershell
.\mvnw.cmd -DskipTests package
# jar estará em target\automanager-0.0.1-SNAPSHOT.jar
```

**Rotas / Endpoints**
Base URL: `http://localhost:8080`

**Clientes**
- **GET /clientes**: lista todos os clientes
	- Exemplo: `GET http://localhost:8080/clientes`
	- Resposta: `200 OK` com array JSON (cada item contém `_links`), ou `204 NO_CONTENT` se vazio.

- **GET /clientes/{id}**: obtém cliente por id
	- Exemplo: `GET http://localhost:8080/clientes/1`
	- Resposta: `200 OK` com objeto JSON + `_links.self` e `_links.clientes`, ou `404 NOT_FOUND`.

- **POST /clientes**: cria um cliente
	- URL: `POST http://localhost:8080/clientes`
	- Header: `Content-Type: application/json`
	- Body (exemplo):
		```json
		{
			"nome": "João Silva",
			"nomeSocial": "J. Silva",
			"dataNascimento": "1990-01-01"
		}
		```
	- Resposta: `201 Created` com header `Location: /clientes/{id}` (corpo vazio). Se `id` informado no body → `409 CONFLICT`.

- **PUT /clientes/{id}**: atualiza cliente
	- URL: `PUT http://localhost:8080/clientes/1`
	- Body exemplo similar ao POST
	- Resposta: `200 OK` ou `404 NOT_FOUND`.

- **DELETE /clientes/{id}**: remove cliente
	- URL: `DELETE http://localhost:8080/clientes/1`
	- Resposta: `204 NO_CONTENT` ou `404 NOT_FOUND`.

**Documentos**
- **GET /documentos**, **GET /documentos/{id}**, **POST /documentos**, **PUT /documentos/{id}**, **DELETE /documentos/{id}**
	- POST Body exemplo:
		```json
		{ "tipo": "RG", "numero": "123456789" }
		```

**Endereços**
- **GET /enderecos**, **GET /enderecos/{id}**, **POST /enderecos**, **PUT /enderecos/{id}**, **DELETE /enderecos/{id}**
	- POST Body exemplo mínimo:
		```json
		{ "cidade": "São Paulo", "rua": "Rua A", "numero": "100" }
		```

**Telefones**
- **GET /telefones**, **GET /telefones/{id}**, **POST /telefones**, **PUT /telefones/{id}**, **DELETE /telefones/{id}**
	- POST Body exemplo:
		```json
		{ "ddd": "11", "numero": "999999999" }
		```

