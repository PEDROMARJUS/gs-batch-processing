package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

//PersonItemProcessor implementa la interfaz ItemProcessor de Spring Batch
//Se recibe un objeto entrante (persona), y se transforma (persona superior)
public class PersonaItemProcessor implements ItemProcessor<Persona, Persona> {

    private static final Logger log = LoggerFactory.getLogger(PersonaItemProcessor.class);

    @Override
    //En el procesamiento por lotes se recogen datos, se pueden transformar y canalizar hacia otro lugar.
    public Persona process(final Persona persona) throws Exception {
        final String nombre = persona.getNombre().toUpperCase();
        final String apellido1 = persona.getApellido1().toUpperCase();
        final String apellido2 = persona.getApellido2().toUpperCase();

        final Persona transformedPersona = new Persona(nombre, apellido1, apellido2);

        log.info("Converting (" + persona + ") into (" + transformedPersona + ")");

        return transformedPersona;
    }

}
