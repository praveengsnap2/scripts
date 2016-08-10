package com.snap2buy.webservice.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.snap2buy.webservice.mapper.BeanMapper;

@Component(value = BeanMapper.BEAN_SALT_SOURCE_SERVICE)
@Scope("prototype")
public class SaltSourceImpl implements SaltSource {

	@Override
	public Object getSalt(UserDetails user) {
		return "snap2buy_"+user.getUsername();
	}
}