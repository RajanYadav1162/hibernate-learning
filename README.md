# **Hibernate Core - Chapter 1 Notes**

## ✅ **JPA vs Hibernate vs ORM**

- **ORM (Object Relational Mapping):** Technique to map Java objects to database tables.
- **JPA (Jakarta Persistence API):** A **specification** (i.e., an interface/contract) that defines how Java objects should interact with relational databases.
- **Hibernate:** A **concrete implementation** of the JPA specification (plus many extra features).

---

## 🧠 **How JDBC vs Hibernate Works**

- **JDBC:** Directly interacts with the DBMS by executing SQL queries immediately.
- **Hibernate:** Works with an in-memory context called the **persistence context**.
    - Entities are first **stored in memory**.
    - Changes are **flushed** (mirrored) to the database later (usually at commit).

---

## 📦 **Persistence Context**

- Managed by the **framework** (Hibernate).
- It’s a good practice to **keep the context small** to improve performance and reduce memory usage.

---

## 🔧 **Setting Up Hibernate with `persistence.xml`**

- **File Location:** `src/main/resources/META-INF/persistence.xml`
- Used to configure DB connection, driver, dialect, and other properties.

### 🔍 **Sample `persistence.xml`**

```xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
  version="3.0">

  <persistence-unit name="my-persistence">

    <properties>
      <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost/demo"/>
      <property name="jakarta.persistence.jdbc.user" value="root"/>
      <property name="jakarta.persistence.jdbc.password" value="root"/>

      <!-- Agroal connection pool -->
      <property name="hibernate.agroal.maxSize" value="20"/>

      <!-- Display SQL in console -->
      <property name="hibernate.show_sql" value="true"/>
      <property name="hibernate.format_sql" value="true"/>
      <property name="hibernate.highlight_sql" value="true"/>

    </properties>
  </persistence-unit>
</persistence>
```

---

## 🧱 **Entity Management**

- The `EntityManager` is the main class that **manages the persistence context**.
- Based on the **Factory Design Pattern**:
  ```java
  EntityManagerFactory emf = Persistence.createEntityManagerFactory(...);
  EntityManager em = emf.createEntityManager();
  ```
- Operations like `persist`, `merge`, `remove` are done inside a transaction (`begin` & `commit`).

---

## 📝 **Example Code**

```java
public class Main {
  public static void main(String[] args) {
    var emf = Persistence.createEntityManagerFactory("my-persistence");
    EntityManager em = emf.createEntityManager();

    try {
      em.getTransaction().begin();

      Product product = new Product();
      product.setId(2L);
      product.setName("shirt");

      em.persist(product);  // Add to persistence context

      em.getTransaction().commit();  // Flush changes to DB
    } finally {
      em.close();
      emf.close();
    }
  }
}
```

---

## 📌 **Entity Class Example**

- Mark class with `@Entity`
- Mark primary key with `@Id`

```java
@Entity
public class Product {
  @Id
  private Long id;
  private String name;

  // Getters and Setters
}
```

---
