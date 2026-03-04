package com.arshad.smart_pos_system.repository;

import com.arshad.smart_pos_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
    User findByEmail(String username);

    //user save(User );
}
