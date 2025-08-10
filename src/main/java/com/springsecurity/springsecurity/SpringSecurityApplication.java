package com.springsecurity.springsecurity;

import com.springsecurity.springsecurity.persistence.entity.PermissionEntity;
import com.springsecurity.springsecurity.persistence.entity.RoleEntity;
import com.springsecurity.springsecurity.persistence.entity.UserEntity;
import com.springsecurity.springsecurity.persistence.repository.UserRepository;
import com.springsecurity.springsecurity.utils.enums.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

   /* @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            *//* Create PERMISSIONS *//*
            PermissionEntity createPermission = PermissionEntity.builder()
                    .name("CREATE")
                    .build();

            PermissionEntity readPermission = PermissionEntity.builder()
                    .name("READ")
                    .build();

            PermissionEntity updatePermission = PermissionEntity.builder()
                    .name("UPDATE")
                    .build();

            PermissionEntity deletePermission = PermissionEntity.builder()
                    .name("DELETE")
                    .build();

            PermissionEntity refactorPermission = PermissionEntity.builder()
                    .name("REFACTOR")
                    .build();

            *//* Create ROLES *//*
            RoleEntity roleAdmin = RoleEntity.builder()
                    .role(RoleEnum.ADMIN)
                    .permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();

            RoleEntity roleUser = RoleEntity.builder()
                    .role(RoleEnum.USER)
                    .permissions(Set.of(createPermission, readPermission))
                    .build();

            RoleEntity roleInvited = RoleEntity.builder()
                    .role(RoleEnum.INVITED)
                    .permissions(Set.of(readPermission))
                    .build();

            RoleEntity roleDeveloper = RoleEntity.builder()
                    .role(RoleEnum.DEVELOPER)
                    .permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                    .build();

            *//* CREATE USERS *//*
            UserEntity userSantiago = UserEntity.builder()
                    .username("santiago")
                    .password("$2a$10$fc/XX8gfOYIznKjP62NqweymnSAALQKXiJ//HYqqL8QdNVVJflZxu")
                    .enabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleAdmin))
                    .build();

            UserEntity userDaniel = UserEntity.builder()
                    .username("daniel")
                    .password("$2a$10$fc/XX8gfOYIznKjP62NqweymnSAALQKXiJ//HYqqL8QdNVVJflZxu")
                    .enabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleUser))
                    .build();

            UserEntity userAndrea = UserEntity.builder()
                    .username("andrea")
                    .password("$2a$10$fc/XX8gfOYIznKjP62NqweymnSAALQKXiJ//HYqqL8QdNVVJflZxu")
                    .enabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleInvited))
                    .build();

            UserEntity userAnyi = UserEntity.builder()
                    .username("anyi")
                    .password("$2a$10$fc/XX8gfOYIznKjP62NqweymnSAALQKXiJ//HYqqL8QdNVVJflZxu")
                    .enabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))
                    .build();

            userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
        };
    }*/
}
