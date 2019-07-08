package wt.mbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import wt.domain.Address;
import wt.domain.User;
import wt.mbeans.LoginBean;
import wt.services.AddressService;
import wt.services.LoginService;
import wt.services.UserService;

@ManagedBean
@SessionScoped
public class AddresBean {

	private double lat;
	private double lon;
	private int id;
	private List<Address> addressess;

	private String addressName;
	private String addressDescription;

	public AddresBean(AddressService _addressService, LoginService _loginService, UserService _userService) {
		super();
		this._addressService = _addressService;
		this._loginService = _loginService;
		this._userService = _userService;

	}

	public AddresBean() {
		super();
	}

	@PostConstruct
	public void init() {
		try {
			this.addressess = null;
			this.addressess = _addressService.getAllAddresses();
			if (!(this.addressess.isEmpty()))
				this.address = this.addressess.get(0);

		} catch (Exception e) {
			System.out.println("Probably there is no address belonging this user yet...");
		}

	}

	@EJB
	public AddressService _addressService;
	@EJB
	public LoginService _loginService;
	@EJB
	public UserService _userService;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String getAllAddresses() {
		try {
			this.addressess = _addressService.getAllAddresses();
			if (loginBean.getEmail() != null && loginBean.getPassword() != null) {
				return "visitedplaces";
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Error occured while getting addressess!!! Details are" + e.getMessage()));
		}
		
		return "places";
		
	}

	public String getUserAdresses() {

		try {
			User user = _userService.getUserById(loginBean.getUserId());

			this.addressess = _addressService.getUserAdressess(user.getId());
			return "";
		} catch (Exception e) {
			System.out.println("Probably addresses is null is address bean in getUserAdresses method. Details are :"
					+ e.getMessage());
			return "addnewplaces";
		}

	}

	public void select() {
		this.address = _addressService.getAddresById(this.selectedAddressId);
	}

	public String getCommentsPage(int currentAddressId) {

		this.setId(currentAddressId);
		Address currentAddress = _addressService.getAddresById(this.getId());
		this.setAddress(currentAddress);
		return "/secure/addcomments?faces-redirect=true";
	}

	public String saveAddres() {
		try {

			if (loginBean.getEmail() != null && loginBean.getPassword() != null) {

				if (lat != 0 && lon != 0 && addressName != null && addressDescription != null) {

					boolean isAddresExist = _addressService.checkAddres(addressName);
					System.out.println("result is : " + isAddresExist);
					if (isAddresExist == true) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage("This addres already exist!!"));

						return "addnewplaces";
					}

					User user = _userService.getUserById(loginBean.getUserId());

					_addressService.addAddress(lat, lon, addressName, addressDescription, user);

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("New addres added!"));
					init();
					return "addnewplaces?faces-redirect=true";

				} else {

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please fill all fields!"));

					return "addnewplaces";
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You are not authorized user"));
				return "login?faces-redirect=true";

			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Address could not be saved!!! Details are" + e.getMessage()));
			return "error?faces-redirect=true";
		}
	}

	public String deleteAddress(int id) {

		if (loginBean.getEmail() != null && loginBean.getPassword() != null && id > 0) {
			boolean result = _addressService.deleteAddressById(id);
			if (result) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully deleted"));

			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("UnSuccess!!"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You are not authorized user"));
			return "login?faces-redirect=true";
		}
		init();
		return "visitedplaces?faces-redirect=true";
	}

	public List<Address> getAddressess() {
		return addressess;
	}

	public void setAddressess(List<Address> addressess) {
		this.addressess = addressess;
	}

	private Address address;
	private int selectedAddressId;

	public int getSelectedAddressId() {
		return selectedAddressId;
	}

	public void setSelectedAddressId(int selectedAddressId) {
		this.selectedAddressId = selectedAddressId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressDescription() {
		return addressDescription;
	}

	public void setAddressDescription(String addressDescription) {
		this.addressDescription = addressDescription;
	}

}
