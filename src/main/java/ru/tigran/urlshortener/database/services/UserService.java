package ru.tigran.urlshortener.database.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tigran.urlshortener.database.entity.Role;
import ru.tigran.urlshortener.database.entity.User;
import ru.tigran.urlshortener.database.repository.RoleRepository;
import ru.tigran.urlshortener.database.repository.UserRepository;
import ru.tigran.urlshortener.utils.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found!");
        return user;
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) return false;

        String salt = PasswordEncoder.generateSalt(7);
        user.setSalt(salt);

        Set<Role> roles = new java.util.HashSet<>(Collections.singleton(new Role(1L, "ROLE_USER")));
        if (userRepository.count() == 0) roles.add(new Role(2L, "ROLE_ADMIN"));
        user.setRoles(roles);

        user.setPassword(PasswordEncoder.encode(user.getPassword(), salt));

        userRepository.save(user);
        return true;
    }
}
