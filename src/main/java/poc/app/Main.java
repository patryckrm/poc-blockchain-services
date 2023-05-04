package poc.app;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import poc.contracts.Saude;
import poc.repository.SaudeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Inject
    SaudeRepository saudeRepository;

    void onStart(@Observes StartupEvent ev) throws Exception {
        Saude saude = this.saudeRepository.verificaDeployDoContrato();
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info(" parando...");
    }
}
