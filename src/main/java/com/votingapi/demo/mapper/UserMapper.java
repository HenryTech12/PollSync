package com.votingapi.demo.mapper;

import com.votingapi.demo.dto.UserDTO;
import com.votingapi.demo.dto.UserProfile;
import com.votingapi.demo.model.UserData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserMapper {
    @Autowired
    private ModelMapper mapper;

    public UserData convert_userdto_to_userdata(UserDTO userDTO) {
         if(!Objects.isNull(userDTO))
             return mapper.map(userDTO, UserData.class);
         else
             return null;
    }

    public UserDTO convert_userdata_to_userdto(UserData userData) {
        if(!Objects.isNull(userData))
            return mapper.map(userData, UserDTO.class);
        else
            return null;
    }

    public UserProfile convert_userdto_to_userprofile(UserDTO userDTO) {
        if(!Objects.isNull(userDTO))
            return mapper.map(userDTO, UserProfile.class);
        else
            return null;
    }

    public UserDTO convert_userprofile_to_userdto(UserProfile userProfile) {
        if(!Objects.isNull(userProfile))
            return mapper.map(userProfile, UserDTO.class);
        else
            return null;
    }
}
