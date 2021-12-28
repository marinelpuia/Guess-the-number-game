package org.marinel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// == annotate the component ==
@Slf4j
@Getter
@Component
public class GameImpl implements Game {

    // == fields ==
    @Getter(AccessLevel.NONE)
    private final NumberGenerator numberGenerator;


    private final int guessCount; // max 10 try and then the game stop
    private int number; // save the number generated for the player to guess
    private int smallest; // the smallest number 0
    private int biggest; // the biggest number 100 in this game
    private int remainingGuesses; // the number of remaining guesses
    private boolean validNumberRange = true;

    // == the player guess the number entered by the player ==
    @Setter
    private int guess;

    @Setter
    private String userName;

    // == constructor ==
    @Autowired
    public GameImpl(NumberGenerator numberGenerator, @GuessCount int guessCount) {
        this.numberGenerator = numberGenerator;
        this.guessCount = guessCount;
    }

    // == init method ==
    @PostConstruct
    @Override
    public void reset() {
        smallest = numberGenerator.getMinNumber();
        guess = numberGenerator.getMinNumber();
        remainingGuesses = guessCount;
        biggest = numberGenerator.getMaxNumber();
        number = numberGenerator.next();
        //log.debug("Method reset() was called and the new game number is {}", number);
    }

    // pre destroy method
    @PreDestroy
    public void preDestroy() {
        log.info("in Game preDestroy()");
    }

    // == public methods ==

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void check() {
        checkValidNumberRange();

        if (validNumberRange) {
            if (guess > number) {
                biggest = guess - 1;
            }
            if (guess < number) {
                smallest = guess + 1;
            }
        }

        remainingGuesses--;
    }

    @Override
    public boolean isGameWon() {
        return guess == number;
    }

    @Override
    public boolean isGameLost() {
        return !isGameWon() && remainingGuesses <= 0;
    }

    // == private method ==
    private void checkValidNumberRange() {
        validNumberRange = (guess >= smallest) && (guess <= biggest);
    }
}
