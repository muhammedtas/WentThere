package wt.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import wt.domain.User;


@Stateless
public class LoginService {
	
	@PersistenceContext
	private EntityManager entityManager;

	public User getUser(String email, String password) {

	//JPQL, JPAQL, EJBQL
	List<User> users = entityManager.createQuery("select u from User u where u.email=:email and u.password=:pass",User.class)
						.setParameter("email", email)
						.setParameter("pass",password)
						.getResultList();
			
			if(users.size()==1)
			{	
				return users.get(0);
			}
		
		return null;
	}

}
