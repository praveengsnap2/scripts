package com.snap2buy.webservice.service.impl;

import com.snap2buy.webservice.dao.AuthenticationServiceDao;
import com.snap2buy.webservice.dao.MetaServiceDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.*;
import com.snap2buy.webservice.service.AuthenticationService;
import com.snap2buy.webservice.service.MetaService;
import com.snap2buy.webservice.util.ConverterUtil;
import com.snap2buy.webservice.util.CryptoUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ANOOP on 07/23/16.
 */
@Component(value = BeanMapper.BEAN_AUTH_SERVICE)
@Service
@Scope("prototype")
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService, ApplicationListener<AuthenticationSuccessEvent>{
	
	 private static Logger LOGGER = Logger.getLogger("s2b");
	 
	 @Autowired
	 @Qualifier(BeanMapper.BEAN_AUTH_SERVICE_DAO)
	 private AuthenticationServiceDao authServiceDao;

	@Override
	public void createUser(User userInput) {
		LOGGER.info("---------------AuthenticationServiceImpl Starts createUser user id = " + userInput.getUserId()+ "----------------\n");
		
		userInput.setPassword(CryptoUtil.encrypt(userInput.getPassword()));

		authServiceDao.createUser(userInput);


        LOGGER.info("---------------AuthenticationServiceImpl Ends createUser user id = "+userInput.getUserId()+"----------------\n");
	}

	@Override
	public List<LinkedHashMap<String, String>> getUserDetail(String userId) {
		LOGGER.info("---------------AuthenticationServiceImpl Starts getUserDetail user id = " + userId+ "----------------\n");

		List<LinkedHashMap<String, String>> user = authServiceDao.getUserDetail(userId);


        LOGGER.info("---------------AuthenticationServiceImpl Ends getUserDetail user id = "+userId+"----------------\n");
        return user;
	}

	@Override
	public void updateUser(User userInput) {
		LOGGER.info("---------------AuthenticationServiceImpl Starts updateUser user id = " + userInput.getUserId()+ "----------------\n");

		authServiceDao.updateUser(userInput);


        LOGGER.info("---------------AuthenticationServiceImpl Ends updateUser user id = "+userInput.getUserId()+"----------------\n");
	}

	@Override
	public void updateUserPassword(User userInput) {
		LOGGER.info("---------------AuthenticationServiceImpl Starts updateUserPassword user id = " + userInput.getUserId()+ "----------------\n");

		userInput.setPassword(CryptoUtil.encrypt(userInput.getPassword()));
		
		authServiceDao.updateUserPassword(userInput);


        LOGGER.info("---------------AuthenticationServiceImpl Ends updateUserPassword user id = "+userInput.getUserId()+"----------------\n");
	}

	@Override
	public void deleteUser(String userId) {
		LOGGER.info("---------------AuthenticationServiceImpl Starts deleteUser user id = " + userId+ "----------------\n");

		authServiceDao.deleteUser(userId);


        LOGGER.info("---------------AuthenticationServiceImpl Ends deleteUser user id = "+userId+"----------------\n");
	}
	
	@Override
	public List<LinkedHashMap<String, String>> getUserForAuth(String userId) {
		LOGGER.info("---------------AuthenticationServiceImpl Starts getUserForAuth with user id = " + userId+ "----------------\n");

		List<LinkedHashMap<String, String>> userList = authServiceDao.getUserForAuth(userId);

        LOGGER.info("---------------AuthenticationServiceImpl Ends getUserForAuth with user id = "+userId+"----------------\n");
        return userList;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		List<LinkedHashMap<String, String>> u = authServiceDao.getUserForAuth(username);
		
		if(u == null || u.size() == 0){
			LOGGER.error("User " + username + " is not defined in the system!");
			throw new UsernameNotFoundException("User '" + username + "' is not defined in the system!");
		}
		Map<String,String> user = u.get(0);
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(authority);
		return new org.springframework.security.core.userdetails.User(user.get("userId"), user.get("password"),true,true,true,true, authorities );
	}

	/**
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event)
	{
		String userName = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
		LOGGER.debug(userName + " logged in successfully");
	}
	
}
