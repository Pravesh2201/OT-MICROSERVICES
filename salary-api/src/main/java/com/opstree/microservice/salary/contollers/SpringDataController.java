// package com.opstree.microservice.salary.controller;

// import com.opstree.microservice.salary.service.SpringDataSalaryService;
// import com.opstree.microservice.salary.model.Employee;
// import com.opstree.microservice.salary.model.Message;

// import java.util.List;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;

// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.beans.factory.annotation.Autowired;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.media.Content;
// import io.swagger.v3.oas.annotations.media.Schema;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;

// @Tag(name = "Salary API", description = "Management APIs for all salary related transaction")
// @RestController
// @RequestMapping("/api/v1/salary")
// @RequiredArgsConstructor
// public class SpringDataController {

//     @Autowired
//     SpringDataSalaryService springDataSalaryService;

//     @Operation(summary = "Create a new employee salary record", tags = { })
//     @ApiResponses({@ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }) })
//     @PostMapping("/create/record")
//     public ResponseEntity<Employee> createSalaryRecord(@RequestBody Employee employee) {
//         try {
//            Employee _employee = springDataSalaryService
//                .saveSalary(new Employee(employee.getId(), employee.getName(), employee.getSalary(), employee.getProcessDate(), employee.getStatus()));
//            return new ResponseEntity<>(employee, HttpStatus.CREATED);
//         } catch (Exception e) {
//             return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }

//     @Operation(summary = "Retrieve all employee salary information", tags = {})
//     @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") })})
//     @GetMapping("/search/all")
//     public ResponseEntity<List<Employee>> getAllEmployeeSalary() {
//         try {
//             return new ResponseEntity<>(springDataSalaryService.getAllEmployeeSalary(), HttpStatus.OK);
//         } catch (Exception e) {
//             return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }

//     @Operation(
//       summary = "Retrieve a Salary by Employee Id",
//       description = "Get a salary object by specifying its id. The response is Employee object with id, name, salary.",
//       tags = {})
//     @ApiResponses({@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") })})
//     @GetMapping("/search")
//     public ResponseEntity<Employee> findSalary(@RequestParam("id") String id) {
//         try {
//             return new ResponseEntity<>(springDataSalaryService.getEmployeeSalary(id), HttpStatus.OK);
//         } catch (Exception e) {
//             return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }
// }

package com.opstree.microservice.salary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.opstree.microservice.salary.service.SpringDataSalaryService;
import com.opstree.microservice.salary.model.Employee;
import java.util.List;
@Configuration
class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000",
                "http://18.143.178.99:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
            .exposedHeaders("Content-Length")
            .allowCredentials(true)
            .maxAge(12 * 60 * 60);
    }
}
@Tag(name = "Salary API", description = "Management APIs for all salary-related transactions")
@RestController
@RequestMapping("/api/v1/salary")
class SpringDataController {
    @Autowired
    private SpringDataSalaryService springDataSalaryService;
    @Operation(summary = "Create a new employee salary record", description = "Creates a new salary record for an employee.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Salary record created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)))
    })
    @PostMapping("/create/record")
    public ResponseEntity<Employee> createSalaryRecord(@RequestBody Employee employee) {
        try {
            Employee _employee = springDataSalaryService.saveSalary(employee);
            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Retrieve all employee salary information", description = "Returns a list of all employee salary records.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of employees", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)))
    })
    @GetMapping("/search/all")
    public ResponseEntity<List<Employee>> getAllEmployeeSalary() {
        try {
            return new ResponseEntity<>(springDataSalaryService.getAllEmployeeSalary(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Retrieve salary by employee ID", description = "Get salary record of an employee by specifying their ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee salary", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<Employee> findSalary(@RequestParam("id") String id) {
        try {
            return new ResponseEntity<>(springDataSalaryService.getEmployeeSalary(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
