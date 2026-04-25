package com.example.authservice.service.role;

import com.example.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long>
{

}
