package ch.bbw.coolcarsbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "${frontend.url}")
public class CarController implements ApplicationRunner {

    private final CarRepository carRepository;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("")
    public String helloWorld() {
        return "Hello World from Backend";
    }

    @GetMapping("/cars")
    public List<Car> getCars() {
        System.out.println(carRepository.findAll());
        return (List<Car>) carRepository.findAll();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("App Runner...");
        carRepository.save(new Car(0, "Dodge", "Challenger", 500));
        carRepository.findAll().forEach(System.out::println);
    }

    @GetMapping("/cars/{id}")
    public Car getACar(@PathVariable int id) {
        return new Car(id, "Ford", "Mustang", 450);
    }
}
