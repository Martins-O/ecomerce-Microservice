package com.martinso.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final RestTemplate template;
    public void registerCustomer(CustomerRequest request) {
        Customer customer = Customer.builder()
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .build();
        repository.saveAndFlush(customer);

        FraudCheckResponse response = template.getForObject(
                "http:localhost:8081/api/v1/fraud/{customerId}",
                 FraudCheckResponse.class,
                 customer.getId()
        );

        assert response != null;
        if(response.isFraudster()){
            throw new IllegalStateException("Fraudster");
        }
    }
}
