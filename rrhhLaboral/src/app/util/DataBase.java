package app.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.model.Categoria;
import app.model.Centro;
import app.model.Horario;
import app.model.TipoContrato;
import app.model.TipoMovimiento;
import app.model.Trabajador;

public class DataBase {

	private Connection connection;
	public DataBase(){
		iniciarConexion();
	}

	private void iniciarConexion() {
		try {
		    Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
		    System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
		}

		connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/rrhhLaboral",
			        "postgres", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Trabajador> obtenerDatosTrabajadores() throws SQLException{
		ObservableList<Trabajador> datosTrabajadores = FXCollections.observableArrayList();
		 String query = "SELECT * FROM datosempleados";
	     java.sql.Statement st = connection.createStatement();
	     ResultSet rs = st.executeQuery(query);

	     while (rs.next())
	      {
	    	Trabajador worker = new Trabajador();
	    	worker.setDni(rs.getString("dni"));
	    	worker.setNombre(rs.getString("nombre"));
	    	worker.setApellido1(rs.getString("apellido1"));
	    	worker.setApellido2(rs.getString("apellido2"));
	    	if (rs.getDate("fechaNacimiento") == null){
	    		worker.setFechaNacimiento(null);
	    	}else{
	    		worker.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
	    	}

	    	worker.setNacionalidad(rs.getString("nacionalidad"));
	    	worker.setDomicilio(rs.getString("domicilio"));
	    	worker.setCiudad(rs.getString("ciudad"));
	    	worker.setPoblacion(rs.getString("poblacion"));
	    	worker.setCp(rs.getInt("cp"));
	    	worker.setnss(rs.getString("nss"));
	    	worker.setEmail(rs.getString("email"));
	    	worker.setTelefono1(rs.getString("telefono1"));
	    	worker.setTelefono2(rs.getString("telefono2"));
	    	worker.setCuenta(rs.getString("cuenta"));
	    	worker.setCarnet(rs.getString("carnet"));
	    	worker.setVehiculo(rs.getString("vehiculo"));
	    	worker.setPermisoTrabajo(rs.getString("permisoTrabajo"));
	    	worker.setDiscapacidades(rs.getString("discapacidades"));
	    	datosTrabajadores.add(worker);
	      }
		return datosTrabajadores;
	}

	public void insertarTrabajador(Trabajador trabajador) {
		// TODO Auto-generated method stub
		try {
			final PreparedStatement pstmt = connection.prepareStatement(
					"INSERT INTO datosempleados (dni, nombre, apellido1, apellido2, fechanacimiento, nacionalidad, domicilio, ciudad, poblacion, cp,"
							+ "nss, email, telefono1, telefono2, cuenta,carnet, vehiculo, permisotrabajo, discapacidades) "
							+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1, trabajador.getDni());
				pstmt.setString(2, trabajador.getNombre());
				pstmt.setString(3, trabajador.getApellido1());
				pstmt.setString(4, trabajador.getApellido2());
				if(trabajador.getFechaNacimiento()==null){
					pstmt.setDate(5, Date.valueOf(LocalDate.of(1900, 1, 1)));
				}else{
					pstmt.setDate(5, Date.valueOf(trabajador.getFechaNacimiento()));
				}
				pstmt.setString(6, trabajador.getNacionalidad());
				pstmt.setString(7, trabajador.getDomicilio());
				pstmt.setString(8, trabajador.getCiudad());
				pstmt.setString(9, trabajador.getPoblacion());
				pstmt.setLong(10, trabajador.getCp());
				pstmt.setString(11, trabajador.getnss());
				pstmt.setString(12, trabajador.getEmail());
				pstmt.setString(13, trabajador.getTelefono1());
				pstmt.setString(14, trabajador.getTelefono2());
				pstmt.setString(15, trabajador.getCuenta());
				pstmt.setString(16, trabajador.getCarnet());
				pstmt.setString(17, trabajador.getVehiculo());
				pstmt.setString(18, trabajador.getPermisoTrabajo());
				pstmt.setString(19, trabajador.getDiscapacidades());
				pstmt.executeQuery();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void eliminarTrabajador(String dni) throws SQLException{
		java.sql.Statement st = connection.createStatement();
		String sql = String.format("DELETE FROM datosempleados WHERE dni='%s'",dni);
		st.execute(sql);
	}

	public void actualizarTrabajador(Trabajador trabajador, String dni) throws SQLException{

		try {
			final PreparedStatement pstmt = connection.prepareStatement(
					"UPDATE datosempleados SET dni=?, nombre=?, apellido1=?, apellido2=?, fechanacimiento=?, nacionalidad=?, domicilio=?, ciudad=?, poblacion=?, cp=?,"
							+ "nss=?, email=?, telefono1=?, telefono2=?, cuenta=?, carnet=?, vehiculo=?, permisotrabajo=?, discapacidades=? WHERE dni=?");
				pstmt.setString(1, trabajador.getDni());
				pstmt.setString(2, trabajador.getNombre());
				pstmt.setString(3, trabajador.getApellido1());
				pstmt.setString(4, trabajador.getApellido2());
				if(trabajador.getFechaNacimiento()==null){
					pstmt.setDate(5, Date.valueOf(LocalDate.of(1900, 1, 1)));
				}else{
					pstmt.setDate(5, Date.valueOf(trabajador.getFechaNacimiento()));
				}
				pstmt.setString(6, trabajador.getNacionalidad());
				pstmt.setString(7, trabajador.getDomicilio());
				pstmt.setString(8, trabajador.getCiudad());
				pstmt.setString(9, trabajador.getPoblacion());
				pstmt.setLong(10, trabajador.getCp());
				pstmt.setString(11, trabajador.getnss());
				pstmt.setString(12, trabajador.getEmail());
				pstmt.setString(13, trabajador.getTelefono1());
				pstmt.setString(14, trabajador.getTelefono2());
				pstmt.setString(15, trabajador.getCuenta());
				pstmt.setString(16, trabajador.getCarnet());
				pstmt.setString(17, trabajador.getVehiculo());
				pstmt.setString(18, trabajador.getPermisoTrabajo());
				pstmt.setString(19, trabajador.getDiscapacidades());
				pstmt.setString(20, trabajador.getDni());
				pstmt.executeQuery();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ObservableList<Centro> obtenerDatosCentros() throws SQLException {

		ObservableList<Centro> datosCentros = FXCollections.observableArrayList();
		 String query = "SELECT * FROM centros";
	     java.sql.Statement st = connection.createStatement();
	     ResultSet rs = st.executeQuery(query);

	     while (rs.next())
	      {
	    	Centro centro = new Centro();
	    	centro.setIdCentro(rs.getInt("idcentro"));
	    	centro.setNombre(rs.getString("nombre"));
	    	centro.setDireccion(rs.getString("direccion"));
	    	centro.setCiudad(rs.getString("ciudad"));
	    	centro.setCp(rs.getInt("cp"));

	    	datosCentros.add(centro);
	      }
		return datosCentros;
	}

	public void eliminarCentro(int idCentro) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement st = connection.createStatement();
		String sql = String.format("DELETE FROM centros WHERE idcentro='%s'",idCentro);
		st.execute(sql);
	}

	public void actualizarCentro(Centro centro, int idCentro) {
		// TODO Auto-generated method stub
		try {
			final PreparedStatement pstmt = connection.prepareStatement(
					"UPDATE centros SET nombre=?, direccion=?, cp=?, ciudad=? WHERE idcentro=?");
				pstmt.setString(1, centro.getNombre());
				pstmt.setString(2, centro.getDireccion());
				pstmt.setLong(3, centro.getCp());
				pstmt.setString(4, centro.getCiudad());
				pstmt.setLong(5, centro.getIdCentro());
				pstmt.executeQuery();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertarCentro(Centro centro) {
		// TODO Auto-generated method stub
		try {
			final PreparedStatement pstmt = connection.prepareStatement(
					"INSERT INTO centros (nombre, direccion, cp, ciudad) "
							+ "VALUES (?,?,?,?)");
				pstmt.setString(1, centro.getNombre());
				pstmt.setString(2, centro.getDireccion());
				pstmt.setLong(3, centro.getCp());
				pstmt.setString(4, centro.getCiudad());
				pstmt.executeQuery();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ObservableList<Categoria> obtenerDatosCategorias() throws SQLException {
		ObservableList<Categoria> datosCategorias = FXCollections.observableArrayList();
		 String query = "SELECT * FROM categoriasprofesionales";
	     java.sql.Statement st = connection.createStatement();
	     ResultSet rs = st.executeQuery(query);

	     while (rs.next())
	      {
	    	Categoria categoria = new Categoria();
	    	categoria.setIdCategoria(rs.getInt("idcategoria"));
	    	categoria.setNombre(rs.getString("nombre"));
	    	categoria.setDescripcion(rs.getString("descripcion"));

	    	datosCategorias.add(categoria);
	      }

		return datosCategorias;
	}

	public void eliminarCategoria(int idCategoria) throws SQLException {
		// // TODO Auto-generated method stub
		java.sql.Statement st = connection.createStatement();
		String sql = String.format("DELETE FROM categoriasprofesionales WHERE idcategoria='%s'",idCategoria);
		st.execute(sql);

	}

	public void actualizarCategoria(Categoria categoria, int idCategoria) {
		// TODO Auto-generated method stub
		try {
			final PreparedStatement pstmt = connection.prepareStatement(
					"UPDATE categoriasprofesionales SET nombre=?, descripcion=? WHERE idcategoria=?");
				pstmt.setString(1, categoria.getNombre());
				pstmt.setString(2, categoria.getDescripcion());
				pstmt.setLong(3, categoria.getIdCategoria());
				pstmt.executeQuery();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		try {
			final PreparedStatement pstmt = connection.prepareStatement(
					"INSERT INTO categoriasprofesionales (nombre, descripcion) "
							+ "VALUES (?,?)");
				pstmt.setString(1, categoria.getNombre());
				pstmt.setString(2, categoria.getDescripcion());
				pstmt.executeQuery();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ObservableList<TipoContrato> obtenerDatosContratos() throws SQLException {
		ObservableList<TipoContrato> datosContratos = FXCollections.observableArrayList();
		 String query = "SELECT * FROM tipocontratos";
	     java.sql.Statement st = connection.createStatement();
	     ResultSet rs = st.executeQuery(query);

	     while (rs.next())
	      {
	    	 TipoContrato contrato = new TipoContrato();
	    	 contrato.setIdTipoContrato(rs.getInt("idtipocontrato"));
	    	 contrato.setNombre(rs.getString("nombre"));
	    	 contrato.setCodContrato(rs.getString("codcontrato"));

	    	 datosContratos.add(contrato);
	      }
		return datosContratos;
	}

	public void actualizarContrato(TipoContrato contrato, int idTipoContrato) {
		// TODO Auto-generated method stub
		try {
			final PreparedStatement pstmt = connection.prepareStatement(
							"UPDATE tipocontratos SET nombre=?, codcontrato=? WHERE idtipocontrato=?");
			pstmt.setString(1, contrato.getNombre());
			pstmt.setString(2, contrato.getCodContrato());
			pstmt.setLong(3, contrato.getIdTipoContrato());
			pstmt.executeQuery();


		} catch (SQLException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void insertarContrato(TipoContrato contrato) {
		// TODO Auto-generated method stub
		try {
			final PreparedStatement pstmt = connection.prepareStatement(
							"INSERT INTO tipocontratos (nombre, codcontrato) "
									+ "VALUES (?,?)");
			pstmt.setString(1, contrato.getNombre());
			pstmt.setString(2, contrato.getCodContrato());
			pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void eliminarContrato(int idTipoContrato) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement st = connection.createStatement();
		String sql = String.format("DELETE FROM tipocontratos WHERE idtipocontrato='%s'",idTipoContrato);
		st.execute(sql);
	}

	public ObservableList<Horario> obtenerDatosHorarios() throws SQLException {
		ObservableList<Horario> datosHorarios = FXCollections.observableArrayList();
		 String query = "SELECT * FROM horarios";
	     java.sql.Statement st = connection.createStatement();
	     ResultSet rs = st.executeQuery(query);

	     while (rs.next())
	      {
	    	 Horario horario = new Horario();
	    	 horario.setIdHorario(rs.getInt("idhorario"));
	    	 horario.setNombre(rs.getString("nombre"));
	    	 horario.setHorario(rs.getString("horario"));
	    	 horario.setHoras(rs.getString("horassemana"));

	    	 datosHorarios.add(horario);
	      }
		return datosHorarios;
	}

	public void eliminarHorario(int idHorario) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement st = connection.createStatement();
		String sql = String.format("DELETE FROM horarios WHERE idhorario='%s'",idHorario);
		st.execute(sql);
	}

	public void actualizarHorario(Horario horario, int idHorario) {
		// TODO Auto-generated method stub
				try {
					final PreparedStatement pstmt = connection.prepareStatement(
									"UPDATE horarios SET nombre=?, horario=?, horassemana=? WHERE idhorario=?");
					pstmt.setString(1, horario.getNombre());
					pstmt.setString(2, horario.getHorario());
					pstmt.setString(3, horario.getHoras());
					pstmt.setLong(4, horario.getIdHorario());
					pstmt.executeQuery();


				} catch (SQLException e) {
							// TODO Auto-generated catch block
					e.printStackTrace();
				}

	}

	public void insertarHorario(Horario horario) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				try {
					final PreparedStatement pstmt = connection.prepareStatement(
									"INSERT INTO horarios (nombre, horario, horassemana) "
											+ "VALUES (?,?,?)");
					pstmt.setString(1, horario.getNombre());
					pstmt.setString(2, horario.getHorario());
					pstmt.setString(3, horario.getHoras());
					pstmt.executeQuery();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	public ObservableList<TipoMovimiento> obtenerDatosTipoMovimientos() throws SQLException {
		ObservableList<TipoMovimiento> datosMovimientos = FXCollections.observableArrayList();
		 String query = "SELECT * FROM tipomovimientos";
	     java.sql.Statement st = connection.createStatement();
	     ResultSet rs = st.executeQuery(query);

	     while (rs.next())
	      {
	    	 TipoMovimiento movimiento = new TipoMovimiento();
	    	 movimiento.setIdTipoMovimiento(rs.getInt("idtipomovimiento"));
	    	 movimiento.setNombre(rs.getString("nombre"));

	    	 datosMovimientos.add(movimiento);
	      }
		return datosMovimientos;
	}

	public void eliminarTipoMovimiento(int idTipoMovimiento) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement st = connection.createStatement();
		String sql = String.format("DELETE FROM tipomovimientos WHERE idtipomovimiento='%s'",idTipoMovimiento);
		st.execute(sql);
	}

	public void actualizarTipoMovimiento(TipoMovimiento tipoMovimiento, int idTipoMovimiento) {
		try {
		final PreparedStatement pstmt = connection.prepareStatement(
				"UPDATE tipomovimientos SET nombre=? WHERE idtipomovimiento=?");
		pstmt.setString(1, tipoMovimiento.getNombre());
		pstmt.setLong(2, tipoMovimiento.getIdTipoMovimiento());
		pstmt.executeQuery();
		
		
		} catch (SQLException e) {
				// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
	}

	public void insertarTipoMovimiento(TipoMovimiento tipoMovimiento) {
		try {
			final PreparedStatement pstmt = connection.prepareStatement(
							"INSERT INTO tipomovimientos (nombre) "
									+ "VALUES (?)");
			pstmt.setString(1, tipoMovimiento.getNombre());
			pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public TipoMovimiento obtenerIdTipoMovimiento( TipoMovimiento tipoMovimiento) throws SQLException {
//		String query = "SELECT * FROM tipomovimientos WHERE nombre='"+tipoMovimiento+"'";
//	    java.sql.Statement st = connection.createStatement();
//	    ResultSet rs = st.executeQuery(query);
//	    
//	    TipoMovimiento movimiento = new TipoMovimiento();
//	    while (rs.next())
//	    {
//	    	movimiento.setIdTipoMovimiento(rs.getInt("idtipomovimiento"));
//	    	movimiento.setNombre(rs.getString("nombre"));
//	    }
//		return movimiento;
//		
//	}


}
