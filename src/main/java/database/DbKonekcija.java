package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton klasa - obezbedjuje jednu jedinu konekciju ka MySQL bazi (isti princip kao na vezbama).
// Razlika u odnosu na verziju sa vezbi: ako prvi pokusaj konekcije ne uspe
// (npr. MySQL jos nije bio pokrenut), sledeci poziv getInstance() ce
// ponovo pokusati da se poveze, umesto da zauvek ostane sa db=null.
public class DbKonekcija {

	private String url = "jdbc:mysql://localhost:3306/apoteka";
	private String user = "root";
	private String password = "root";
	private static DbKonekcija instance = null;
	private Connection db = null;

	private DbKonekcija() {
		povezniSe();
	}

	private void povezniSe() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver je ucitan!");

			db = DriverManager.getConnection(url, user, password);

			if (db == null)
				System.out.println("Nema konekcije!");
			else
				System.out.println("Ima konekcije!");

		} catch (ClassNotFoundException e) {
			System.out.println("GRESKA " + e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DbKonekcija getInstance() {
		if (instance == null) {
			instance = new DbKonekcija();
		} else {
			// ako prethodna konekcija ne postoji ili je u medjuvremenu
			// prekinuta, probamo ponovo da se povezemo umesto da vratimo
			// zauvek "mrtvu" konekciju
			try {
				if (instance.db == null || instance.db.isClosed()) {
					instance.povezniSe();
				}
			} catch (SQLException e) {
				instance.povezniSe();
			}
		}
		return instance;
	}

	public void zatvoriKonekciju() {
		try {
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getDb() {
		return db;
	}
}
