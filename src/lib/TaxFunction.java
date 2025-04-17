package lib;

public class TaxFunction {

	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus
	 * dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan
	 * bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan)
	 * dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena
	 * pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah
	 * sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya
	 * ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	private static final int BASIC_PTKP = 54000000;
	private static final int MARRIED_PTKP = 4500000;
	private static final int CHILD_PTKP = 1500000;
	private static final int MAX_CHILDREN = 3;
	private static final double TAX_RATE = 0.05;

	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible,
			boolean isMarried, int numberOfChildren) {

		if (numberOfMonthWorking > 12) {
			System.err.println("More than 12 month working per year");
		}

		int penghasilanTdkKenaPajak = calculatePTKP(isMarried, numberOfChildren);
		int annualIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
		int taxableIncome = annualIncome - deductible - penghasilanTdkKenaPajak;

		int tax = (int) (TAX_RATE * taxableIncome);
		return Math.max(tax, 0);
	}

	private static int calculatePTKP(boolean isMarried, int numberOfChildren) {
		int penghasilanTdkKenaPajak = BASIC_PTKP;

		if (isMarried) {
			penghasilanTdkKenaPajak += MARRIED_PTKP;
		}

		int validChildren = Math.min(numberOfChildren, MAX_CHILDREN);
		penghasilanTdkKenaPajak += validChildren * CHILD_PTKP;

		return penghasilanTdkKenaPajak;
	}
}
