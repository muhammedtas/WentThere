package wt.mbeans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import wt.domain.User;
import wt.services.LoginService;


@ManagedBean
@SessionScoped
public class LoginBean {
	
	private String email;
	private String password;
	private int userId;
	private User currentUser;


	@EJB
	private LoginService loginservice;
	
	public String login() {
		
		try {
			System.out.println(email);
			System.out.println(password);
			
			User user = loginservice.getUser(email, password);
					
			if(user== null) {
		        FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Wrong credentials!!!"));
		        
				return "login";
			}
			setUserId(user.getId());
			setCurrentUser(user);
			
			return "secure/visitedplaces?faces-redirect=true";

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error Occured!!!", null));
		return "error";
		}
		
		
	}
	
	 public String logout() {
	
		 FacesContext.getCurrentInstance().getExternalContext()
		        .invalidateSession();
		 currentUser =null;

	     return "/login?faces-redirect=true";
		 //return "login?faces-redirect=true";
	 }
	
	
	public LoginBean() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public User getCurrentUser() {
		return currentUser;
	}


	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int id) {
		this.userId = id;
	}
	
}