package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//El procesamiento por lotes se puede integrar en aplicaciones web y archivos WAR,
//pero es más sencillo crear una aplicación independiente.
//Empaquetamos todo en un solo archivo JAR ejecutable, impulsado por un método Java main()
public class Application {

    public static void main(String[] args) throws Exception {
    	//La aplicación es iniciada por Spring Boot
        SpringApplication.run(Application.class, args);
    }
}
