package org.marinel.service;

public interface GameService {

    // == fields ==

    void getTheUserName(String userName);

    boolean isGameOver();

    String getMainMessage();

    String getResultMessage();

    void checkGuess(int guess);

    void reset();
}
