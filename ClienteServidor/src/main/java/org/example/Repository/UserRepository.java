package org.example.Repository;


import java.util.Optional;
import org.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}