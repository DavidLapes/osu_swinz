package com.djenterprise.app.game;

import com.djenterprise.db.game.GameDAO;
import com.djenterprise.db.game.QuestionDAO;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class GameCycle {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(GameCycle.class.getName());

    //TODO ( + JavaDoc, comments )
    static public void constructGame( String creator ) {
        GameBO gameBO = new GameBO();
        gameBO.setGameId(
                gameBO.generateId()
        );
        gameBO.setCreator(creator);

        List<QuestionBO> questions = QuestionDAO.getAllQuestions();
        List<QuestionBO> gameQuestions = new ArrayList<>();

        Properties properties = new Properties();
        FileInputStream inputStream;
        // Initialize Path to property file for DB CONNECTION
        Path path;
        // Is this Windows?
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            path = Paths.get("src\\com\\djenterprise\\config\\game_config.properties");
            LOGGER.info("Reading file " + path.toString());
        }
        // It is not Windows
        else {
            path = Paths.get("src//com//djenterprise//config//game_config.properties");
            LOGGER.info("Reading file " + path.toString());
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

        Collections.shuffle(questions);
        String questionCount = properties.getProperty("question_count");
        for( int i = 0; i < Integer.parseInt(questionCount); i ++ ) {
            gameQuestions.add(
                    questions.get(i)
            );
        }

        GameDAO.createGame(gameBO, gameQuestions);
    }
}