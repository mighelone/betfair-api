package com.mvasce.betfair.ingestion.state;

public interface StateManagerInterface {
    public void setState(String clk, String initialClk);
    public BetfairState getState();
}
