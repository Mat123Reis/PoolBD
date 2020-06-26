package poolBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static int limiteDeInstancia = 5;
	private static int atual = 0;
	private static PoolBD[] instancias;
	private Connection connection;
	protected static String urlBD = "urlBD";

	public void PoolBD() {
	}

	public static void criarInstancia() {
		instancias = new PoolBD[limiteDeInstancia];

		for (int i = 0; i < limiteDeInstancia; i++) {
			instancias[i] = new PoolBD();
		}
	}

	public void conectar() throws SQLException {
		if (this.connection == null || this.connection.isClosed()) {
			this.connection = DriverManager.getConnection(urlBD);
		}
	}

	public static PoolBD pegarInstancia() throws SQLException {
		if (instancias == null) {
			criarInstancia();
		}

		atual = (atual + 1) % limiteDeInstancia;
		PoolBD instancia = instancias[atual];
		instancia.conectar();

		return instancia;
	}
}
