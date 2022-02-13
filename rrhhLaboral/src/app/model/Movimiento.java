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

}
