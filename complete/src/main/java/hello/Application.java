package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//El procesamiento por lotes se puede integrar en aplicaciones web y archivos WAR,
//pero es m�s sencillo crear una aplicaci�n independiente.
//Empaquetamos todo en un solo archivo JAR ejecutable, impulsado por un m�todo Java main()
public class Application {

    public static void main(String[] args) throws Exception {
    	//La aplicaci�n es iniciada por Spring Boot
        SpringApplication.run(Application.class, args);
    }
}
