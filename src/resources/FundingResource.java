package resources;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DBManagement;

public class FundingResource extends Resource {

	private Quantity quantity;

	public FundingResource(Quantity quantity) {
		this.quantity = quantity;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	@Override
	public boolean addToDB() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("FUNDINGRESOURCE", new String[] { "AMOUNT" },
				new String[] { quantity.getUnit().toString() }, new String[] { "UNIT" });
		System.out.println("From FundingResource: " + query);
		ResultSet rs = db.getQuery(query);
		try {
			if (!rs.next()) {
				query = db.generateAddQuery("FUNDINGRESOURCE",
						new String[] { Integer.toString(quantity.getAmount()), quantity.getUnit().toString() });
				return db.update(query);
			} else {
				int amount = rs.getInt("AMOUNT");
				amount += quantity.getAmount();
				query = db.generateUpdateQuery("FUNDINGRESOURCE",
						new String[] { Integer.toString(amount), quantity.getUnit().toString() },
						new String[] { "AMOUNT", "UNIT" }, new String[] { quantity.getUnit().toString() },
						new String[] { "UNIT" });
				return db.update(query);
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean removeFromDB() {
		DBManagement db = new DBManagement();
		String query = db.generateSelectQuery("FUNDINGRESOURCE", new String[] { "AMOUNT" },
				new String[] { quantity.getUnit().toString() }, new String[] { "UNIT" });
		ResultSet rs = db.getQuery(query);
		if (rs == null) {
			return false;
		} else {
			try {
				int amount = rs.getInt("AMOUNT");
				amount -= quantity.getAmount();
				query = db.generateUpdateQuery("FUNDINGRESOURCE",
						new String[] { Integer.toString(amount), quantity.getUnit().toString() },
						new String[] { "AMOUNT", "UNIT" }, new String[] { quantity.getUnit().toString() },
						new String[] { "UNIT" });
				return db.update(query);
			} catch (SQLException e) {
				return false;
			}
		}
	}
}
