package com.example.mysqltest.user;



import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUsername(String username);
	
}
