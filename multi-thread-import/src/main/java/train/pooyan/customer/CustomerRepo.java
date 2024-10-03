package train.pooyan.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface CustomerRepo extends CrudRepository<Customer, Long> {
}
