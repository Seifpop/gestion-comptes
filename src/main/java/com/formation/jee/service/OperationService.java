package com.formation.jee.service;

import com.formation.jee.models.Operation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
@SuppressWarnings({"unchecked"})
public class OperationService {
    @Inject
    private EntityManager em;

    /**
     * Retrieves a list of operations associated with the specified account.
     *
     * @param accountId The ID of the account for which to retrieve the operations.
     * @return A list of Operation objects associated with the specified account.
     */
    public List<Operation> getOperations(String accountId) {
        Query query = em.createQuery("SELECT o FROM Operation o WHERE o.account.id = :accountId");
        query.setParameter("accountId", accountId);

        return (List<Operation>) query.getResultList();
    }

    /**
     * Removes an operation with the specified ID.
     *
     * @param operationId The ID of the operation to be removed.
     */
    public void removeOperation(String operationId) {
        // begin transaction
        em.getTransaction().begin();

        // remove the entity
        em.remove(em.getReference(Operation.class, operationId));

        // commit transaction
        em.flush();
        em.getTransaction().commit();
    }

    /**
     * Adds a new operation to an account.
     *
     * @param operation The operation to be added to the database.
     */
    public void addOperation(Operation operation) {
        // begin transaction
        em.getTransaction().begin();

        // remove the entity
        em.persist(operation);

        // commit transaction
        em.flush();
        em.getTransaction().commit();
    }
}
