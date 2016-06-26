package Items;

import java.sql.ResultSet;
import java.sql.SQLException;

import Data.DatabaseInterface;

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
		DatabaseInterface db = new DatabaseInterface();
		String query = db.generateSelectQuery("FUNDINGRESOURCE", new String[] { "AMOUNT" },
				new String[] { quantity.getUnit().toString() }, new String[] { "UNIT" });
		ResultSet rs = db.getQuery(query);
		if (rs == null) {
			query = db.generateAddQuery("FUNDINGRESOURCE",
					new String[] { Integer.toString(quantity.getAmount()), quantity.getUnit().toString() });
			return db.update(query);
		} else {
			try {
				int amount = rs.getInt("AMOUNT");
				amount += quantity.getAmount();
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

	@Override
	public boolean removeFromDB() {
		DatabaseInterface db = new DatabaseInterface();
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
