package com.djenterprise.app.game;

import com.djenterprise.db.game.GameDAO;
import com.djenterprise.db.game.QuestionDAO;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class GameConstruct {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(GameConstruct.class.getName());

    /**
     * Creates a game and configures needed attributes like adding questions.
     * @param creator user who created the game
     * @param playerOne first player
     * @param playerTwo second player
     */
    static public void constructGame( String creator, String playerOne, String playerTwo ) {
        //Game to be created
        GameBO gameBO = new GameBO();
        //Set randomly-generated ID to this game
        gameBO.setGameId(
                gameBO.generateId()
        );
        //Set user to this game who created it
        gameBO.setCreator(creator);
        //Set first player
        gameBO.setPlayerOne(playerOne);
        //Set second player
        gameBO.setPlayerTwo(playerTwo);
        //Get all possible questions
        List<QuestionBO> questions = QuestionDAO.getAllQuestions();
        //Prepare list for questions which will be selected in upcoming step
        List<QuestionBO> gameQuestions = new ArrayList<>();
        //Shuffle those questions so we don't get same questions
        Collections.shuffle(questions);
        //Obtain a number of how many questions game should have
        int questionCount = getQuestionCount();
        //Go through those questions
        for( int i = 0; i < questionCount; i ++ ) {
            //Add questions to the game
            gameQuestions.add(
                    questions.get(i)
            );
        }
        //Construct instance in DB
        GameDAO.createGame(gameBO, gameQuestions);
    }

    /**
     * Creates a game and configures needed attributes.
     * @param creator user who created the game
     * @param gameQuestions questions to be added to quiz
     * @param playerOne first player
     * @param playerTwo second player
     */
    static public void constructGame( String creator, List<QuestionBO> gameQuestions, String playerOne, String playerTwo ) {
        //Game to be created
        GameBO gameBO = new GameBO();
        //Set randomly-generated ID to this game
        gameBO.setGameId(
                gameBO.generateId()
        );
        gameBO.setPlayerOne(playerOne);
        gameBO.setPlayerTwo(playerTwo);
        //Set user to this game who created it
        gameBO.setCreator(creator);
        //Construct instance in DB
        GameDAO.createGame(gameBO, gameQuestions);
    }

    /**
     * Returns number how many questions should game have according to property file
     * @return int number of questions
     */
    static private int getQuestionCount() {
        Properties properties = new Properties();
        FileInputStream inputStream;
        // Initialize Path to property file for DB CONNECTION
        Path path;
        try {
            // Is this Windows?
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                path = Paths.get(GameConstruct.class.getResource("game_config.properties").toURI());
                LOGGER.info("Reading file " + path.toString());
            }
            // It is not Windows
            else {
                path = Paths.get(GameConstruct.class.getResource("game_config.properties").toURI());
                LOGGER.info("Reading file " + path.toString());
            }
        } catch (URISyntaxException ex ) {
            throw new RuntimeException(ex);
        }

        // Get the File by specified Path
        File file = new File(path.toUri());

        try {
            LOGGER.info("Opening file stream");
            // Create file stream between property file and application
            inputStream = new FileInputStream(file);
            // Load property file
            LOGGER.info("Loading game configuration");
            properties.load(inputStream);
            // Close stream
            LOGGER.info("Closing file stream");
            inputStream.close();
        } catch ( FileNotFoundException FNFEx ) {
            // Specified file was not found
            LOGGER.error(FNFEx);
            throw new RuntimeException("No such file has been found '" + path + "'");
        } catch ( IOException IOEx ) {
            // Whops! Error while working with file or closing the stream
            LOGGER.error(IOEx);
            throw new RuntimeException("An error has occurred during reading the file '" + path + "'or closing FileInputStream");
        }

        String ret = properties.getProperty("question_count");

        return Integer.parseInt(ret);
    }
}