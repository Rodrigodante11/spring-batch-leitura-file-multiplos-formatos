package com.rodrigo.springbatch.springbatchleituraarquivomultiplosformatos.reader;

import com.rodrigo.springbatch.springbatchleituraarquivomultiplosformatos.dominio.Cliente;
import com.rodrigo.springbatch.springbatchleituraarquivomultiplosformatos.dominio.Transacao;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class clienteTRansacaoLineMapperConfig {

    @SuppressWarnings("rawtypes")
    @Bean
    public PatternMatchingCompositeLineMapper lineMapperch(){ //(PatternMatchingCompositeLineMapperch) usa um padrao para descobrir qual lineMapper ele ira aplicar
        PatternMatchingCompositeLineMapper lineMapperch = new PatternMatchingCompositeLineMapper();
        lineMapperch.setTokenizers(tokenizers());  // recebe linhas e divdem em palavras  // tokenizador
        lineMapperch.setFieldSetMappers(fieldSetMappers()); // pega as palavras e mapea para um objeto de dominio // mapeadores
        return lineMapperch;
    }

    @SuppressWarnings("rawtypes")
    private Map<String, FieldSetMapper> fieldSetMappers() {
        Map<String, FieldSetMapper> fieldSetMapperMap = new HashMap<>();
        fieldSetMapperMap.put("0*", fieldSetMapper(Cliente.class));
        fieldSetMapperMap.put("1*", fieldSetMapper(Transacao.class));
        return fieldSetMapperMap;
    }

    @SuppressWarnings("rawtypes")
    private FieldSetMapper fieldSetMapper(Class classe) {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(classe);
        return fieldSetMapper;
    }

    private Map<String, LineTokenizer> tokenizers() {
        Map<String, LineTokenizer> tokenizers = new HashMap<>();
        tokenizers.put("0*", clienteLineTokenizer());  // os 0 la no arquivo representa (comeo da linha) os clientes
        tokenizers.put("1*", transcaoLineTokenizer()); // os 0 la no arquivo representa (comeo da linha) as transacoes
        return tokenizers;
    }

    private LineTokenizer clienteLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("nome", "sobrenome", "idade", "email");
        lineTokenizer.setIncludedFields(1,2,3,4);  // nao pegando o "0" pois ele eh o tipo de linha(cliente/transacao)
        return lineTokenizer;
    }

    private LineTokenizer transcaoLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id", "descricao", "valor");
        lineTokenizer.setIncludedFields(1,2,3);  // nao pegando o "0" pois ele eh o tipo de linha(cliente/transacao)
        return lineTokenizer;
    }
}
