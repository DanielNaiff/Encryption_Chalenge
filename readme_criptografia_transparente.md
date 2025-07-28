# secure-entity-crypto-api

> API REST com criptografia transparente para campos sensíveis usando Spring Boot e JPA.

---

## 🔐 Desafio: Criptografia Transparente em Entidades JPA

Este projeto foi desenvolvido como solução para um desafio técnico com foco em **criptografia transparente de campos sensíveis** em uma aplicação com **Spring Boot**.

Desafio original: [backend‑br/desafios – Criptografia (PROBLEM.md)](https://github.com/backend-br/desafios/blob/master/cryptography/PROBLEM.md)

### ✅ Objetivo

Implementar criptografia automática para os campos `userDocument` e `creditCardToken`, garantindo que:

- Os dados estejam **criptografados no banco de dados**;
- As camadas de **serviço e controle não saibam** que os dados estão criptografados;
- A conversão ocorra **automaticamente** no processo de persistência e recuperação de dados via JPA.

---

##  Estrutura da Entidade

```java
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = EncryptionConverter.class)
    private String userDocument;

    @Convert(converter = EncryptionConverter.class)
    private String creditCardToken;

    private Long value;
}
```

> Os campos sensíveis são anotados com `@Convert` e usam um `AttributeConverter` customizado.

---

## 🔒 Criptografia Utilizada

- **Algoritmo:** AES (Advanced Encryption Standard)
- **Tipo:** Simétrica
- **Chave:** Armazenada de forma segura em um arquivo `.properties` ou variável de ambiente.

```java
public class EncryptionConverter implements AttributeConverter<String, String> {
    private static final String SECRET_KEY = "minha-chave-secreta123";

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return CryptoUtils.encrypt(attribute, SECRET_KEY);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return CryptoUtils.decrypt(dbData, SECRET_KEY);
    }
}
```

---

## 🔮 Funcionalidades

- CRUD completo com Spring Data JPA
- Criptografia e descriptografia transparente
- Banco de dados simulado (H2 ou PostgreSQL)
- Documentação com Swagger (opcional)

---

## 📊 Exemplo no Banco de Dados

| id | userDocument     | creditCardToken | value |
| -- | ---------------- | --------------- | ----- |
| 1  | MzYxNDA3ODE4MzM= | YWJjMTIz        | 5999  |

---

## 🚀 Como Executar

```bash
git clone https://github.com/danielnaiff/secure-entity-crypto-api.git
cd secure-entity-crypto-api
./mvnw spring-boot:run
```

---

## 📁 Tecnologias

- Java 17+
- Spring Boot
- Spring Data JPA
- AES Encryption
- Lombok
- Swagger (se aplicável)

---

## 📂 Organização

```
src
├── main
│   ├── java
│   │   ├── com.example.cryptoapi
│   │   │   ├── controller
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   ├── service
│   │   │   └── security (EncryptionConverter e CryptoUtils)
│   └── resources
│       └── application.properties
```

---

## 📌 Referências

- [AES - Wikipedia](https://pt.wikipedia.org/wiki/Advanced_Encryption_Standard)
- [AttributeConverter - JPA](https://docs.oracle.com/javaee/7/api/javax/persistence/AttributeConverter.html)

---

## 🙋‍♂️ Autor

Desenvolvido por [Daniel Naiff](https://github.com/danielnaiff)

