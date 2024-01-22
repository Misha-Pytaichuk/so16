package my.app.orderserviceso16;

import my.app.orderserviceso16.util.exception.BindingExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderServiceSo16Application {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceSo16Application.class, args);
	}

	@Bean
	public BindingExceptionHandler bindingExceptionHandler(){
		return new BindingExceptionHandler();
	}
}
