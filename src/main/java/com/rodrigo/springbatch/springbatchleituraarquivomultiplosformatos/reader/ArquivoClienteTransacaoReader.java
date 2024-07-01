package com.rodrigo.springbatch.springbatchleituraarquivomultiplosformatos.reader;

import com.rodrigo.springbatch.springbatchleituraarquivomultiplosformatos.dominio.Cliente;
import com.rodrigo.springbatch.springbatchleituraarquivomultiplosformatos.dominio.Transacao;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente> {
    private Object objAtual;
    private ItemStreamReader<Object> delegate;

    public ArquivoClienteTransacaoReader(ItemStreamReader<Object> delegate) {
        this.delegate = delegate;
    }


    @Override
    public Cliente read() throws Exception {
        if(objAtual == null) {
            objAtual = delegate.read(); // Ler um objeto
        }
        Cliente cliente = (Cliente) objAtual;
        objAtual = null;

        if(cliente != null) {  // enquanto tiver cliente para ler no arquivo
            while(peek() instanceof Transacao){
                cliente.getTransacoes().add((Transacao) objAtual);
            }
        }
        return cliente;
    }

    private Object peek() throws Exception {
        objAtual = delegate.read();// leitura do proximo item
        return objAtual;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }

}
