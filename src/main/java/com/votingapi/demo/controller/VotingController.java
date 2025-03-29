package com.votingapi.demo.controller;

import com.votingapi.demo.dto.PollDTO;
import com.votingapi.demo.dto.PollOption;
import com.votingapi.demo.dto.UserDTO;
import com.votingapi.demo.service.PollService;
import com.votingapi.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.*;

@Controller
public class VotingController {

    private String storeId;
    private final int votedIndex = 0;
    @Autowired
    private PollService pollService;
    @Autowired
    private UserService userService;

    @RequestMapping("/create-poll")
    public String createPoll(Model model) {
        PollDTO pollDTO = new PollDTO();
        pollService.addPollOption(pollDTO);
        model.addAttribute("pollDTO", pollDTO);
        updateValues(model);
        return "create_poll";
    }

    public void updateValues(Model model) {
        model.addAttribute("days", pollService.addDayValue());
        model.addAttribute("timeout", pollService.addTimeValue());
    }

    @RequestMapping("/create-option")
    public String addOption(Model model, @ModelAttribute PollDTO pollDTO) {

      if(!Objects.isNull(pollDTO)) {
          PollOption option = pollDTO.getOption();
          if(!Objects.isNull(option)) {
              List<String> options = option.getOptions();

              options.add("");


              System.out.println("SIZE: " + options.size());

              option.setOptions(options);

              pollDTO.setOption(option);
              model.addAttribute("pollDTO", pollDTO);
          }
      }
      else {
        PollDTO pollDTO1 = new PollDTO();
        pollService.addPollOption(pollDTO1);
        model.addAttribute("pollDTO", pollDTO1);
      }
       updateValues(model);
        return "create_poll";
    }

    @RequestMapping("poll/save")
    public String savePoll(@ModelAttribute @Valid PollDTO pollDTO) {
        if(!Objects.isNull(pollDTO)) {

            pollService.updateResult(pollDTO);
            pollService.addPoll(pollDTO);
        }
        return "index";
    }


    @RequestMapping("poll/get")
    public String pollPage(Model model) {
        model.addAttribute("pollDTO", new PollDTO());
        return "vote";
    }

    @RequestMapping("poll/result")
    public String getById(@ModelAttribute PollDTO pollDTO, Model model) {
        if(!Objects.isNull(pollDTO)) {
            PollDTO resultPoll = pollService.getByVoteId(pollDTO);
            if(!Objects.isNull(resultPoll)) {
                boolean expires = pollService.hasVoteExpired(pollDTO);
                if(!expires) {
                    pollService.updateResult(resultPoll);
                    System.out.println("size: "+ resultPoll.getOption().getOptions().size());
                    model.addAttribute("pollDTO", resultPoll);
                    model.addAttribute("voteID", pollDTO.getVoteId());
                    model.addAttribute("error", "");
                    System.out.println("ID: " + pollDTO.getVoteId());
                }
                else {
                    model.addAttribute("error", "Voting ID as already expired, Poll is closed already");
                }
            }
            else {
                model.addAttribute("error", "Invalid Voting ID, ID doesn't exist");
                return "vote";
            }
        }
        return "poll";
    }
   @RequestMapping("poll/update")
   public String updatePollResult(Principal principal, @ModelAttribute PollDTO pollDTO, int pos, String voteID, Model model) {
        if(!Objects.isNull(pollDTO)) {
            pollDTO.setVoteId(voteID);
            boolean expires = pollService.hasVoteExpired(pollDTO);
            if(!expires) {
                UserDTO newUserDTO = new UserDTO();
                newUserDTO.setUsername(principal.getName());
                UserDTO userDTO = userService.getUserDataByUsername(newUserDTO);
                if (!Objects.isNull(userDTO)) {
                    if (!userDTO.isHasVoted()) {
                        if (this.storeId == null) {
                            storeId = voteID;
                        }
                        PollDTO idPoll = new PollDTO();
                        idPoll.setVoteId(storeId);
                        idPoll.setVoted(pollDTO.isVoted());

                        PollDTO fetchPoll = pollService.getByVoteId(idPoll);

                        if (!Objects.isNull(fetchPoll)) {
                            List<Integer> results = fetchPoll.getOption().getResults();
                            int current_result = results.get(pos);
                            current_result++;//increment result value
                            results.set(pos, current_result);
                            pollService.updatePoll(fetchPoll); //updates result of poll

                            userDTO.setHasVoted(true);
                            userDTO.setVoteId(storeId);
                            userService.updateUserByUsername(principal.getName(), userDTO); // updates on db if user ha voted
                            model.addAttribute("pollDTO", fetchPoll);
                            model.addAttribute("voteID", pollDTO.getVoteId());
                        }
                    } else {
                        PollDTO pollDTO1 = new PollDTO();
                        pollDTO1.setVoteId(userDTO.getVoteId());
                        PollDTO fetchPoll = pollService.getByVoteId(pollDTO1);
                        model.addAttribute("pollDTO", fetchPoll);
                        model.addAttribute("voteID", userDTO.getVoteId());

                        if (!Objects.isNull(fetchPoll)) {
                            if (fetchPoll.isMultipleAnswer()) {
                                List<Integer> results = fetchPoll.getOption().getResults();
                                int current_result = results.get(pos);
                                if (current_result < 1) {
                                    current_result++;//increment result value
                                    results.set(pos, current_result);
                                    pollService.updatePoll(fetchPoll); //updates result of poll

                                    userDTO.setHasVoted(true);
                                    userDTO.setVoteId(storeId);
                                    userService.updateUserByUsername(principal.getName(), userDTO); // updates on db if user ha voted
                                    model.addAttribute("pollDTO", fetchPoll);
                                    model.addAttribute("voteID", pollDTO.getVoteId());
                                }
                            }
                        }
                    }
                }
            }
            else {
                model.addAttribute("error", "Vote has already expired");
            }
        }
        return "poll";
   }

   @RequestMapping("/showResult")
   public String showResult() {
        return "showResult";
   }
   @PostMapping("/poll/live")
   public String result(Principal principal ,String voteId, Model model) {
        PollDTO pollDTO = new PollDTO();
        pollDTO.setVoteId(voteId);
        Map<String, Integer> pollMessage = new HashMap<>();

        if(!pollService.hasVoteExpired(pollDTO)) {
            PollDTO myPoll = pollService.getByVoteId(pollDTO);

            if (!Objects.isNull(myPoll)) {
                String user = (myPoll.isAnonymous()) ? "Anonymous User" : principal.getName();
                model.addAttribute("user", user);
                model.addAttribute("timeout", myPoll.getEndDay());
                List<String> options = myPoll.getOption().getOptions();
                List<Integer> results = myPoll.getOption().getResults();
                for (int i = 0; i < options.size(); i++) {
                    pollMessage.put("Option:(" + options.get(i) + ") has a total votes of " + results.get(i), results.get(i));
                }
            }
        }
        else {
            model.addAttribute("error", "Vote has already expired");
        }
        model.addAttribute("message", pollMessage);
        model.addAttribute("pollDTO", pollService.getByVoteId(pollDTO));

        return "result";
   }
}
