package com.example.hellospring.controller;

import com.example.hellospring.entity.Employee;
import com.example.hellospring.repository.EmployeeRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return repository.findAll();
    }

    @PostMapping("/employees")
    public Employee saveEmployeeDetails(@RequestBody Employee newEmployee){
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> getOneEmployee(@PathVariable Long id){
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).getOneEmployee(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees")
        );
    }

    @PutMapping("/employees/{id}")
    public Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setEmail(newEmployee.getEmail());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
    }


}
