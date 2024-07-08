package com.mvasce.betfair.ingestion.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class FileStateManager implements StateManagerInterface{


    private final String filename;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setState(String clk, String initialClk) {
        BetfairState state = new BetfairState(clk, initialClk);
        try {
            mapper.writeValue(new File(filename), state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BetfairState getState() {
        try {
            return mapper.readValue(new File(filename), BetfairState.class);
        } catch (IOException e) {
            return null;
        }
    }
}
