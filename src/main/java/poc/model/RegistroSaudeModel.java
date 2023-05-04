package poc.model;

import org.web3j.tuples.generated.Tuple4;

import java.math.BigInteger;
import java.util.Date;

public class RegistroSaudeModel {

    private Integer id;
    private String dono;
    private Date dataPublicacao;


    private String nome;
    private String sobrenome;
    private String email;
    private String endereco;
    private String registro;

    public static RegistroSaudeModel from(Tuple4<BigInteger, String, String, BigInteger> rs){
        RegistroSaudeModel novo = new RegistroSaudeModel();
        novo.setId(rs.component1().intValue());
        novo.setDono(rs.component3());
        novo.setDataPublicacao( new Date(rs.component4().longValue() * 1000) );
        return novo;
    }

    public RegistroSaudeModel setData(RegistroSaudeModel decripted){
        this.nome = decripted.nome;
        this.sobrenome = decripted.sobrenome;
        this.endereco = decripted.endereco;
        this.email = decripted.email;
        this.registro = decripted.registro;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}
