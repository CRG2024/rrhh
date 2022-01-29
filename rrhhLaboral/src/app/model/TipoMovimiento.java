package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TipoMovimiento {

	private IntegerProperty   idTipoMovimiento;
	private StringProperty  nombre;

	public TipoMovimiento() {
		this(null);
	}

	public TipoMovimiento(String nombren) {
		this.idTipoMovimiento = new SimpleIntegerProperty(0);
		this.nombre = new SimpleStringProperty("");
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

	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}
	public StringProperty nombreProperty(){
		return nombre;
	}

}
