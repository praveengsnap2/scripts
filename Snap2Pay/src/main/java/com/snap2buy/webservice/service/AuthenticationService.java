package com.snap2buy.webservice.service;

import com.snap2buy.webservice.model.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Anoop on 07/23/16.
 */
public interface AuthenticationService {

    public void createUser(User user);
    public List<LinkedHashMap<String, String>> getUserDetail(String userId);
    public void updateUser(User user);
    public void updateUserPassword(User user);
    public void deleteUser(String userId);
	List<LinkedHashMap<String, String>> getUserForAuth(String userId);
    
}
