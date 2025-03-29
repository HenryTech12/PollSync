package com.votingapi.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class PollDTO {

    @NotBlank
    @NotNull
    private String polltype;
    @NotBlank
    @NotNull
    private String question;
    @NotNull
    private PollOption option;
    private Integer endDay;
    private Integer endTime;
    private boolean anonymous;
    private boolean multipleAnswer;
    private String voteId;
    private boolean voted;
    private LocalDate createdDate;
    private LocalTime createdTime;

    public String getPolltype() {
        return polltype;
    }

    public void setPolltype(String polltype) {
        this.polltype = polltype;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public PollOption getOption() {
        return option;
    }

    public void setOption(PollOption option) {
        this.option = option;
    }

    public Integer getEndDay() {
        return endDay;
    }

        public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isMultipleAnswer() {
        return multipleAnswer;
    }

    public void setMultipleAnswer(boolean multipleAnswer) {
        this.multipleAnswer = multipleAnswer;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "PollDTO{" +
                "polltype='" + polltype + '\'' +
                ", question='" + question + '\'' +
                ", option=" + option +
                ", endDay='" + endDay + '\'' +
                ", endTime='" + endTime + '\'' +
                ", anonymous=" + anonymous +
                ", multipleAnswer=" + multipleAnswer +
                '}';
    }
}
