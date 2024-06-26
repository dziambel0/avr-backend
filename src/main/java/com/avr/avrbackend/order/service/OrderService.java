package com.avr.avrbackend.order.service;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.mapper.CarMapper;
import com.avr.avrbackend.cars.service.CarService;
import com.avr.avrbackend.company.domain.Company;
import com.avr.avrbackend.company.repository.CompanyRepository;
import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.order.domain.OrderDto;
import com.avr.avrbackend.order.mapper.OrderMapper;
import com.avr.avrbackend.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CarService carService;

    private final OrderMapper orderMapper;

    private final CarMapper carMapper;

    private final CompanyRepository companyRepository;

    private final CurrencyService currencyService;

    private final WeatherService weatherService;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public Order updateOrder(Order order){
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public ResponseEntity<OrderDto> addCarToOrder(Long orderId, Long carId){
        logger.info("Adding car {} to order {}", carId, orderId);

        Car car = carService.getCarById(carId)
                .orElseThrow(()-> new RuntimeException("Car not found by id: " + carId));
        logger.info("Retrieved car: {}", car);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found by id: " + orderId));
        logger.info("Retrieved order: {}", order);

        order.getCars().add(car);
        orderRepository.save(order);
        logger.info("Car added to order: {}", order);

        OrderDto orderDto = orderMapper.mapToOrderDto(order);
        return ResponseEntity.ok(orderDto);
    }

    public Order deleteCarFromOrder(Long orderId, Long carId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found id: " + orderId));

        Car car = carService.getCarById(carId)
                .orElseThrow(()-> new RuntimeException("Car not found by id: " + carId));

        order.getCars().remove(car);
        return orderRepository.save(order);
    }

    public Car getCarFromOrder(Long orderId, Long carId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found by id: " + orderId));

        return order.getCars().stream()
                .filter(car -> car.getCarId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Car not found by id: " + carId));
    }

    public List<CarDto> getCarDtosFromOrder(Long orderId) {
        List<Car> carList = orderRepository.findById(orderId)
                .map(Order::getCars)
                .orElseThrow(() -> new RuntimeException("Cars not found in Order id: " + orderId));
        return carList.stream()
                .map(carMapper::mapToCarDto)
                .collect(Collectors.toList());
    }

    public Order assignComapnyToOrder(Long orderId, Long companyId){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found by id: " + orderId));
        Company company = companyRepository.findById(companyId).orElseThrow(()-> new RuntimeException("Company not found by Id: "+companyId));

        order.setCompany(company);
        return orderRepository.save(order);
    }

    public void calculateOrderPrice (Long orderId){
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()){
            Order order = orderOptional.get();
            long days = ChronoUnit.DAYS.between(order.getStartDate(), order.getEndDate());
            int numbersOfCars = order.getCars().size();
            double priceInPLN = days * numbersOfCars * 20;
            double exchangeRate = currencyService.getEuroExchangeRate();

            //API WYMIANA WALUT - Zamiana na EUR
            double priceInEUR = priceInPLN / exchangeRate;

            //API POGODA - Napiczanie rabat
            try{
                String weatherData = weatherService.getWeatherData("Katowice");
                JSONObject json = new JSONObject (weatherData);
                String weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");

                if(weatherDescription.contains("rain")){
                    priceInEUR *= 0.8;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            order.setPrice(priceInEUR);
            orderRepository.save(order);
        }else {
            throw new RuntimeException("Order not found by id: "+orderId);
        }
    }
}
