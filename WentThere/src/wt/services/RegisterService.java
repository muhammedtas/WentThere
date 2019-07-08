package wt.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import wt.domain.User;

@Stateless
public class RegisterService {
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	public void addUser(String name, String lastname, String email, String password) {
		
		
		
		User user = new User(name,lastname,email,password);
		
		entitymanager.persist(user);
		
	}
	
	public boolean checkUser(String name) {
		
		try {
			User checkUser = entitymanager.createQuery("From User u where u.name=:name", User.class).setParameter("name", name).getResultList().get(0);
			if(checkUser!= null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
		
		
	}
	
	

}
