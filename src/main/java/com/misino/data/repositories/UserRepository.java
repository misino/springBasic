package com.misino.data.repositories;

import com.misino.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);
//    String findEmailByEmail(String email);
}
