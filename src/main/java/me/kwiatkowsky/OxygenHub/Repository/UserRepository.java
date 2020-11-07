package me.kwiatkowsky.OxygenHub.Repository;

import me.kwiatkowsky.OxygenHub.Domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
