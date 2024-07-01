package com.rodrigo.springbatch.springbatchleituraarquivomultiplosformatos.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultiplosArquivosClienteTransacaoReaderConfig {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @StepScope
    @Bean
    public MultiResourceItemReader multiplosArquivosClienteTransacoesReader(
            @Value("#{jobParameters['arquivosClientes']}") Resource[] arquivosClientes,
            FlatFileItemReader leituraArquivoMultiplosFormatosReader){
        return new MultiResourceItemReaderBuilder<>()
                .name("multiplosArquivosClienteTransacaoReaderConfig")
                .resources(arquivosClientes)
                .delegate(new ArquivoClienteTransacaoReader(leituraArquivoMultiplosFormatosReader))
                .build();
    }

}
