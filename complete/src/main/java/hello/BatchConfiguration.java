package hello;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableBatchProcessing
//Se prepara el trabajo por lotes
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    // tag::readerwriterprocessor[]
    @Bean
    //Se define la entrada
    public FlatFileItemReader<Persona> reader() {
        FlatFileItemReader<Persona> reader = new FlatFileItemReader<Persona>();
        //Archivo de origen de datos (en .csv cada fila contiene los campos separados por el delimitador definido)
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Persona>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "nombre", "apellido1", "apellido2" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Persona>() {{
                setTargetType(Persona.class);
            }});
        }});
        return reader;
    }

    @Bean
    public PersonaItemProcessor processor() {
        return new PersonaItemProcessor();
    }

    //Se define la salida
    @Bean
    /*
    public JdbcBatchItemWriter<Person> writer() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }
    */
    public JdbcBatchItemWriter<Persona> writer() {
        JdbcBatchItemWriter<Persona> writer = new JdbcBatchItemWriter<Persona>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Persona>());
        writer.setSql("INSERT INTO pruuser (nombre, apellido1, apellido2) VALUES (:nombre, :apellido1, :apellido2)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    //Definimos el trabajo, y los pasos que lo componen
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    //En chunk se define la cantidad de datos que se escriben a la vez
    //Cada paso puede involucrar a un lector, un procesador y un escritor
    //Chunk () tiene un prefijo <X, Y> porque es un m�todo gen�rico, 
    // representa los tipos de entrada y salida de cada registro de procesamiento
    // y se corresponde con ItemReader <X> e ItemWriter <Y>
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Persona, Persona> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]
}
