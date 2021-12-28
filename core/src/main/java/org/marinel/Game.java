package org.marinel;

public interface Game {

    // == add the methods ==
    String getUserName();
    // == set method for getUserName() ==
    void setUserName(String userName);

    int getNumber();

    int getGuess();

    // == set method for getGuess() ==
    void setGuess(int guess);

    int getSmallest();

    int getBiggest();

    int getRemainingGuesses();

    int getGuessCount();

    void reset();

    void check();

    boolean isValidNumberRange();

    boolean isGameWon();

    boolean isGameLost();
}
