package app.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

public class Movimiento {

	private IntegerProperty   idMovimiento;
	private StringProperty  dni;
	private IntegerProperty   idCentro;
	private IntegerProperty   idHorario;
	private ObjectProperty<LocalDate> fechaCreacion;
	private ObjectProperty<LocalDate> fechaInicio;
	private ObjectProperty<LocalDate> fechaFin;
	private IntegerProperty   importeBaja;
	private IntegerProperty   idCategoria;
	private IntegerProperty   idTipoContrato;
	private IntegerProperty   idTipoMovimiento;


	public Movimiento() {
		this(null, null,null, null,null, null, null,null, null,null);
	}


	public Movimiento(Integer idMovimiiento, Trabajador trabajador, TipoMovimiento tipoMovimiento,
			LocalDate fechaCreacion, LocalDate fechaInicio, LocalDate fechaFin, Categoria categoria, TipoContrato tipoContrato,
			Horario horario, Integer importeBaja) {
		this.idMovimiento = new SimpleIntegerProperty(idMovimiiento);
		this.fechaCreacion= new SimpleObjectProperty<LocalDate>(fechaCreacion);
		this.fechaInicio= new SimpleObjectProperty<LocalDate>(fechaInicio);
		this.fechaFin= new SimpleObjectProperty<LocalDate>(fechaFin);
		this.importeBaja= new SimpleIntegerProperty(0);
	}

	public int getIdMovimiento() {
		return idTipoMovimiento.get();
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
	
	public int getIdCentro() {
		return idCentro.get();
	}
	public void setIdCentro(int idCentro) {
		this.idCentro.set(idCentro);;
	}
	public IntegerProperty idCentroProperty(){
		return idCentro;
	}
	
	public int getIdHorario() {
		return idHorario.get();
	}
	public void setIdHorario(int idHorario) {
		this.idHorario.set(idHorario);
	}
	public IntegerProperty idHorarioProperty(){
		return idHorario;
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
	
	public int getIdCategoria() {
		return idCategoria.get();
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria.set(idCategoria);
	}
	public IntegerProperty idCategoriaProperty(){
		return idCategoria;
	}
	
	public int getIdTipoContrato() {
		return idTipoContrato.get();
	}
	public void setIdTipoContrato(int idTipoContrato) {
		this.idTipoContrato.set(idTipoContrato);
	}
	public IntegerProperty idTipoContratoProperty(){
		return idTipoContrato;
	}
	
	public int getIdTipoMovimiento() {
		return idTipoMovimiento.get();
	}
	public void setIdTipoMovimiento(int idTipoMovimiento) {
		this.idTipoMovimiento.set(idTipoMovimiento);
	}
	public IntegerProperty idTipoMovimientoProperty(){
		return idTipoMovimiento;
	}
}
