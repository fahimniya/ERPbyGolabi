package resources;

public class Quantity {

	private int amount;
	private Unit unit;
	
	public Quantity(int amount, Unit unit) {
		this.amount = amount;
		this.unit = unit;
	}
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

}
