pragma solidity >=0.7.0 <0.9.0;

contract Saude {

    address owner = msg.sender;
    uint registroId = 0;

    struct RegistroSaude {
        uint id;
        string descricao;
        address dono;
        uint dataPublicacao;
    }

    struct Liberacao {
        address paciente;
        address medico;
    }

    RegistroSaude[] public registros;

    Liberacao[] public liberacoes;

    function recebeRegistroSaude(string memory descricao) public {
        RegistroSaude memory r;
        r.id = proximoID();
        r.descricao = descricao;
        r.dataPublicacao = block.timestamp;
        r.dono = msg.sender;
        registros.push(r);
    }

    function liberarAcesso(address carteira) public {
        Liberacao memory l;
        l.paciente = msg.sender;
        l.medico = carteira;
        liberacoes.push(l);
    }

    function verificaLiberacao(address paciente) public view returns (int indice) {
        Liberacao memory l;
        l.paciente = paciente;
        l.medico = msg.sender;

        //encontra o indice
        int indice = -1;
        for (uint i=0; i<liberacoes.length; i++) {
            Liberacao memory atual = liberacoes[i];
            if ( atual.paciente == l.paciente && atual.medico == l.medico ){
                indice = int(i);
            }
        }
        return indice;
    }

    function revogarAcesso(address carteira) public {
        Liberacao memory l;
        l.paciente = msg.sender;
        l.medico = carteira;

        //encontra o indice
        int indice = -1;
        uint indiceU = 0;
        for (uint i=0; i<liberacoes.length; i++) {
            Liberacao memory atual = liberacoes[i];
            if ( atual.paciente == msg.sender && atual.medico == carteira ){
                indice = int(i);
                indiceU = i;
            }
        }
        if (indice > -1){
            //move o ultimo item para o indice atual e faz um pop
            liberacoes[indiceU] = liberacoes[liberacoes.length - 1];
            liberacoes.pop();
        }
    }

    function consultarRegistroSaude( uint index ) public view returns (RegistroSaude memory r) {
        if ( registros.length >= index ) {
            r = registros[index];
        }
        //address solicitante = msg.sender;
        //address paciente = r.dono;
        return r;
    }

    function consultar( uint index ) public view returns (uint, string memory, address, uint) {
        RegistroSaude storage r;
        if ( registros.length >= index ) {
            r = registros[index];
            return (r.id, r.descricao, r.dono, r.dataPublicacao);
        }
        //address solicitante = msg.sender;
        //address paciente = r.dono;
        //return r;
    }

    function consultarLiberacao( uint index ) public view returns (address, address) {
        Liberacao storage l;
        if ( liberacoes.length >= index ) {
            l = liberacoes[index];
            return (l.paciente, l.medico);
        }
        //address solicitante = msg.sender;
        //address paciente = r.dono;
        //return r;
    }

    function consultarTotalDeRegistros() public view returns (uint) {
        if (registros.length > 0) {
            return registros.length;
        }
        return 0;
    }

    function consultarTotalDeLiberacoes() public view returns (uint) {
        if (liberacoes.length > 0) {
            return liberacoes.length;
        }
        return 0;
    }

    function proximoID() private returns (uint) {
        return registroId++;
    }

}