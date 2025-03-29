package com.votingapi.demo.mapper;

import com.votingapi.demo.dto.PollDTO;
import com.votingapi.demo.model.PollData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PollMapper {

    @Autowired
    private ModelMapper mapper;

    public PollData convert_polldto_to_polldata(PollDTO pollDTO) {
        if(!Objects.isNull(pollDTO))
            return mapper.map(pollDTO, PollData.class);
        else
            return null;
    }

    public PollDTO convert_polldata_to_polldto(PollData pollData) {
        if(!Objects.isNull(pollData))
            return mapper.map(pollData, PollDTO.class);
        else
            return null;
    }
}
