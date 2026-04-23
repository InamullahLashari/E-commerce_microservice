package com.example.authservice.service.auth.role;

import com.example.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long>
{

}
