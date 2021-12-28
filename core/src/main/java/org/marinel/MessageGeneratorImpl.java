package org.marinel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MessageGeneratorImpl implements MessageGenerator {

    // == fields ==
    private final Game game;

    // == constructor ==
    @Autowired
    public MessageGeneratorImpl(Game game) {
        this.game = game;
    }

    // == init method ==
    @PostConstruct
    public void init() {
        log.info("game = {}", game);
    }

    // == public methods ==

    @Override
    public String getTheUserName() {
        return "Welcome " + game.getUserName();
    }

    @Override
    public String getMainMessage() {
        return game.getUserName()  + ", number is between " + game.getSmallest() + " and " + game.getBiggest() + ". Can you guess it?";
    }

    @Override
    public String getResultMessage() {

        if (game.isGameWon()) {
            return game.getUserName() + ", you guessed it! The number was " + game.getNumber();
        } else if (game.isGameLost()) {
            return game.getUserName() + ", you lost. The number was " + game.getNumber();
        } else if (!game.isValidNumberRange()) {
            return game.getUserName() + ", it's invalid number range!";
        } else if (game.getRemainingGuesses() == game.getGuessCount()) {
            return game.getUserName() + ", what is your first guess?";
        } else {
            String direction = "Lower";

            if (game.getGuess() < game.getNumber()) {
                direction = "Higher";
            }
            return game.getUserName() + ", go " + direction + "! You have " + game.getRemainingGuesses() + " guesses left";
        }
    }
}
