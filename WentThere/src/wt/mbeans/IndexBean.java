package wt.mbeans;
//package wt.mbeans;
//
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//
//import wt.domain.Country;
//import wt.services.CountryService;
//
//@ManagedBean
//@ViewScoped
//public class IndexBean {
//	
//	private List<Country> countries;
//	
//	private int selectedCountryId;
//	
//	private Country country;
//	
//	@EJB
//	private CountryService countryService;
//	
//	public void select()
//	{
//		this.country = countryService.getCountryById(this.selectedCountryId);
//	}
//	
//	
//	public IndexBean(CountryService countryService) {
//		super();
//		this.countryService = countryService;
//	}
//
//
//	public IndexBean() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//
//	public void test() {
//		System.out.println("Entered bean method");
//	}
//	
//	@PostConstruct
//	public void init()
//	{
//		this.countries= countryService.getAllCountries();
//		if(!(this.countries.isEmpty())) this.country = this.countries.get(0);
//	}
//
//	public List<Country> getCountries() {
//		return countries;
//	}
//
//	public void setCountries(List<Country> countries) {
//		this.countries = countries;
//	}
//
//	public int getSelectedCountryId() {
//		return selectedCountryId;
//	}
//
//	public void setSelectedCountryId(int selectedCountryId) {
//		this.selectedCountryId = selectedCountryId;
//	}
//
//	public Country getCountry() {
//		return country;
//	}
//
//	public void setCountry(Country country) {
//		this.country = country;
//	}
//	
//	
//
//}
