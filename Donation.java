
public class Donation implements Comparable {
	
	Donor donor;
	String category;
	String designation;
	String description;
	Double amount;
	
	public Donation(Donor donor, String category, String designation, 
					String description, Double amount) {
		super();
		this.donor = donor;
		this.category = category;
		this.designation = designation;
		this.description = description;
		this.amount = amount;
	}
	
	public Donation(Donor donor) {
		super();
		this.donor=donor;
		category="";
		designation="";
		description="";
		amount=0.0;
	}
	
	public Donation() {
		super();
		this.donor=null;
		category="";
		designation="";
		description="";
		amount=0.0;	
	}
	
	@Override
    public int compareTo(Object compareD) {
		String thisd = this.getDescription();
		String otherd = ((Donation)compareD).getDescription();
        int compareDescription=thisd.compareToIgnoreCase(otherd);
        return compareDescription;
    }
	
	public Donor getDonor() {
		return donor;
	}
	public void setDonor(Donor donor) {
		this.donor = donor;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		designation = designation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}


}
