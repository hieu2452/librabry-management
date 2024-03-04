package com.demo.book.security;

import com.demo.book.repository.StaffRepository;
import com.demo.book.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<Object,Object> template;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(template.hasKey(username)) {
            String json = (String) template.opsForValue().get(username);
            UserDetails userDetails;
            try {
                userDetails = objectMapper.readValue(json, new TypeReference<UserDetails>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return userDetails;
        }
        return staffRepository.findByUsername(username);
    }
}
