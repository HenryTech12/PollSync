package com.votingapi.demo.repo;

import com.votingapi.demo.model.PollData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollRepository  extends JpaRepository<PollData, Long> {
    Optional<PollData> findByVoteId(String voteId);

}
