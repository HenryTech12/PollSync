package com.votingapi.demo.repo;

import com.votingapi.demo.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByUsername(String username);
    Optional<UserData> findByEmail(String email);
    Optional<UserData> findByVoteId(String voteId);
}
