package hello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	//Se detecta el final del trabajo y se listan los resultados
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			/*
			List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people", new RowMapper<Person>() {
			@Override
			public Person mapRow(ResultSet rs, int row) throws SQLException {
				return new Person(rs.getString(1), rs.getString(2));
			}
			*/
			List<Persona> results = jdbcTemplate.query("SELECT nombre, apellido1, apellido2 FROM pruuser", new RowMapper<Persona>() {
				@Override
				public Persona mapRow(ResultSet rs, int row) throws SQLException {
					return new Persona(rs.getString(1), rs.getString(2), rs.getString(3));
				}
			});

			for (Persona persona : results) {
				log.info("Found <" + persona + "> in the database.");
			}

		}
	}
}
