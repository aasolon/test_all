package com.example.testall.beanvalidation;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanValidationController {

    @Autowired
    SmartValidator validator;

    @PostMapping("/test-bean-validation")
    public String testBeanValidation(@RequestBody @Valid Car car) {
        return "asd";
    }
}
