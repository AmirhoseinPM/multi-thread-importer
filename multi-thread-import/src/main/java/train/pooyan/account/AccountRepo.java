package train.pooyan.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AccountRepo {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Account item) {
        entityManager.persist(item);
    }
}
