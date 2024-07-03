package com.mvasce.betfair.state;

public interface StateManagerInterface {
    public void setState(String clk, String initialClk);
    public BetfairState getState();
}
