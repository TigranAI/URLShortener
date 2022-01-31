package ru.tigran.urlshortener.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.tigran.urlshortener.database.entity.Role;
import ru.tigran.urlshortener.database.repository.RoleRepository;

@Component
public class InitDatabase {

    @Autowired
    RoleRepository roleRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event){
        if (roleRepository.count() == 0){
            roleRepository.save(new Role(1L, "ROLE_USER"));
            roleRepository.save(new Role(2L, "ROLE_ADMIN"));
        }
    }
}
