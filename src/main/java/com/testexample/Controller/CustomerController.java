package com.testexample.Controller;

import com.testexample.Entity.Customer;
import com.testexample.Repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    record NewCustomerRequest(String name, String email, Integer age){}

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);

        customerRepository.save(customer);
    }

    @PutMapping("{customerId}/edit")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request) {
        Customer updateCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not exist with id: " + id));

        updateCustomer.setName(request.name);
        updateCustomer.setEmail(request.email);
        updateCustomer.setAge(request.age);

        customerRepository.save(updateCustomer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }
}
