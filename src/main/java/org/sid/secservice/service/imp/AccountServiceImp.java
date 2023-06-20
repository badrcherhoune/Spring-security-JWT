package org.sid.secservice.service.imp;

import java.util.List;

import org.sid.secservice.entities.AppRole;
import org.sid.secservice.entities.AppUser;
import org.sid.secservice.repository.AppRoleRepository;
import org.sid.secservice.repository.AppUserRepository;
import org.sid.secservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImp implements AccountService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private AppRoleRepository appRoleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public AppUser addNewUser(AppUser appUser) {
		// TODO Auto-generated method stub
		String pw = appUser.getPassword();
		appUser.setPassword(passwordEncoder.encode(pw));
		return appUserRepository.save(appUser);
	}

	@Override
	public AppRole addNewRole(AppRole appRole) {
		return appRoleRepository.save(appRole);
	}

	@Override
	public void addRoleToUser(String username, String rouleName) {
		// TODO Auto-generated method stub
		AppUser appUser = appUserRepository.findByUserName(username);
		AppRole appRole = appRoleRepository.findByRoleName(rouleName);
		appUser.getAppRoles().add(appRole);
	}

	@Override
	public AppUser loadUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return this.appUserRepository.findByUserName(userName);
	}

	@Override
	public List<AppUser> listUsers() {
		// TODO Auto-generated method stub
		return this.appUserRepository.findAll();
	}

}
