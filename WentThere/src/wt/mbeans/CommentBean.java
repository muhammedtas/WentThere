package wt.mbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import wt.domain.Address;
import wt.domain.Comment;
import wt.services.AddressService;
import wt.services.CommentService;
import wt.services.LoginService;
import wt.services.UserService;

@ManagedBean
@RequestScoped
public class CommentBean {

	private int id;
	private Address address;
	private List<Comment> comments;
	private String title;
	private String content;
	
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean; 
	
	@ManagedProperty("#{addresBean}")
	private AddresBean addressBean;
	@EJB
	private AddressService _addressService;
	@EJB
	private LoginService _loginService;
	@EJB
	private UserService _userService;
	@EJB
	private CommentService _commentService;
	
	public CommentBean(AddressService _addressService, LoginService _loginService, UserService _userService, CommentService _commentService) {
		super();
		this._addressService = _addressService;
		this._loginService = _loginService;
		this._userService = _userService;
		this._commentService = _commentService;
	}

	public CommentBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
		try {
			if (loginBean.getEmail() != null && loginBean.getPassword() != null) {
				this.comments = null;
				int currentAddressId = addressBean.getAddress().getId();
				List<Comment> commentsToFetch = _commentService.getAllCommentsOfAddres(currentAddressId);
				this.comments = commentsToFetch;
			}
		} catch (Exception e) {
			System.out.println("Probably there is no address belonging this user yet...");
		}

	}
	
	public String addComment() {

		try {
			
			if (loginBean.getEmail() != null && loginBean.getPassword() != null) {
				
				if (!title.isEmpty() && !content.isEmpty()) {
					Address currentAddres =  addressBean.getAddress();
					_commentService.addComment(title, content, currentAddres);
					FacesContext.getCurrentInstance().addMessage("Information",
							new FacesMessage("Comment Added Successfully!"));
				}
				else {
					FacesContext.getCurrentInstance().addMessage("Warning",
							new FacesMessage("Please fill all fields!"));
				}
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You are not authorized user"));
				return "login?faces-redirect=true";
			}
						
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("Error",
					new FacesMessage("Comment could not be saved!!! Details are" + e.getMessage()));
		}
		init();
		return "addcomments?faces-redirect=true";
	}

	public String deleteComment(int id) {
		try {
			
			if (loginBean.getEmail() != null && loginBean.getPassword() != null) {
				boolean result = _commentService.deleteComment(id);
				if (result) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Successfully deleted!"));
				}
				else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("No reason!"));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You are not authorized user"));
				return "login?faces-redirect=true";
			}
			
			

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Comment could not be deleted!!! Details are" + e.getMessage()));
		}
		init();
		return "addcomments?faces-redirect=true";
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public AddresBean getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(AddresBean addressBean) {
		this.addressBean = addressBean;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public AddressService get_addressService() {
		return _addressService;
	}

	public void set_addressService(AddressService _addressService) {
		this._addressService = _addressService;
	}

	public LoginService get_loginService() {
		return _loginService;
	}

	public void set_loginService(LoginService _loginService) {
		this._loginService = _loginService;
	}

	public UserService get_userService() {
		return _userService;
	}

	public void set_userService(UserService _userService) {
		this._userService = _userService;
	}

	public CommentService get_commentService() {
		return _commentService;
	}

	public void set_commentService(CommentService _commentService) {
		this._commentService = _commentService;
	}

}
