package com.rajan;

import com.rajan.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {

  public static void main(String[] args) {

    var emf = Persistence.createEntityManagerFactory("my-persistence");
    EntityManager em = emf.createEntityManager();
    try{
      em.getTransaction().begin();
      Product product = new Product();
      product.setName("shirt");
      product.setId(2l);
      em.persist(product);
      em.getTransaction().commit();
    }finally {
      emf.close();
      em.close();
    }
  }
}