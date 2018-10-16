package com.capgemini.helloservice2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
@EnableEurekaClient
@SpringBootApplication
public class Helloservice2Application {

	public static void main(String[] args) {
		SpringApplication.run(Helloservice2Application.class, args);
	}


@RestController
class ServiceInstanceRestController {

	@Autowired
    private EurekaClient eurekaClient;
	
	
    private RestTemplate restTemplate=new RestTemplate() ;
	
	@GetMapping("/greeting")
	public String getGreetingMessage() {
		Application application = eurekaClient.getApplication("helloServerC");
	       InstanceInfo instanceInfo = application.getInstances().get(0);
	       String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/"+"message";
		return restTemplate.getForObject(url, String.class);
	}
}

}