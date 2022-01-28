package app.model;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Trabajador {

	private StringProperty  dni;
	private StringProperty  nombre;
	private StringProperty  apellido1;
	private StringProperty  apellido2;
	private ObjectProperty<LocalDate> fechaNacimiento;
	private StringProperty  nacionalidad;
	private StringProperty  domicilio;
	private StringProperty  ciudad;
	private StringProperty  poblacion;
	private IntegerProperty   cp;
	private StringProperty  nss;
	private StringProperty  email;
	private StringProperty  telefono1;
	private StringProperty  telefono2;
	private StringProperty  cuenta;
	private StringProperty carnet;
	private StringProperty vehiculo;
	private StringProperty permisoTrabajo;
	private StringProperty  discapacidades;

	public Trabajador() {
		this(null,null, null,null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param firstName
	 * @param lastName
	 */
	public Trabajador(String dni, String nombre, String apellido1, String apellido2) {
		this.dni = new SimpleStringProperty(dni);
		this.nombre = new SimpleStringProperty(nombre);
		this.apellido1= new SimpleStringProperty(apellido1);
		this.apellido2= new SimpleStringProperty(apellido2);
		this.fechaNacimiento= new SimpleObjectProperty<LocalDate>(null);
		this.nacionalidad= new SimpleStringProperty("");
		this.domicilio = new SimpleStringProperty("");
		this.ciudad = new SimpleStringProperty("");
		this.poblacion= new SimpleStringProperty("");
		this.cp= new SimpleIntegerProperty(0);
		this.nss = new SimpleStringProperty("");
		this.email = new SimpleStringProperty("");
		this.telefono1= new SimpleStringProperty("");
		this.telefono2= new SimpleStringProperty("");
		this.cuenta= new SimpleStringProperty("");
		this.carnet= new SimpleStringProperty("0");
		this.vehiculo= new SimpleStringProperty("0");
		this.permisoTrabajo= new SimpleStringProperty("0");
		this.discapacidades= new SimpleStringProperty("0");
	}

	public String getDni() {
		return dni.get();
	}
	public void setDni(String dni) {
		this.dni.set(dni);
	}
	public StringProperty dniProperty(){
		return dni;
	}


	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(String nombre) {
		this.nombre.set(nombre);;
	}
	public StringProperty nombreProperty(){
		return nombre;
	}


	public String getApellido1() {
		return apellido1.get();
	}
	public void setApellido1(String apellido1) {
		this.apellido1.set(apellido1);
	}
	public StringProperty apellido1Property(){
		return apellido1;
	}


	public String getApellido2() {
		return apellido2.get();
	}
	public void setApellido2(String apellido2) {
		this.apellido2.set(apellido2);
	}
	public StringProperty apellido2Property(){
		return apellido2;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento.get();
	}
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento.set(fechaNacimiento);
	}
	public ObjectProperty<LocalDate> fechaNacimientoProperty() {

		return fechaNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad.get();
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad.set(nacionalidad);
	}
	public StringProperty nacionalidadProperty(){
		return nacionalidad;
	}

	public String getDomicilio() {
		return domicilio.get();
	}
	public void setDomicilio(String domicilio) {
		this.domicilio.set(domicilio);
	}
	public StringProperty domicilioProperty(){
		return domicilio;
	}

	public String getCiudad() {
		return ciudad.get();
	}
	public void setCiudad(String ciudad) {
		this.ciudad.set(ciudad);
	}
	public StringProperty ciudadProperty(){
		return ciudad;
	}

	public String getPoblacion() {
		return poblacion.get();
	}
	public void setPoblacion(String poblacion) {
		this.poblacion.set(poblacion);
	}
	public StringProperty poblacionProperty(){
		return poblacion;
	}

	public int getCp() {
		return cp.get();
	}
	public void setCp(int cp) {
		this.cp.set(cp);;
	}
	public IntegerProperty cpProperty(){
		return cp;
	}

	public String getnss() {
		return nss.get();
	}
	public void setnss(String nss) {
		this.nss.set(nss);
	}
	public StringProperty nssProperty(){
		return nss;
	}

	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);;
	}
	public StringProperty emailProperty(){
		return email;
	}

	public String getTelefono1() {
		return telefono1.get();
	}
	public void setTelefono1(String telefono1) {
		this.telefono1.set(telefono1);
	}
	public StringProperty telefono1Property(){
		return telefono1;
	}

	public String getTelefono2() {
		return telefono2.get();
	}
	public void setTelefono2(String telefono2) {
		this.telefono2.set(telefono2);
	}
	public StringProperty telefono2Property(){
		return telefono2;
	}

	public String getCuenta() {
		return cuenta.get();
	}
	public void setCuenta(String cuenta) {
		this.cuenta.set(cuenta);;
	}
	public StringProperty cuentaProperty(){
		return cuenta;
	}

	public String getCarnet() {
		return carnet.get();
	}
	public void setCarnet(String carnet) {
		this.carnet.set(carnet);;
	}
	public StringProperty carnetProperty(){
		return carnet;
	}

	public String getVehiculo() {
		return vehiculo.get();
	}
	public void setVehiculo(String vehiculo) {
		this.vehiculo.set(vehiculo);;
	}
	public StringProperty vehiculoProperty(){
		return vehiculo;
	}

	public String getPermisoTrabajo() {
		return permisoTrabajo.get();
	}
	public void setPermisoTrabajo(String permisoTrabajo) {
		this.permisoTrabajo.set(permisoTrabajo);;
	}
	public StringProperty permisoTrabajoProperty(){
		return permisoTrabajo;
	}

	public String getDiscapacidades() {
		return discapacidades.get();
	}
	public void setDiscapacidades(String discapacidades) {
		this.discapacidades.set(discapacidades);;
	}
	public StringProperty discapacidadesProperty(){
		return discapacidades;
	}

}
