package wt.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import wt.domain.User;

@Stateless
public class UserService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public User getUser(String username , String password ) {
		
		List<User> users = entityManager.createQuery("select u from User u where u.username=:username and u.password=:password",User.class)
				.setParameter("username",username)
				.setParameter("password", password)
				.getResultList();
		
		
		return users.get(0);
		
	}
	public User getUserById(int id) {
		
		User user = entityManager.find(User.class, id);
		return user;
		
	}
	

	

}
