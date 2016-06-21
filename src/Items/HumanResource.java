package Items;

public class HumanResource extends Resource {
	private String Fname, Lname;
	private Degree degree;

	public HumanResource(String Fname, String Lname, String degree,
			String specialty) {
		this.Fname = Fname;
		this.Lname = Lname;
		this.degree = new Degree(degree, specialty);
	}

	@Override
	public boolean add() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	class Degree {
		private String degree, specialty;

		public Degree(String degree, String specialty) {
			this.degree = degree;
			this.specialty = specialty;
		}

		public String getDegree() {
			return degree;
		}

		public String getSpecialty() {
			return specialty;
		}

	}

}
