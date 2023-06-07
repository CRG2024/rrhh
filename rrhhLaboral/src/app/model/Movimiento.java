package app.model;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

import app.util.DataBase;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movimiento {
	
	private IntegerProperty idMovimiento;
	private StringProperty  dni;
	private StringProperty  nombreTrabajador;
	private StringProperty   nombreCentro;
	private StringProperty   nombreHorario;
	private StringProperty   nombreCategoria;
	private StringProperty   nombreTipoContrato;
	private StringProperty   nombreTipoMovimiento;
	private ObjectProperty<LocalDate> fechaCreacion;
	private ObjectProperty<LocalDate> fechaInicio;
	private ObjectProperty<LocalDate> fechaFin;
	private IntegerProperty   importeBaja;



	//TODO cambiar trabajador por dni
	public Movimiento() {
		this(null, null, null, null, null, null, null, null, null, null);
	}


	public Movimiento(Integer idMovimiento, Trabajador trabajador, Centro centro, Horario horario,
			LocalDate fechaInicio, LocalDate fechaFin, Integer importeBaja, 
			Categoria categoria, TipoContrato tipoContrato, TipoMovimiento tipoMovimiento
			) {
		this.idMovimiento = new SimpleIntegerProperty(0);
		this.dni = new SimpleStringProperty(trabajador.getDni());
		this.nombreTrabajador = new SimpleStringProperty(trabajador.getNombreCompleto());
		this.nombreCentro = new SimpleStringProperty(centro.getNombre());
		this.nombreHorario = new SimpleStringProperty(horario.getNombre());
		this.fechaCreacion= new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.fechaInicio= new SimpleObjectProperty<LocalDate>(fechaInicio);
		this.fechaFin= new SimpleObjectProperty<LocalDate>(fechaFin);
		this.importeBaja= new SimpleIntegerProperty(importeBaja);
		
		this.nombreCategoria = new SimpleStringProperty(categoria.getNombre());
		this.nombreTipoContrato = new SimpleStringProperty(tipoContrato.getNombre());
		this.nombreTipoMovimiento= new SimpleStringProperty(tipoMovimiento.getNombre());
	}

	public int getIdMovimiento() {
		return idMovimiento.get();
	}
	public void setIdMovimiento(int idMovimiento) {
		this.idMovimiento.set(idMovimiento);
	}
	public IntegerProperty idMovimientoProperty(){
		return idMovimiento;
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

	public String getNombreTrabajador() {
		return nombreTrabajador.get();
	}
	public void setNombreTrabajador(String nombreTrabajador) {
		this.nombreTrabajador.set(nombreTrabajador);
	}
	public StringProperty nombreTrabajadorProperty(){
		return nombreTrabajador;
	}

	public String getNombreCentro() {
		return nombreCentro.get();
	}
	public void setNombreCentro(String nombreCentro) {this.nombreCentro.set(nombreCentro); }
	public StringProperty nombreCentroProperty(){
		return nombreCentro;
	}

	public String getNombreHorario() {
		return nombreHorario.get();
	}
	public void setNombreHorario(String nombreHorario) {
		this.nombreHorario.set(nombreHorario);
	}
	public StringProperty nombreHorarioProperty(){
		return nombreHorario;
	}
	
	public LocalDate getFechaCreacion() {
		return fechaCreacion.get();
	}
	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion.set(fechaCreacion);
	}
	public ObjectProperty<LocalDate> fechaCreacionProperty() {
		return fechaCreacion;
	}
	
	public LocalDate getFechaInicio() {
		return fechaInicio.get();
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio.set(fechaInicio);
	}
	public ObjectProperty<LocalDate> fechaInicioProperty() {
		return fechaInicio;
	}
	
	public LocalDate getFechaFin() {
		return fechaFin.get();
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin.set(fechaFin);
	}
	public ObjectProperty<LocalDate> fechaFinProperty() {
		return fechaFin;
	}
	
	public int getImporteBaja() {
		return importeBaja.get();
	}
	public void setImporteBaja(int importeBaja) {
		this.importeBaja.set(importeBaja);;
	}
	public IntegerProperty importeBajaProperty(){
		return importeBaja;
	}
	
	public String getNombreCategoria() {
		return nombreCategoria.get();
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria.set(nombreCategoria);
	}
	public StringProperty nombreCategoriaProperty(){
		return nombreCategoria;
	}
	
	public String getNombreTipoContrato() {
		return nombreTipoContrato.get();
	}
	public void setNombreTipoContrato(String nombreTipoContrato) {
		this.nombreTipoContrato.set(nombreTipoContrato);
	}
	public StringProperty nombreTipoContratoProperty(){
		return nombreTipoContrato;
	}
	
	public String getNombreTipoMovimiento() {
		return nombreTipoMovimiento.get();
	}
	public void setNombreTipoMovimiento(String nombreTipoMovimiento) {this.nombreTipoMovimiento.set(nombreTipoMovimiento);
	}
	public StringProperty nombreTipoMovimientoProperty(){
		return nombreTipoMovimiento;
	}

}
