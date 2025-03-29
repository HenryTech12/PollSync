package com.votingapi.demo.dto;

import jakarta.persistence.Embeddable;

import java.util.List;
@Embeddable
public class PollOption {
    private List<String> options;
    private List<Integer> results;

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getResults() {
        return results;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }
}
