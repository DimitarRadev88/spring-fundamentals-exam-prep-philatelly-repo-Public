package com.philately.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.philately.paper.model.Paper;
import com.philately.paper.model.PaperName;
import com.philately.paper.service.PaperService;
import com.philately.stamp.model.Stamp;
import com.philately.user.model.User;
import com.philately.web.dto.StampAddBindingModel;
import com.philately.web.dto.UserRegisterBindingModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    @Autowired
    public ModelMapper modelMapper(PaperService paperService) {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, String> passwordConverter = ctx ->
                ctx.getSource() == null
                        ? null
                        : passwordEncoder().encode(ctx.getSource());

        modelMapper.createTypeMap(UserRegisterBindingModel.class, User.class)
                .addMappings(
                        mapper -> mapper.using(passwordConverter)
                                .map(UserRegisterBindingModel::getPassword, User::setPassword));

        Converter<PaperName, Paper> paperConverter = ctx ->
                ctx.getSource() == null
                        ? null
                        : paperService.getPaper(ctx.getSource());

        modelMapper.createTypeMap(StampAddBindingModel.class, Stamp.class)
                .addMappings(
                        mapper -> mapper.using(paperConverter)
                                .map(StampAddBindingModel::getPaperName, Stamp::setPaper));

        return modelMapper;
    }
}
