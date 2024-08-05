package br.com.alyson.apimanagertask.domain.repository;

import br.com.alyson.apimanagertask.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
