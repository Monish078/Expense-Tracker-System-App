package com.intermediate_project.Expense_Tracker_System_App.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MapperConfig {

    @Bean // easy conversion of student to studentDto
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
