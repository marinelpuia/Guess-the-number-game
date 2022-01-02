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
    public static final String RESULT_GAME_WON = "game.won";
    public static final String RESULT_GAME_LOSE = "game.lose";
    public static final String RESULT_NUMBER_RANGE= "game.invalid.range";
    public static final String RESULT_FIRST_GUESS= "game.first.guess";
    public static final String RESULT_LOWER = "game.lower";
    public static final String RESULT_HIGHER= "game.higher";
    public static final String RESULT_GO = "game.go";
    public static final String RESULT_YOU_HAVE= "game.you.have";
    public static final String RESULT_GUESSES_LEFT= "game.guesses.left";

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
    }

    @Override
    public String getResultMessage() {

        if (game.isGameWon()) {
            return game.getUserName() + getMessage(RESULT_GAME_WON, game.getNumber()) ;
        } else if (game.isGameLost()) {
            return game.getUserName() + getMessage(RESULT_GAME_LOSE, game.getNumber());
        } else if (!game.isValidNumberRange()) {
            return game.getUserName() + getMessage(RESULT_NUMBER_RANGE, game.isValidNumberRange());
        } else if (game.getRemainingGuesses() == game.getGuessCount()) {
            return game.getUserName() + getMessage(RESULT_FIRST_GUESS);
        } else {
            String direction = getMessage(RESULT_LOWER);

            if (game.getGuess() < game.getNumber()) {
                direction = getMessage(RESULT_HIGHER);
            }
            return game.getUserName() + getMessage(RESULT_GO) + direction +
                    getMessage(RESULT_YOU_HAVE) + game.getRemainingGuesses() + getMessage(RESULT_GUESSES_LEFT);
        }
    }

    // == get the message from the messages.properties file ==
    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
