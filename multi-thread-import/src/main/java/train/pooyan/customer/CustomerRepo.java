package train.pooyan.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CustomerRepo {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Customer item) {
        entityManager.persist(item);
    }
}
