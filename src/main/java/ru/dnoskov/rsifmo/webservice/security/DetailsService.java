package ru.dnoskov.rsifmo.webservice.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DetailsService implements UserDetailsService {

	List<User> users;
	
	public DetailsService() {
		users = new ArrayList<>();
		users.add(new User("login", "pass", new String[] {"ROLE_USER"}));
	}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = users.stream().filter(u -> u.getUsername().equals(username)).findAny();
        
        if (optionalUser.isPresent()){
        	User user = optionalUser.get();
        	
        	return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    AuthorityUtils.createAuthorityList(user.getRoles()));
        } else {
        	throw new UsernameNotFoundException(username + " was not found");
        }
    }

}
