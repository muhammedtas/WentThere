package wt.services;

import java.util.List;
 
import javax.ejb.Stateless;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import wt.domain.Address;
import wt.domain.User;
import wt.mbeans.LoginBean;

@Stateless
public class AddressService {

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean; 
	


	@PersistenceContext
	private EntityManager entityManager;

	
	public AddressService() {
		super();
	}

	public void addAddress(double lat, double lon, String addressName,String addressDescription, User user) {
		
		try {
			
			Address address = new Address(lat,lon,addressName,addressDescription,user);
			
			entityManager.persist(address);
			
		} catch (Exception e) {
			
			System.out.println("An error has occured while saving this addres. Details are   :" + e.getMessage()+e.getLocalizedMessage());
		}
	}

	public Address getAddresById(int id) {
		
		return entityManager.find(Address.class, id);
		
	}

	public List<Address> getAllAddresses() {
		List<Address> addresses = null;
		try {
			
			addresses = entityManager.createQuery("from Address", Address.class).getResultList();
			
		} catch (Exception e) {
			System.out.println("An error has occured while getting all address in service method" + e.getMessage());
		}
		return addresses;
	}
	
	public List<Address> getUserAdressess(int id){
		
		List<Address> address = null;
		try {
			address =  entityManager.createQuery("from Address a where a.user.id =:id ", Address.class).setParameter("id", id).getResultList();

		} catch (Exception e) {
			System.out.println("An error has occured while getting user's address in service method. Details are   : " + e.getMessage());		
			
		}
		return address;
		
	}
	
	public boolean deleteAddressById(int id) {
		
		try {
			
			Address address = entityManager.find(Address.class, id);
			entityManager.remove(address);
			return true;
		} catch (Exception e) {
			
			return false;
		}
		
		
	}
	
	public LoginBean getLoginBean() {
		return loginBean;
	}


	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public boolean checkAddres(String addressName) {
		List<Address> addres =  entityManager.createQuery("from Address a where a.addressName =:addressName", Address.class)
				.setParameter("addressName", addressName).getResultList();
		
		if (!addres.isEmpty()) {
			return true;
		} else {
			return false;
		}
		
	}

}