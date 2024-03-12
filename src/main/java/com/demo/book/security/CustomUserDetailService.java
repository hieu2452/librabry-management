package com.demo.book.security;

import com.demo.book.entity.Staff;
import com.demo.book.repository.StaffRepository;
import com.demo.book.repository.UserRepository;
import com.demo.book.service.impl.BookRedisServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<Object,Object> template;
//    HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
    private final String AUTH_KEY = "auth-";
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String key = AUTH_KEY+username;
        try {
            if(template.hasKey(key)) {
                String json = (String) template.opsForValue().get(key);
                UserDetails userDetails;

                userDetails = objectMapper.readValue(json, new TypeReference<Staff>() {});
                return userDetails;
            } else  {
                UserDetails userDetails = staffRepository.findByUsername(username);
//                Map<byte[], byte[]> mappedHash = mapper.toHash(userDetails);
                template.opsForValue().set(key,objectMapper.writeValueAsString(userDetails),2 ,TimeUnit.MINUTES);
                return userDetails;
            }
        } catch (RedisConnectionFailureException ex) {
            log.error("Connect to redis server fail");
            UserDetails userDetails = staffRepository.findByUsername(username);
            return userDetails;
        }
    }
}
