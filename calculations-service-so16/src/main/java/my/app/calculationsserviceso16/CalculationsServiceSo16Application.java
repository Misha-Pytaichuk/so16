package my.app.calculationsserviceso16;

import my.app.calculationsserviceso16.exception.BindingExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CalculationsServiceSo16Application {

	public static void main(String[] args) {
		SpringApplication.run(CalculationsServiceSo16Application.class, args);
	}

	@Bean
	public BindingExceptionHandler bindingExceptionHandler(){
		return new BindingExceptionHandler();
	}
}
