package com.example.demo.config;

import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

/**
 * Seeds initial permissions, roles, and an admin user if database is empty.
 * This uses JPA and respects spring.jpa.hibernate.ddl-auto settings.
 */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(PermissionRepository permissionRepository,
                               RoleRepository roleRepository,
                               UserRepository userRepository,
                               PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() > 0) return;

            // Define baseline permissions relevant to modules in RFP
            List<String> perms = List.of(
                "USER_READ","USER_WRITE",
                "PROJECT_READ","PROJECT_WRITE",
                "TENDER_READ","TENDER_WRITE",
                "CONTRACTOR_READ","CONTRACTOR_WRITE",
                "MILESTONE_READ","MILESTONE_WRITE",
                "PAYMENT_READ","PAYMENT_WRITE",
                "REPORT_READ"
            );
            for (String p : perms) {
                permissionRepository.findByName(p).orElseGet(() -> permissionRepository.save(new Permission(p)));
            }

            // Roles
            Role admin = roleRepository.findByName("ADMIN").orElseGet(() -> {
                Role r = new Role("ADMIN");
                r.setPermissions(Set.copyOf(permissionRepository.findAll()));
                return roleRepository.save(r);
            });

            Role inspector = roleRepository.findByName("INSPECTOR").orElseGet(() -> {
                Role r = new Role("INSPECTOR");
                r.setPermissions(Set.copyOf(permissionRepository.findAll().stream()
                        .filter(pp -> pp.getName().endsWith("_READ") || pp.getName().equals("REPORT_READ"))
                        .toList()));
                return roleRepository.save(r);
            });

            Role pm = roleRepository.findByName("PROJECT_MANAGER").orElseGet(() -> {
                Role r = new Role("PROJECT_MANAGER");
                r.setPermissions(Set.copyOf(permissionRepository.findAll().stream()
                        .filter(pp -> pp.getName().startsWith("PROJECT_") || pp.getName().startsWith("MILESTONE_") || pp.getName().equals("REPORT_READ"))
                        .toList()));
                return roleRepository.save(r);
            });

            // Admin user
            if (!userRepository.existsByUsername("admin")) {
                User u = new User("admin", encoder.encode("Admin@123"), true);
                u.getRoles().add(admin);
                userRepository.save(u);
            }
        };
    }
}
