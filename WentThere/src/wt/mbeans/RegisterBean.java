package wt.mbeans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import wt.services.RegisterService;

@ManagedBean
@SessionScoped
public class RegisterBean {
	

	private String name;
	private String lastname;
	private String email;
	private String password;

	
	
	@EJB
	private RegisterService registerService;
	
	
	public String Register() {
	
		
		if(name != null && email != null && password != null) {
		
			try {
				registerService.addUser(name,lastname,email,password);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Success!!!"));
				return "login";
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("User could not be registered...!!!"));
				return "register";
			
			}
		
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Values are not expected"));
			return "register";
		}
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
	
	
}
