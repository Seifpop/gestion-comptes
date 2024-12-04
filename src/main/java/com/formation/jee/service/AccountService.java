package com.formation.jee.service;

import com.formation.jee.models.Account;
import org.hibernate.Hibernate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@ApplicationScoped
@SuppressWarnings({"unchecked"})
public class AccountService {
    @Inject
    private EntityManager em;

    // Méthode pour récupérer des comptes bancaires
    public List<Account> getAccounts() {
        return (List<Account>) em.createQuery("SELECT a FROM Account a").getResultList();
    }

    // Méthode pour créer des comptes bancaires
    public void addAccount(Account account) {
        if (getAccountByNumber(account.getNumber()) != null) {
            throw new IllegalArgumentException("Account already exists.");
        }

        // begin transaction
        em.getTransaction().begin();

        // save entity
        em.persist(account);

        // commit transaction
        em.flush();
        em.getTransaction().commit();
    }

    // Méthode pour éditer des comptes bancaires
    public void editAccount(Account account) {
        Account model = getAccountByNumber(account.getNumber());
        if (model == null) {
            throw new IllegalArgumentException("Account does not exist.");
        }

        // begin transaction
        em.getTransaction().begin();

        // save entity
        model.setName(account.getName());
        em.persist(model);

        // commit transaction
        em.flush();
        em.getTransaction().commit();
    }

    /**
     * Retrieves an {@link Account} from the database using the provided {@code accountNumber}.
     *
     * @param accountNumber The unique identifier for the {@link Account} to be retrieved.
     * @return The {@link Account} corresponding to the provided {@code accountNumber}, or {@code null} if the object is not found.
     */
    public Account getAccountByNumber(String accountNumber) {
        // Attempt to retrieve the Account from the database using the provided accountNumber
        Account model = em.getReference(Account.class, accountNumber);
        if (model != null) { // Check if it was successfully retrieved
            // If the object is a lazy-loaded model,
            // initialize it to ensure all data is available
            if (!Hibernate.isInitialized(model)) {
                try {
                    // Initialize the lazy-loaded object
                    Hibernate.initialize(model);
                } catch (EntityNotFoundException ignored) {
                }
            }
        }

        return model;
    }
}
