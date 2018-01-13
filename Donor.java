
public class Donor {
	
	private String lastName;
	private String firstName;
	private String envelopeNumber;
	private String address;
	private String city;
	private String state;
	private String zip;
	
	public Donor() {
		this.lastName = "";
		this.firstName = "";
		this.envelopeNumber = "";
		this.address = "";
		this.city = "";
		this.state = "";
		this.zip = "";
	}
	
	public Donor(String lastName, String firstName, String envelopeNumber, String address, String city, String state,
			String zip) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.envelopeNumber = envelopeNumber;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEnvelopeNumber() {
		return envelopeNumber;
	}
	public void setEnvelopeNumber(String envelopeNumber) {
		this.envelopeNumber = envelopeNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
}
