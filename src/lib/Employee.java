package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {
	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;

	private int yearJoined;
	private int monthJoined;
	private int dayJoined;
	private int monthWorkingInYear;

	private boolean isForeigner;
	private Gender gender; // true = Laki-laki, false = Perempuan

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private String spouseName;
	private String spouseIdNumber;

	private List<Child> children;

	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
			int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, Gender gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;

		children = new LinkedList<>();
	}

	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya
	 * (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3:
	 * 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */

	public void setMonthlySalary(int grade) {
		int baseSalary = 0;

		switch (grade) {
			case 1:
				baseSalary = 3000000;
				break;
			case 2:
				baseSalary = 5000000;
				break;
			case 3:
				baseSalary = 7000000;
				break;
			default:
				throw new IllegalArgumentException("Invalid grade: " + grade);
		};

		if (isForeigner) {
			baseSalary *= 1.5;
		}
		monthlySalary = (int) baseSalary;
	}

	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}

	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = idNumber;
	}

	public void addChild(String name, String idNumber) {
		children.add(new Child(name, idNumber));
	}

	// Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah
	// bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
	private int calculateMonthsWorkedThisYear() {
		LocalDate date = LocalDate.now();

		if (date.getYear() == yearJoined) {
			return date.getMonthValue() - monthJoined + 1;
		} else {
			return 12;
		}
	}
	
	public int getAnnualIncomeTax() {
		int monthsWorked = calculateMonthsWorkedThisYear();
		boolean hasSpouse = !spouseIdNumber.isEmpty();

		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthsWorked, annualDeductible,
				hasSpouse, children.size());
	}

	public List<Child> getChildren() {
		return children;
	}
}