package org.sid.secservice.web;

import java.util.List;

import org.sid.secservice.entities.AppRole;
import org.sid.secservice.entities.AppUser;
import org.sid.secservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping(path = "/users")
	public List<AppUser> appusers(){
		return this.accountService.listUsers();
	}
	
	@PostMapping(path = "/users")
	public AppUser saveUser(@RequestBody AppUser appUser) {
		return this.accountService.addNewUser(appUser);
	}
	
	@PostMapping(path = "/roles")
	public AppRole saveUser(@RequestBody AppRole appRole) {
		return accountService.addNewRole(appRole);
	}
	
	@PostMapping(path = "/addRoleTouser")
	public void addRoleTouser(@RequestBody RoleUserForm roleUserForm) {
		accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
	}

}

class RoleUserForm {
	private String username;
	private String roleName;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
