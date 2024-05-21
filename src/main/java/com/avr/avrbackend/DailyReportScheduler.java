package com.avr.avrbackend;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyReportScheduler {

    @Autowired
    private CarService carService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${admin.email}")
    private String adminEmail;

    @Scheduled(cron = "0 15 19 * * *")
    public void sendDailyRaport(){
        List<Car> registeredCars = carService.getAllCars();
        String raport = createRaport(registeredCars);
        sendEmail(adminEmail, "Daily Raport", raport);
    }

    private String createRaport(List<Car> registeredCars){
        StringBuilder raport = new StringBuilder();
        raport.append("Daily Raport - Registered Cars\n\n");
        raport.append("Total Cars Registered: ").append(registeredCars.size()).append("\n\n");
        for (Car car : registeredCars){
            raport.append("Car Id: ").append(car.getCarId());
            raport.append(", brand: ").append(car.getBrand());
            raport.append(", model: ").append(car.getModel());
            raport.append(", car year: ").append(car.getCarYear());
            raport.append(", registration number: ").append(car.getRegistrationNumber());
            raport.append("Car status: ").append(car.getCarStatus()).append("\n");
        }
        return raport.toString();
    }

    private void sendEmail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

}
