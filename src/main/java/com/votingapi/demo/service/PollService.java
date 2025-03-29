package com.votingapi.demo.service;

import com.votingapi.demo.dto.PollDTO;
import com.votingapi.demo.dto.PollOption;
import com.votingapi.demo.dto.UserDTO;
import com.votingapi.demo.mail.EmailService;
import com.votingapi.demo.mapper.PollMapper;
import com.votingapi.demo.model.PollData;
import com.votingapi.demo.model.UserData;
import com.votingapi.demo.repo.PollRepository;
import com.votingapi.demo.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PollMapper pollMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(PollService.class);
    public void addPoll(PollDTO pollDTO) {
        if(!Objects.isNull(pollDTO)) {
            pollDTO.setCreatedDate(LocalDate.now());
            pollDTO.setCreatedTime(LocalTime.now());
            PollData pollData = pollMapper.convert_polldto_to_polldata(pollDTO);
            pollData.setVoteId(UUID.randomUUID().toString());
            pollRepository.save(pollData);
            logger.info("poll saved to database");
            sendVoteIdMail(pollDTO,pollData.getVoteId()); // send vote id to user
        }
    }

    public void sendVoteIdMail(PollDTO pollDTO, String voteId) {
        Optional<UserData> userData = userRepository.findByVoteId(pollDTO.getVoteId());
        if(userData.isPresent()) {
            UserData user = userData.orElse(new UserData());
            String content = """
                      %s created a poll , here is your voting id %s you can 
                      share with in order to access your poll, and vote.
                      Thanks for using our service
                    """.formatted(user.getUsername(), voteId);
            emailService.sendEmail(user.getEmail(),content, "Vote Success Message");
        }
    }

    public void updateResult(PollDTO pollDTO) {
        if(!Objects.isNull(pollDTO)) {
            PollOption pollOption = pollDTO.getOption();
            if(!Objects.isNull(pollOption)) {
                if(!Objects.isNull(pollOption.getResults())) {
                    pollDTO.getOption().setResults(pollOption.getResults());
                }
                else {
                    List<Integer> results = new ArrayList<>();
                    for (String option : pollOption.getOptions()) {
                        results.add(0);
                        System.out.println("hello result");
                        pollOption.setResults(results);
                    }
               }
            }
        }
    }

    public void removePoll(PollDTO pollDTO) {
        if(!Objects.isNull(pollDTO)) {
            Optional<PollData> optionalPollData =
                    pollRepository.findByVoteId(pollDTO.getVoteId());
            if(optionalPollData.isPresent()) {
                PollData pollData =
                        optionalPollData.orElse(new PollData());
                pollRepository.deleteById(pollData.getId());

                logger.info("removed poll object from database");
            }
        }
    }

    public PollDTO getByVoteId(PollDTO pollDTO) {
        if(!Objects.isNull(pollDTO)) {
            Optional<PollData> optionalPollData =
                    pollRepository.findByVoteId(pollDTO.getVoteId());
            if(optionalPollData.isPresent()) {
                    PollData pollData = optionalPollData.
                            orElseThrow(() -> {
                                throw new NullPointerException();
                            });
                    return pollMapper.convert_polldata_to_polldto(pollData);
            }
        }
        return null;
    }

    public boolean hasVoteExpired(PollDTO pollDTO) {

        Optional<PollData> optionalPollData =
                pollRepository.findByVoteId(pollDTO.getVoteId());
        if(optionalPollData.isPresent()) {
            PollData pollData = optionalPollData.orElse(new PollData());

            int endDay = pollData.getEndDay();
            int endTime = pollData.getEndTime();

            LocalDate createdDate = pollData.getCreatedDate().plusDays(endDay);
            LocalTime createdTime = pollData.getCreatedTime().plusHours(endTime);

            System.out.println("end Day "+endDay);
            System.out.println("end Time "+endTime);

            LocalDate currentLocalDate = LocalDate.now();
            LocalTime currentLocalTime = LocalTime.now();

            boolean verifyDate = currentLocalDate.isAfter(createdDate);
            boolean verifyTime = currentLocalTime.isAfter(createdTime);

            System.out.println(verifyDate+"  "+verifyTime);
            return (verifyDate && verifyTime);
        }
        return false;
    }

    public List<PollDTO> getPolls() {
        List<PollData> optionalPollData = pollRepository.findAll();
        List<PollDTO> pollDTOList = new ArrayList<>();
        optionalPollData.forEach((res) -> {
            PollDTO pollDTO =  pollMapper.convert_polldata_to_polldto(res);
            pollDTOList.add(pollDTO);
        });
        return  pollDTOList;
    }

    public void updatePoll(PollDTO fetchPoll) {
        if(!Objects.isNull(fetchPoll)) {
            Optional<PollData> optionalPollData =
                    pollRepository.findByVoteId(fetchPoll.getVoteId());
            if(optionalPollData.isPresent()) {
                PollData pollData = optionalPollData.orElseThrow(() -> {
                    throw new NullPointerException();
                });
                if(!Objects.isNull(pollData)) {
                    pollData.setVoted(fetchPoll.isVoted());
                    pollRepository.save(pollData);
                    System.out.println("updated poll successfully");
                    logger.info("updated poll successfully");
                }
            }
        }
    }

    public void addPollOption(PollDTO pollDTO) {
        List<String> options = new ArrayList<>();
        options.add("");


        PollOption option = new PollOption();
        option.setOptions(options);

        pollDTO.setOption(option);
    }

    public List<Integer> addDayValue() {
        List<Integer> values = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        for(int i = 0; i <= 30; i++) {
            values.add(i);
        }
        return values;
    }
    public List<Integer> addTimeValue() {
        List<Integer> values = new ArrayList<>();
        for(int i = 0; i <= 24; i++) {
            values.add(i);
        }
        return values;
    }
}
