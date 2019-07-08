package wt.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import wt.domain.Address;
import wt.domain.Comment;

@Stateless

public class CommentService {

	@PersistenceContext
	private EntityManager entityManager;
	
	public boolean addComment(String title, String commentContext, Address address) {
		
		try {
						
			Comment content = new Comment(title, commentContext, address);
			
			entityManager.persist(content);
			
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
		
	}
	
	public List<Comment> getAllCommentsOfAddres(int id) {
		
		List<Comment> comments = null;
		try {
			
			comments = entityManager.createQuery("from Comment c where c.address.id = :id", Comment.class)
					.setParameter("id", id)
					.getResultList();
			
			
		} catch (Exception e) {
			System.out.println("An error has occured getting all comments of addres. Details are   :" + e.getMessage()+e.getLocalizedMessage());
		}
		return comments;
	}
	
	public Address commentAddres(int id) {
		Address address = null;
		try {
			address = entityManager.find(Address.class, id);
		} catch (Exception e) {
			System.out.println("An error has occured getting addres of this comment. Details are   :" + e.getMessage()+e.getLocalizedMessage());
		}
		return address;
		
	}
	
	public boolean deleteComment(int id) {
		try {
			Comment comment = entityManager.find(Comment.class, id);
			entityManager.remove(comment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
