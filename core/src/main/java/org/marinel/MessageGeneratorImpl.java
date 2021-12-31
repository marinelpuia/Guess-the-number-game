package org.marinel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MessageGeneratorImpl implements MessageGenerator {

    // == constants ==
    public static final String MAIN_MESSAGE = "game.main.message";
    // == fields ==
    private final Game game;
    // == use to inject message in the user interface
    private final MessageSource messageSource;

    // == constructor ==
    @Autowired
    public MessageGeneratorImpl(Game game, MessageSource messageSource) {
        this.game = game;
        this.messageSource = messageSource;
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
        return getMessage(MAIN_MESSAGE, game.getSmallest(), game.getBiggest());
//        return game.getUserName()  + ", number is between " + game.getSmallest() +
//                " and " + game.getBiggest() + ". Can you guess it?";
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

    // == get the message from the messages.properties file ==
    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
