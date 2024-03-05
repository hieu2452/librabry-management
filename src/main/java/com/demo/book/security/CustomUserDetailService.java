package com.demo.book.security;

import com.demo.book.entity.Staff;
import com.demo.book.repository.StaffRepository;
import com.demo.book.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<Object,Object> template;
    private final String AUTH_KEY = "auth-";
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String key = AUTH_KEY+username;
        if(template.hasKey(key)) {
            try {
                String json = (String) template.opsForValue().get(key);
                UserDetails userDetails;
                userDetails = objectMapper.readValue(json, new TypeReference<Staff>() {});
                return userDetails;
            } catch (IOException e) {
                e.printStackTrace();
                throw new UsernameNotFoundException("Error deserializing Staff object from JSON for username: " + username);
            }
        }
//        Thread.sleep(2000);
        UserDetails userDetails = staffRepository.findByUsername(username);
            template.opsForValue().set(key,objectMapper.writeValueAsString(userDetails),2 ,TimeUnit.MINUTES);
        return userDetails;
    }
}
