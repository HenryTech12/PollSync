package com.votingapi.demo.events;

import com.votingapi.demo.dto.PollDTO;
import com.votingapi.demo.dto.UserDTO;
import com.votingapi.demo.mail.EmailService;
import com.votingapi.demo.service.PollService;
import com.votingapi.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingEvent {

    @Autowired
    private PollService pollService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(VotingEvent.class);

    @Scheduled(fixedDelay = 5000)
    public void pollExpiration() {
        logger.info("waiting ...");
        List<PollDTO> allPoll = pollService.getPolls();
        if(!allPoll.isEmpty()) {
            allPoll.forEach((poll) -> {
                boolean expires = pollService.hasVoteExpired(poll);
                if(expires) {
                    UserDTO userDTO = userService.getUserDTOByVoteId(poll.getVoteId());
                    emailService.sendEmail(userDTO.getEmail(),
                            "This a reminder regarding the poll created on "+
                                    poll.getCreatedDate() + " with vote Id: "+
                                    poll.getVoteId()+" has already expired, Thanks for using our service ",
                            "Voting Expiration Reminder");
                }
            });
        }
    }

}
