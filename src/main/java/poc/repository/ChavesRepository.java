package poc.repository;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.web3j.crypto.Credentials;
import poc.service.ChavesService;

import javax.crypto.Cipher;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.HeaderParam;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Logger;

@Singleton
public class ChavesRepository {

    private static final Logger LOGGER = Logger.getLogger(ChavesRepository.class.getName());

    private static final String ALGORITMO = "RSA";
    private static final String ALGORITMO_ASSINATURA = "SHA1withRSA";
    private static final String CHARSET = StandardCharsets.UTF_8.name();
    private static final String NOME_CHAVE_PRIVADA = "key.private";
    private static final String NOME_CHAVE_PUBLICA = "key.pub";

    @Inject
    @ConfigProperty(name = "servico.chaves.dir")
    String diretorioChaves;

    public void verificarChave(String chavePrivadaCarteira) throws Exception {
        this.verificaParametros(chavePrivadaCarteira);
        Credentials c = Credentials.create(chavePrivadaCarteira);
        String diretorio = this.diretorioChaves + File.separator + c.getAddress();
        File f = new File(diretorio);
        if (!f.exists()){
            f.mkdir();
            KeyPair pair = this.salvarEmDiscoNovoKeypair(f);
        }
        else{
            LOGGER.info("Par de Chaves Encontrado");
        }
    }

    public PrivateKey carregarChavePrivada(String chavePrivadaCarteira) throws Exception {
        this.verificaParametros(chavePrivadaCarteira);
        this.verificarChave(chavePrivadaCarteira);

        Credentials c = Credentials.create(chavePrivadaCarteira);
        String diretorio = this.diretorioChaves + File.separator + c.getAddress();
        File f = new File(diretorio + File.separator + NOME_CHAVE_PRIVADA);

        try(FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis) ) {
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            byte[] decoded = Base64.getDecoder().decode(keyBytes);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance(ALGORITMO);
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public PublicKey carregarChavePublica(String chavePrivadaCarteira) throws Exception {
        this.verificaParametros(chavePrivadaCarteira);
        this.verificarChave(chavePrivadaCarteira);
        Credentials c = Credentials.create(chavePrivadaCarteira);
        String diretorio = this.diretorioChaves + File.separator + c.getAddress();
        File f = new File(diretorio + File.separator + NOME_CHAVE_PUBLICA);

        try(FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis) ) {
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            byte[] decoded = Base64.getDecoder().decode(keyBytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance(ALGORITMO);
            return kf.generatePublic(spec);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public PublicKey carregarChavePublicaDoEndereco(String endereco) throws Exception {
        String diretorio = this.diretorioChaves + File.separator + endereco;
        File f = new File(diretorio + File.separator + NOME_CHAVE_PUBLICA);

        try(FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis) ) {
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            byte[] decoded = Base64.getDecoder().decode(keyBytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance(ALGORITMO);
            return kf.generatePublic(spec);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public String assinar(String chavePrivadaCarteira, String message) throws SignatureException{
        try {
            PrivateKey privateKey = this.carregarChavePrivada(chavePrivadaCarteira);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SignatureException(ex);
        }
    }

    public String ler(String chavePrivadaCarteira, String message) throws SignatureException{
        try {
            PublicKey publicKey = this.carregarChavePublica(chavePrivadaCarteira);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(message));
            return new String(bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SignatureException(ex);
        }
    }

    public String lerDoEndereco(String endereco, String message) throws SignatureException{
        try {
            PublicKey publicKey = this.carregarChavePublicaDoEndereco(endereco);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(message));
            return new String(bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SignatureException(ex);
        }
    }

    private KeyPair salvarEmDiscoNovoKeypair(File diretorio) throws NoSuchAlgorithmException {
        KeyPair pair = this.criarNovoKeyPair();
        this.salvarEmDisco(diretorio, NOME_CHAVE_PUBLICA, pair.getPublic().getEncoded() );
        this.salvarEmDisco(diretorio, NOME_CHAVE_PRIVADA, pair.getPrivate().getEncoded() );
        return  pair;
    }

    private KeyPair criarNovoKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITMO);
        keyPairGen.initialize(2048);
        LOGGER.info("Novo Par de Chaves Gerado");
        return keyPairGen.generateKeyPair();
    }

    private void salvarEmDisco(File diretorio, String fileName, byte [] bytes ){
        String file = diretorio.getAbsolutePath() + File.separator + fileName;
        try(FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos)) {
            String base64 = Base64.getEncoder().encodeToString(bytes);
            dos.writeBytes(base64);
            dos.flush();
            LOGGER.info("Arquivo Salvo: " + file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void verificaParametros(String chavePrivadaCarteira) throws Exception {
        if (chavePrivadaCarteira == null || chavePrivadaCarteira.trim().length() < 1){
            throw new Exception("Chave privada da carteira é obrigatória");
        }
        if ( this.diretorioChaves == null || chavePrivadaCarteira.trim().length() < 1){
            throw new Exception("Diretório de chaves é obrigatório na config");
        }
        else{
            File f = new File(this.diretorioChaves);
            if (!f.exists() || !f.isDirectory()){
                throw new Exception("Diretório de chaves não existe: " + this.diretorioChaves);
            }
        }
    }

}
