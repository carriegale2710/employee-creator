package io.carrie.employee.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Qualifier("updateModelMapper")
    public ModelMapper updateModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(ctx -> ctx.getSource() != null &&
                !(ctx.getSource() instanceof String && ((String) ctx.getSource()).isBlank()));
        return mapper;
    }

}
