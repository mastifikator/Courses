import com.fasterxml.jackson.databind.ObjectMapper;
import database.InMemoryUserDatabase;
import database.UserDatabase;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import validators.MessageValidator;
import validators.MsisdnValidator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractEnvironmentSetup {
    protected UserDatabase db;
    protected ObjectMapper objectMapper;
    protected MessageValidator validator;
    protected BlockingQueue<String> successfullyEnrichedMessage;
    protected BlockingQueue<String> unsuccessfullyEnrichedMessage;
    protected EnrichmentService enrichmentService;
    protected Logger LOG;

    @BeforeEach
    public void prepareDatabase(){
        db = new InMemoryUserDatabase();
        db.addUser("0", new User("0", "000"));
        db.addUser("1", new User("1", "111"));
        db.addUser("2", new User("2", "222"));
        db.addUser("3", new User("3", "333"));
        db.addUser("4", new User("4", "444"));
        db.addUser("5", new User("5", "555"));
        db.addUser("6", new User("6", "666"));
        db.addUser("7", new User("7", "777"));
        db.addUser("8", new User("8", "888"));
        db.addUser("9", new User("9", "999"));
        db.addUser("10", new User("10", "100"));

        LOG = LoggerFactory.getLogger(EnrichmentService.class);
        objectMapper = new ObjectMapper();
        validator = new MsisdnValidator(db);
        successfullyEnrichedMessage = new LinkedBlockingQueue<>();
        unsuccessfullyEnrichedMessage = new LinkedBlockingQueue<>();

        enrichmentService = new EnrichmentService(validator,
                db,
                successfullyEnrichedMessage,
                unsuccessfullyEnrichedMessage);
    }

}
