
# Crear Entidades

Ejemplos de otras entidades típicas en un e-commerce
Aquí te dejo una tabla con otras entidades comunes que podrías encontrar en un backend como ShopVerse.

* Entidad
    * Descripción
        * Campos comunes

* Order
    * Pedido realizado por un cliente
        * id, orderDate, total, customerId

* OrderItem
    * Producto incluido dentro de un pedido
        * id, orderId, productId, quantity, price

* Review
    * Opinión o calificación de un producto
        * id, productId, rating, comment, customerId

* Payment
    * Información sobre un pago asociado a una orden
        * id, orderId, amount, method, status

## Tarea
Explora y estudia más sobre las anotaciones de JPA:
Revisa la documentación oficial y toma nota de las anotaciones que no vimos en clase.

📎 Recurso recomendado:

[📄 Java Persistence (JPA) Annotations – Spring Docs](https://docs.spring.io/spring-data/jpa/reference/jpa.html)

Anota al menos tres anotaciones nuevas que no usamos todavía y describe para qué sirven.

Piensa qué otras entidades podrías agregar a ShopVerse (por ejemplo: Customer, Order, etc.) y qué atributos deberían tener. Puedes bosquejarlas como ejercicio opcional.

# FIN Crear Entidades

# Actividad

Antes de comenzar con la implementación de los repositorios, te pedimos que realices una lectura complementaria y respondas estas preguntas en tu cuaderno o documento personal:

📖 Lectura sugerida:

[📘 Spring Data JPA - Repositorios](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories)

[📘 Spring Data JPA - Query Methods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods)

Actividad:
¿Cuál es la diferencia entre CrudRepository y JpaRepository?

Escribe 3 ejemplos de métodos personalizados por nombre (findBy...) que podrías usar en ProductRepository.

¿Cuál es la diferencia entre usar una consulta JPQL (@Query) y una consulta nativa?
# FIN ACTIVIDAD

# Recursos recomendados

[📘 Spring Boot – ResponseEntity Explained (Baeldung)](https://www.baeldung.com/spring-response-entity)

[📘 HTTP Status Codes – MDN Web Docs](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)

[📘 REST API Design Best Practices – RESTful Handbook](https://restfulapi.net/http-status-codes/)

