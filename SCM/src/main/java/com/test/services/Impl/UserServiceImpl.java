package com.test.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.entities.User;
import com.test.helper.AppConstants;
import com.test.helper.ResouceNotFoundException;
import com.test.repo.UserRepo;
import com.test.services.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	

    @Autowired
    private UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    
    public User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        
        user.setUserId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());
        
        return userRepo.save(user);
    }

    
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }
    

    
    public Optional<User> updatUser(User user) {
        User user2 = userRepo.findById(user.getUserId())
        .orElseThrow(()-> new ResouceNotFoundException("User not found!!"));   
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setAbout(user.getAbout());
        user2.setPassword(user.getPassword());
        user2.setPhone(user.getPhone());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnable(user.isEnabled());
        user2.setEmailVerify(user.isEmailVerify());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true:false;
    }

    
    public boolean isUserExistByEmail(String userId) {
        User user2 = userRepo.findByEmail(userId).orElse(null);
        return user2 != null ? true:false;  
    }

    
    public List<User> getAllUsers() {
       return userRepo.findAll(); 
    }

    
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(()-> new ResouceNotFoundException("User not found!!"));   
        userRepo.delete(user2);
    }


	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElse(null);
	}
    
}
