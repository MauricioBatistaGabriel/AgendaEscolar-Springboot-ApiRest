package org.example.domain.repository;

import org.example.domain.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Integer> {

    Optional<TokenBlackList> findByToken(String token);
}
