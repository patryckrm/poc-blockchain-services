package poc.service;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import poc.contracts.Saude;
import poc.repository.ChavesRepository;
import poc.repository.SaudeRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Logger;

@Path("/chaves")
public class ChavesService {


    private static final Logger LOGGER = Logger.getLogger(ChavesService.class.getName());

    @Inject
    ChavesRepository chavesRepository;

    @Inject
    SaudeRepository saudeRepository;

    @GET
    @Path("/verificar")
    @Produces(MediaType.TEXT_PLAIN)
    public void verificarChave(@HeaderParam("private-key")String privateKey) throws Exception {
        this.chavesRepository.verificarChave(privateKey);
    }

    @POST
    @Path("/acesso")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String liberarAcesso(@HeaderParam("private-key")String privateKey, String carteira) throws Exception {
        Saude saude = saudeRepository.getFrom(privateKey);
        LOGGER.info( String.format("liberar acesso em [%s] ", saude.getContractAddress()));
        LOGGER.info( String.format("acesso de [%s] para [%s] ", privateKey, carteira));
        TransactionReceipt receipt = saude.liberarAcesso(carteira).send();
        return receipt.getBlockHash();
    }

    @DELETE
    @Path("/acesso")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String revogarAcesso(@HeaderParam("private-key")String privateKey, String carteira) throws Exception {
        Saude saude = saudeRepository.getFrom(privateKey);
        LOGGER.info( String.format("revogar acesso em [%s] ", saude.getContractAddress()));
        LOGGER.info( String.format("acesso de [%s] para [%s] ", privateKey, carteira));
        TransactionReceipt receipt = saude.revogarAcesso(carteira).send();
        return receipt.getBlockHash();
    }

}
