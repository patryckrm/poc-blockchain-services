package poc.repository;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import poc.contracts.Saude;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigInteger;
import java.util.logging.Logger;

@Singleton
public class SaudeRepository {

    private static final Logger LOGGER = Logger.getLogger(SaudeRepository.class.getName());

    @Inject
    @ConfigProperty(name = "saude.contract.deploy.new")
    String contractDeployNew;

    @Inject
    @ConfigProperty(name = "saude.contract.address")
    String contractAddress;

    @Inject
    @ConfigProperty(name = "saude.contract.gas.price")
    String contractGasPrice;

    @Inject
    @ConfigProperty(name = "saude.contract.gas.limit")
    String contractGasLimit;

    @Inject
    @ConfigProperty(name = "blockchain.address")
    String blockchainAddress;

    @Inject
    @ConfigProperty(name = "blockchain.admin.address")
    String adminAddress;

    @Inject
    @ConfigProperty(name = "blockchain.admin.private.key")
    String adminPrivateKey;

    private String actualAddress;

    @Produces
    public Web3j web3j() {
        return Web3j.build(new HttpService(this.blockchainAddress));
    }

    @Produces
    private TransactionManager txManager(Web3j web3j) {
        return new ClientTransactionManager(web3j, this.adminAddress);
    }



    public Saude getFrom(String privateKey){
        Credentials credentials = Credentials.create(privateKey);
        StaticGasProvider gas = new StaticGasProvider(new BigInteger(this.contractGasPrice), new BigInteger(this.contractGasLimit));
        return Saude.load(this.actualAddress, this.web3j(), credentials, gas);
    }

    public Saude verificaDeployDoContrato() throws Exception {
        LOGGER.info( "Verificando o contrato SAUDE ");

        Web3j web3j = this.web3j();
        Credentials credentials = Credentials.create(this.adminPrivateKey);

        StaticGasProvider gas = new StaticGasProvider(new BigInteger(this.contractGasPrice), new BigInteger(this.contractGasLimit));

        Saude saude = null;
        if (  Boolean.parseBoolean(this.contractDeployNew) ){
            saude = Saude.deploy(web3j, credentials, gas).send();
            LOGGER.info( "Saude implantado no endereço: " + saude.getContractAddress() );
            this.actualAddress = saude.getContractAddress();
        }
        else {
            saude = Saude.load(this.contractAddress, web3j, credentials, gas);
            LOGGER.info( "Saude carregado do endereço: " + saude.getContractAddress() );
            this.actualAddress = saude.getContractAddress();
        }

        return saude;
    }


}
