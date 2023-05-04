package poc.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.4.
 */
@SuppressWarnings("rawtypes")
public class Saude extends Contract {
    public static final String BINARY = "6080604052600080546001600160a01b0319163317815560015534801561002557600080fd5b50610deb806100356000396000f3fe608060405234801561001057600080fd5b50600436106100ce5760003560e01c806361b93eb61161008c578063c4ad185e11610066578063c4ad185e1461026b578063d1500cc214610273578063ded39a5d14610293578063e073d088146102a657600080fd5b806361b93eb614610187578063b10b65ce1461019a578063b54afcbe146101bd57600080fd5b80629147d4146100d357806309e963771461010b5780630c83e34c146101215780632319aa891461014c5780635c07828d146101615780635f64ce1f14610174575b600080fd5b6100e66100e1366004610b23565b6102b9565b604080516001600160a01b039384168152929091166020830152015b60405180910390f35b6101136102f2565b604051908152602001610102565b61013461012f366004610b23565b61030a565b6040516001600160a01b039091168152602001610102565b61015f61015a366004610b52565b610334565b005b61015f61016f366004610c03565b61042f565b610113610182366004610c03565b6105cc565b6100e6610195366004610b23565b6106aa565b6101ad6101a8366004610b23565b610702565b6040516101029493929190610c80565b61015f6101cb366004610c03565b604080518082019091523381526001600160a01b039182166020820190815260048054600181018255600091909152915160029092027f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b810180549385166001600160a01b031994851617905590517f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19c9091018054919093169116179055565b610113610803565b610286610281366004610b23565b610815565b6040516101029190610cb5565b6101ad6102a1366004610b23565b610940565b61015f6102b4366004610c03565b610a0e565b600481815481106102c957600080fd5b6000918252602090912060029091020180546001909101546001600160a01b0391821692501682565b60045460009015610304575060045490565b50600090565b6003818154811061031a57600080fd5b6000918252602090912001546001600160a01b0316905081565b6103686040518060800160405280600081526020016060815260200160006001600160a01b03168152602001600081525090565b610370610a70565b815260208082018381524260608401523360408401526002805460018101825560009190915283517f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace60049092029182019081559151805185946103f8937f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5acf01920190610a8a565b5060408201516002820180546001600160a01b0319166001600160a01b039092169190911790556060909101516003909101555050565b604080518082019091523381526001600160a01b03821660208201526000196000805b6004548110156104ee5760006004828154811061047157610471610d06565b60009182526020918290206040805180820190915260029092020180546001600160a01b0390811680845260019092015416928201929092529150331480156104cf5750856001600160a01b031681602001516001600160a01b0316145b156104db578193508192505b50806104e681610d32565b915050610452565b506000198213156105c6576004805461050990600190610d4d565b8154811061051957610519610d06565b90600052602060002090600202016004828154811061053a5761053a610d06565b60009182526020909120825460029092020180546001600160a01b039283166001600160a01b0319918216178255600193840154939091018054939092169216919091179055600480548061059157610591610d64565b60008281526020902060026000199092019182020180546001600160a01b031990811682556001919091018054909116905590555b50505050565b60006105e8604080518082019091526000808252602082015290565b6001600160a01b038316815233602082015260001960005b6004548110156106a25760006004828154811061061f5761061f610d06565b60009182526020918290206040805180820190915260029092020180546001600160a01b0390811680845260019092015481169383019390935286519193509116148015610686575083602001516001600160a01b031681602001516001600160a01b0316145b1561068f578192505b508061069a81610d32565b915050610600565b509392505050565b600080600083600480549050106106fc57600484815481106106ce576106ce610d06565b6000918252602090912060029091020180546001909101546001600160a01b03918216969116945092505050565b50915091565b60006060600080600085600280549050106107fa576002868154811061072a5761072a610d06565b906000526020600020906004020190508060000154816001018260020160009054906101000a90046001600160a01b0316836003015482805461076c90610d7a565b80601f016020809104026020016040519081016040528092919081815260200182805461079890610d7a565b80156107e55780601f106107ba576101008083540402835291602001916107e5565b820191906000526020600020905b8154815290600101906020018083116107c857829003601f168201915b505050505092509450945094509450506107fc565b505b9193509193565b60025460009015610304575060025490565b6108496040518060800160405280600081526020016060815260200160006001600160a01b03168152602001600081525090565b600254821161093b576002828154811061086557610865610d06565b90600052602060002090600402016040518060800160405290816000820154815260200160018201805461089890610d7a565b80601f01602080910402602001604051908101604052809291908181526020018280546108c490610d7a565b80156109115780601f106108e657610100808354040283529160200191610911565b820191906000526020600020905b8154815290600101906020018083116108f457829003601f168201915b505050918352505060028201546001600160a01b0316602082015260039091015460409091015290505b919050565b6002818154811061095057600080fd5b6000918252602090912060049091020180546001820180549193509061097590610d7a565b80601f01602080910402602001604051908101604052809291908181526020018280546109a190610d7a565b80156109ee5780601f106109c3576101008083540402835291602001916109ee565b820191906000526020600020905b8154815290600101906020018083116109d157829003601f168201915b50505050600283015460039093015491926001600160a01b031691905084565b6000546001600160a01b0316331415610a6d57600380546001810182556000919091527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b0180546001600160a01b0319166001600160a01b0383161790555b50565b6001805460009182610a8183610d32565b91905055905090565b828054610a9690610d7a565b90600052602060002090601f016020900481019282610ab85760008555610afe565b82601f10610ad157805160ff1916838001178555610afe565b82800160010185558215610afe579182015b82811115610afe578251825591602001919060010190610ae3565b50610b0a929150610b0e565b5090565b5b80821115610b0a5760008155600101610b0f565b600060208284031215610b3557600080fd5b5035919050565b634e487b7160e01b600052604160045260246000fd5b600060208284031215610b6457600080fd5b813567ffffffffffffffff80821115610b7c57600080fd5b818401915084601f830112610b9057600080fd5b813581811115610ba257610ba2610b3c565b604051601f8201601f19908116603f01168101908382118183101715610bca57610bca610b3c565b81604052828152876020848701011115610be357600080fd5b826020860160208301376000928101602001929092525095945050505050565b600060208284031215610c1557600080fd5b81356001600160a01b0381168114610c2c57600080fd5b9392505050565b6000815180845260005b81811015610c5957602081850181015186830182015201610c3d565b81811115610c6b576000602083870101525b50601f01601f19169290920160200192915050565b848152608060208201526000610c996080830186610c33565b6001600160a01b03949094166040830152506060015292915050565b60208152815160208201526000602083015160806040840152610cdb60a0840182610c33565b60408501516001600160a01b0316606085810191909152909401516080909301929092525090919050565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b6000600019821415610d4657610d46610d1c565b5060010190565b600082821015610d5f57610d5f610d1c565b500390565b634e487b7160e01b600052603160045260246000fd5b600181811c90821680610d8e57607f821691505b60208210811415610daf57634e487b7160e01b600052602260045260246000fd5b5091905056fea2646970667358221220dd8ff9fe18fc4332756314e50f824beb777a88049a0369cb14b9b4277031c6bd64736f6c63430008090033";

    public static final String FUNC_ADICIONACARTEIRA = "adicionaCarteira";

    public static final String FUNC_CONSULTAR = "consultar";

    public static final String FUNC_CONSULTARLIBERACAO = "consultarLiberacao";

    public static final String FUNC_CONSULTARREGISTROSAUDE = "consultarRegistroSaude";

    public static final String FUNC_CONSULTARTOTALDELIBERACOES = "consultarTotalDeLiberacoes";

    public static final String FUNC_CONSULTARTOTALDEREGISTROS = "consultarTotalDeRegistros";

    public static final String FUNC_LIBERACOES = "liberacoes";

    public static final String FUNC_LIBERARACESSO = "liberarAcesso";

    public static final String FUNC_PERMITIDOS = "permitidos";

    public static final String FUNC_RECEBEREGISTROSAUDE = "recebeRegistroSaude";

    public static final String FUNC_REGISTROS = "registros";

    public static final String FUNC_REVOGARACESSO = "revogarAcesso";

    public static final String FUNC_VERIFICALIBERACAO = "verificaLiberacao";

    @Deprecated
    protected Saude(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Saude(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Saude(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Saude(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> adicionaCarteira(String carteira) {
        final Function function = new Function(
                FUNC_ADICIONACARTEIRA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, carteira)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, String, String, BigInteger>> consultar(BigInteger index) {
        final Function function = new Function(FUNC_CONSULTAR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, String, String, BigInteger>>(function,
                new Callable<Tuple4<BigInteger, String, String, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, String, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<String, String>> consultarLiberacao(BigInteger index) {
        final Function function = new Function(FUNC_CONSULTARLIBERACAO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple2<String, String>>(function,
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<RegistroSaude> consultarRegistroSaude(BigInteger index) {
        final Function function = new Function(FUNC_CONSULTARREGISTROSAUDE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<RegistroSaude>() {}));
        return executeRemoteCallSingleValueReturn(function, RegistroSaude.class);
    }

    public RemoteFunctionCall<BigInteger> consultarTotalDeLiberacoes() {
        final Function function = new Function(FUNC_CONSULTARTOTALDELIBERACOES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> consultarTotalDeRegistros() {
        final Function function = new Function(FUNC_CONSULTARTOTALDEREGISTROS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple2<String, String>> liberacoes(BigInteger param0) {
        final Function function = new Function(FUNC_LIBERACOES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple2<String, String>>(function,
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> liberarAcesso(String carteira) {
        final Function function = new Function(
                FUNC_LIBERARACESSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, carteira)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> permitidos(BigInteger param0) {
        final Function function = new Function(FUNC_PERMITIDOS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> recebeRegistroSaude(String descricao) {
        final Function function = new Function(
                FUNC_RECEBEREGISTROSAUDE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(descricao)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, String, String, BigInteger>> registros(BigInteger param0) {
        final Function function = new Function(FUNC_REGISTROS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, String, String, BigInteger>>(function,
                new Callable<Tuple4<BigInteger, String, String, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, String, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> revogarAcesso(String carteira) {
        final Function function = new Function(
                FUNC_REVOGARACESSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, carteira)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> verificaLiberacao(String paciente) {
        final Function function = new Function(FUNC_VERIFICALIBERACAO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, paciente)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Saude load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Saude(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Saude load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Saude(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Saude load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Saude(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Saude load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Saude(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Saude> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Saude.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Saude> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Saude.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Saude> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Saude.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Saude> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Saude.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class RegistroSaude extends DynamicStruct {
        public BigInteger id;

        public String descricao;

        public String dono;

        public BigInteger dataPublicacao;

        public RegistroSaude(BigInteger id, String descricao, String dono, BigInteger dataPublicacao) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id), 
                    new org.web3j.abi.datatypes.Utf8String(descricao), 
                    new org.web3j.abi.datatypes.Address(160, dono), 
                    new org.web3j.abi.datatypes.generated.Uint256(dataPublicacao));
            this.id = id;
            this.descricao = descricao;
            this.dono = dono;
            this.dataPublicacao = dataPublicacao;
        }

        public RegistroSaude(Uint256 id, Utf8String descricao, Address dono, Uint256 dataPublicacao) {
            super(id, descricao, dono, dataPublicacao);
            this.id = id.getValue();
            this.descricao = descricao.getValue();
            this.dono = dono.getValue();
            this.dataPublicacao = dataPublicacao.getValue();
        }
    }
}
