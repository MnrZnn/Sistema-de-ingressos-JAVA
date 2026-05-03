# Sistema de Ingressos para Eventos

Sistema Java Web com Maven para gerenciar ingressos com herança e polimorfismo. Persistência via MongoDB Atlas.

---

## Tecnologias

- **Java 11** + **Jakarta Servlet 5**
- **Maven** (build e dependências)
- **MongoDB Atlas** (mongodb-driver-sync 4.11)
- **Tomcat 10+** (servidor de aplicação)
- **Bootstrap** via CDN + CSS customizado
- **Lucide Icons**

---

## Estrutura de Pacotes

```
src/main/java/com/ingressos/
├── model/
│   ├── Ingresso.java          ← classe abstrata
│   ├── IngressoNormal.java    ← herda Ingresso
│   ├── IngressoVIP.java       ← herda Ingresso (+50%)
│   └── IngressoMeia.java      ← herda Ingresso (-50%)
├── dao/
│   └── IngressoDAO.java       ← acesso MongoDB
├── service/
│   └── IngressoService.java   ← regras de negócio
├── servlet/
│   └── IngressoServlet.java   ← controlador HTTP
└── util/
    └── MongoDBUtil.java       ← conexão Atlas
```

---

## Configuração

### 1. MongoDB Atlas

Edite `MongoDBUtil.java` e substitua `SUA_SENHA` pela senha real do Atlas:

```java
"mongodb+srv://munirabouzenni_db_user:SUA_SENHA@biblioteca.enmxsi0.mongodb.net/sistema_ingressos"
```

### 2. Build

```bash
mvn clean package
```

### 3. Deploy

Copie o `.war` gerado em `target/sistema-ingressos.war` para a pasta `webapps/` do Tomcat 10.

---

## Conceitos Aplicados

### Herança
`IngressoNormal`, `IngressoVIP` e `IngressoMeia` herdam de `Ingresso`, reutilizando atributos comuns e sobrescrevendo comportamentos específicos.

### Polimorfismo
`IngressoService.detalharIngresso(Ingresso i)` chama `i.imprimirIngresso()` sem conhecer o tipo concreto. O mesmo ocorre em `calcularValor()` — cada subtipo aplica sua regra de preço.

---

## Rotas

| Método | Rota                          | Ação                        |
|--------|-------------------------------|-----------------------------|
| GET    | `/ingressos/lista`            | Listar todos os ingressos   |
| GET    | `/ingressos/lista?tipo=VIP`   | Filtrar por tipo            |
| GET    | `/ingressos/novo`             | Formulário de cadastro      |
| POST   | `/ingressos`                  | Salvar novo ingresso        |
| GET    | `/ingressos/detalhe?id=...`   | Detalhe do ingresso         |
| GET    | `/ingressos/cancelar?id=...`  | Cancelar ingresso           |
| GET    | `/ingressos/utilizar?id=...`  | Marcar como utilizado       |
| GET    | `/ingressos/deletar?id=...`   | Excluir ingresso            |

---

## Diagramas

Os diagramas UML em Mermaid estão na pasta `/diagramas`:

- `diagrama-classes.mmd`
- `diagrama-sequencia.mmd`
- `diagrama-estado.mmd`

Para renderizar, use o [Mermaid Live Editor](https://mermaid.live) ou extensão do VS Code.
