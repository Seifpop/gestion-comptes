package com.formation.jee.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class HibernateContainer {
    @PersistenceUnit(unitName = UNIT_NAME)
    private EntityManagerFactory emFactory; // instance for the default persistence unit.

    /**
     * The name of the default persistence unit.
     */
    public static final String UNIT_NAME = "default";

    /**
     * Initializes the EntityManagerFactory for the default persistence unit.
     */
    @PostConstruct
    public void init() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory(UNIT_NAME);
        }
    }

    /**
     * Produces a new {@link EntityManager} instance in a request-scoped context.
     *
     * @return A new {@link EntityManager} instance for the default persistence unit.
     */
    @Produces
    @Default
    @RequestScoped
    public EntityManager create() {
        return emFactory.createEntityManager();
    }

    /**
     * Disposes of the given {@link EntityManager} instance by closing it if it is still open.
     *
     * @param entityManager The EntityManager instance to be disposed.
     */
    public void dispose(@Disposes @Default EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
