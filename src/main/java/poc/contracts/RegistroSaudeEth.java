package poc.contracts;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

public class RegistroSaudeEth extends DynamicStruct {
    public BigInteger id;

    public String descricao;

    public String dono;

    public BigInteger dataPublicacao;

    public RegistroSaudeEth(BigInteger id, String descricao, String dono, BigInteger dataPublicacao) {
        super(new org.web3j.abi.datatypes.generated.Uint256(id),
                new org.web3j.abi.datatypes.Utf8String(descricao),
                new org.web3j.abi.datatypes.Address(160, dono),
                new org.web3j.abi.datatypes.generated.Uint256(dataPublicacao));
        this.id = id;
        this.descricao = descricao;
        this.dono = dono;
        this.dataPublicacao = dataPublicacao;
    }

    public RegistroSaudeEth(Uint256 id, Utf8String descricao, Address dono, Uint256 dataPublicacao) {
        super(id, descricao, dono, dataPublicacao);
        this.id = id.getValue();
        this.descricao = descricao.getValue();
        this.dono = dono.getValue();
        this.dataPublicacao = dataPublicacao.getValue();
    }
}
