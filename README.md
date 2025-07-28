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

- TransactionRequest — para receber os dados de entrada ao criar uma transação (CREATE);

- TransactionResponse — para retornar dados ao cliente após operações, garantindo que apenas os campos necessários sejam expostos;

- TransactionUpdateRequest — para atualizar dados da transação (UPDATE) sem expor diretamente a entidade.

```java
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_document", nullable = false, unique = true)
    @Convert(converter = AESConverter.class)
    private String userDocument;

    @Column(name = "credit_card_token", nullable = false)
    @Convert(converter = AESConverter.class)
    private String creditCardToken;

    @Column(name="user_value")
    private Long transactionValue;
}
```

> Os campos sensíveis são anotados com `@Convert` e usam um `AttributeConverter` customizado.

---

## 🔒 Criptografia Utilizada

- **Algoritmo:** AES (Advanced Encryption Standard)
- **Tipo:** Simétrica
- **Chave:** Armazenada de forma segura na variável de ambiente.

```java
@Converter
public class AESConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES";

    private static final byte[] KEY = System.getenv("APP_KEY").getBytes();

    private final SecretKeySpec secretKeySpec;

    public AESConverter() {
        this.secretKeySpec = new SecretKeySpec(KEY, ALGORITHM);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(attribute.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decoded = Base64.getDecoder().decode(dbData);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar", e);
        }
    }
}

```

---

##  Funcionalidades

- CRUD completo com Spring Data JPA
- Criptografia e descriptografia transparente
- Banco de dados simulado (PostgreSQL)
- Docker

---

## 📊 Exemplo no Banco de Dados

| id | userDocument     | creditCardToken | value |
| -- | ---------------- | --------------- | ----- |
| 1  | MzYxNDA3ODE4MzM= | YWJjMTIz        | 5999  |

---



## 📁 Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- AES Encryption
- Lombok

---

## 📂 Organização

```
src
├── main
│   ├── java
│   │   ├── com.DanielNaiff.encryption
│   │   │   ├── controller
│   │   │   ├── model
│   │   │   │   └──dto 
│   │   │   ├── repository
│   │   │   ├── service
│   │   │   └── security
│   │   │       └──converter
│   └── resources
│       └── application.properties
```

---

## 📌 Referências

- [AES - Wikipedia](https://pt.wikipedia.org/wiki/Advanced_Encryption_Standard)
- [AttributeConverter - JPA](https://docs.oracle.com/javaee/7/api/javax/persistence/AttributeConverter.html)

---

## 🙋‍♂️ Autor

Desenvolvido por [Daniel Naiff](https://github.com/DanielNaiff)

