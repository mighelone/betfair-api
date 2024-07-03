package com.mvasce.betfair.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.DataInput;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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
