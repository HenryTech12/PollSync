package com.votingapi.demo.service;

import com.votingapi.demo.dto.UserDTO;
import com.votingapi.demo.dto.UserProfile;
import com.votingapi.demo.mail.EmailService;
import com.votingapi.demo.mapper.UserMapper;
import com.votingapi.demo.model.UserData;
import com.votingapi.demo.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(UserDTO userDTO) {
        if(!Objects.isNull(userDTO)) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword())); // encode passsword
            userRepository.save(userMapper.convert_userdto_to_userdata(userDTO));
            emailService.sendHTMLFile(userDTO.getEmail(),"Voting Welcome Mail", "welcome");
         }
        logger.info("user service added to database.");
    }

    public UserDTO getUserDataByUsername(UserDTO userDTO) {
        if(!Objects.isNull(userDTO)) {
           Optional<UserData> userDataOptional =
                   userRepository.findByUsername(userDTO.getUsername());

            UserData userData =  userDataOptional.orElseThrow(() -> {
                       throw new NullPointerException("user data not found");
               });
            return userMapper.convert_userdata_to_userdto(userData);
           }
        return new UserDTO();
    }

    public void updateUserByUsername(String username , UserDTO newUserDTO) {
        if(!Objects.isNull(newUserDTO)) {
            Optional<UserData> optionalUserData = userRepository.findByUsername(username);
            if(optionalUserData.isPresent()) {
                UserData userData = optionalUserData.orElse(new UserData());

                String voteId = newUserDTO.getVoteId();
                newUserDTO.setVoteId(""); // avoid number format exception;
                UserData newUserData = userMapper.convert_userdto_to_userdata(newUserDTO);
                newUserData.setId(userData.getId());
                newUserData.setVoteId(voteId);

                userRepository.save(newUserData);
                logger.info("user data updated in database");
            }
        }
    }

    public UserProfile getProfile(UserDTO userDTO) {
        return userMapper.convert_userdto_to_userprofile(userDTO);
    }

   public boolean updatePassword(UserProfile userProfile) {
        Optional<UserData> optionalUserData = userRepository.findByUsername(userProfile.getUsername());
        if(optionalUserData.isPresent()) {
            UserData dbUserData = optionalUserData.orElse(new UserData());
            if(userProfile.getPassword().equals(userProfile.getConfirmPassword())) {
                boolean matches = passwordEncoder.matches(userProfile.getOldPassword(), dbUserData.getPassword());
                if(matches) {
                    dbUserData.setPassword(passwordEncoder.encode(userProfile.getPassword()));
                    userRepository.save(dbUserData);
                    logger.info("password changed successfully");
                    if(userProfile.getEmail() != null) {
                        emailService.sendEmail(userProfile.getEmail(), "You have successfully " +
                                "changed your password, in case this isn't from you! Kindly contact the " +
                                "app Service and report issue. Thanks!", "Password Changed");
                    }
                    return true;
                }
            }
        }
        return false;
   }

   public UserDTO getUserDTOByVoteId(String voteId) {
        Optional<UserData> optionalUserData = userRepository.findByVoteId(voteId);
        if(optionalUserData.isPresent()) {
            UserData userData = optionalUserData.orElseThrow(() -> {
                throw new NullPointerException("user data not found");
            });
            if(!Objects.isNull(userData))
                return userMapper.convert_userdata_to_userdto(userData);
        }
        return new UserDTO();
   }

    public String generateApplicationUrl(HttpServletRequest httpServletRequest) {
        System.out.println("URL "+httpServletRequest.getRequestURL());
        return httpServletRequest.getRequestURL().toString();
    }
}
