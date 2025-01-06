package com.jwtproject.jwtdemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jwtproject.jwtdemo.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //User findonebyemailidignorecaseandpassword(String emailId, String password);

    User findByEmailIdIgnoreCaseAndPassword(String emailId, String password);

}

