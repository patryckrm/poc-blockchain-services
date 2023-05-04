package poc.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import poc.contracts.Saude;
import poc.model.LiberacaoModel;
import poc.model.RegistroSaudeModel;
import poc.repository.ChavesRepository;
import poc.repository.SaudeRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/saude")
public class SaudeService {

    private static final Logger LOGGER = Logger.getLogger(SaudeService.class.getName());

    @Inject
    SaudeRepository saudeRepository;

    @Inject
    ChavesRepository chavesRepository;

    @GET
    @Path("/liberacoes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LiberacaoModel> consultarLiberacoes(@HeaderParam("private-key")String privateKey) throws Exception {

        List<Tuple2<String,String>> tuplas = new ArrayList<>();

        Saude saude = saudeRepository.getFrom(privateKey);
        LOGGER.info( String.format("consultar total de liberacoes em [%s] ", saude.getContractAddress()));
        BigInteger total = saude.consultarTotalDeLiberacoes().send();
        if (total.intValue() > 0){
            for (int i=0; i< total.intValue(); i++){
                tuplas.add( saude.consultarLiberacao(BigInteger.valueOf(i)).send() );
            }
        }

        List<LiberacaoModel> liberacoes = new ArrayList<>();

        Credentials credentials = Credentials.create(privateKey);
        //varre as tuplas, e retorna somente o da chave atual
        for( Tuple2<String,String> t: tuplas ){
            LOGGER.info( String.format("Liberação de [%s] para [%s] ", t.component1(), t.component2()));

            if ( t.component1().equalsIgnoreCase(credentials.getAddress()) ){
                LiberacaoModel liberacao = new LiberacaoModel();
                liberacao.setDono(t.component1());
                liberacao.setCarteira(t.component2());
                liberacoes.add(liberacao);
            }
        }

        return liberacoes;
    }

    @GET
    @Path("/liberacoes/todas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tuple2<String,String>> consultarTodasLiberacoes(@HeaderParam("private-key")String privateKey) throws Exception {
        Saude saude = saudeRepository.getFrom(privateKey);
        LOGGER.info( String.format("consultar total de liberacoes em [%s] ", saude.getContractAddress()));
        BigInteger total = saude.consultarTotalDeLiberacoes().send();
        if (total.intValue() > 0){
            List<Tuple2<String,String>> tuplas = new ArrayList<>();
            for (int i=0; i< total.intValue(); i++){
                tuplas.add( saude.consultarLiberacao(BigInteger.valueOf(i)).send() );
            }
            return tuplas;
        }
        return null;
    }

    @GET
    @Path("/total-registros")
    @Produces(MediaType.TEXT_PLAIN)
    public BigInteger consultarTotalDeRegistros(@HeaderParam("private-key")String privateKey) throws Exception {
        Saude saude = saudeRepository.getFrom(privateKey);
        LOGGER.info( String.format("consultar total de registros em [%s] ", saude.getContractAddress()));
        return saude.consultarTotalDeRegistros().send();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String novoRegistro(@HeaderParam("private-key")String privateKey, RegistroSaudeModel registroSaudeModel) throws Exception {

        Saude saude = saudeRepository.getFrom(privateKey);
        LOGGER.info( String.format("novo registro a ser inserido em [%s] ", saude.getContractAddress()));

        //json
        String dados = new ObjectMapper().writeValueAsString(registroSaudeModel);

        //criptografa com a chave privada
        String assinado = this.chavesRepository.assinar(privateKey,dados);
        LOGGER.info( String.format("Registro [%s] ", dados));
        LOGGER.info( String.format("Registro criptografado [%s] ", assinado));

        TransactionReceipt receipt = saude.recebeRegistroSaude( assinado )
                                            .send();

        return receipt.getBlockHash();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RegistroSaudeModel> consultarRegistros(@HeaderParam("private-key")String privateKey) throws Exception {
        Saude saude = saudeRepository.getFrom(privateKey);
        LOGGER.info( String.format("consultar os registros em [%s] ", saude.getContractAddress()));

        List<Tuple4<BigInteger, String, String, BigInteger>> registros = new ArrayList<>();
        BigInteger total = saude.consultarTotalDeRegistros().send();
        for (int i=0; i< total.intValue(); i++){
            registros.add( saude.consultar(BigInteger.valueOf(i)).send() );
        }

        List<RegistroSaudeModel> lista = new ArrayList<>();
        registros.stream().forEach( r ->{
            RegistroSaudeModel registro = RegistroSaudeModel.from(r);
            lista.add(registro);

            //tenta descriptografar com a chave do usuario atual
            RegistroSaudeModel descriptografado = this.tentaDescriptografarRegistro(privateKey, r.component3(), r.component2());
            if (descriptografado != null) {
                registro.setData(descriptografado);
            }
        });

        return lista;
    }

    public RegistroSaudeModel tentaDescriptografarRegistro(String chavePrivada, String dono, String conteudo ){
        try{
            Credentials credentials = Credentials.create(chavePrivada);
            if (credentials.getAddress().equalsIgnoreCase(dono)) {
                LOGGER.info(String.format("Consulta pelo dono do registro [%s] ", dono));
                LOGGER.info(String.format("Registro criptografado [%s] ", conteudo));
                String descStr = chavesRepository.ler(chavePrivada, conteudo);

                LOGGER.info(String.format("Registro criptografado [%s] ", conteudo));
                LOGGER.info(String.format("Registro descriptografado [%s] ", descStr));
                RegistroSaudeModel descriptografado = new ObjectMapper().readValue(descStr, RegistroSaudeModel.class);
                return descriptografado;
            }
            else{
                //verifica se esta liberado
                LOGGER.info(String.format("Verificando se [%s] foi liberado por [%s]", credentials.getAddress(), dono));
                Saude saude = saudeRepository.getFrom(chavePrivada);
                BigInteger indice = saude.verificaLiberacao(dono)
                        .send();
                LOGGER.info(String.format("Liberado? [%s]", indice.toString()));
                if (indice.intValue() > -1){
                    //descriptografa (com a chave publica do dono)
                    LOGGER.info(String.format("Buscando a chave publica de [%s] ", dono));
                    String descStr = chavesRepository.lerDoEndereco(dono, conteudo);
                    LOGGER.info(String.format("Registro criptografado [%s] ", conteudo));
                    LOGGER.info(String.format("Registro descriptografado [%s] ", descStr));
                    RegistroSaudeModel descriptografado = new ObjectMapper().readValue(descStr, RegistroSaudeModel.class);
                    return descriptografado;
                }
                return null;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            LOGGER.severe(ex.getMessage());
        }
        return null;
    }

}
