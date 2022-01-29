package ru.tigran.urlshortener.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tigran.urlshortener.database.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
