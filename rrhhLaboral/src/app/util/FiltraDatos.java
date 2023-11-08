package app.util;

import app.model.Categoria;
import app.model.Centro;
import app.model.Movimiento;
import app.model.Trabajador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;

import java.sql.SQLException;
import java.time.LocalDate;

public class FiltraDatos {

    private DataBase bbdd;

    public FiltraDatos() {
        bbdd = new DataBase();
    }

    public ObservableList<Movimiento> filtrarMovimientosPorTrabajador(
            int indexNombre,
            ObservableList<Trabajador> trabajadores,
            ObservableList<Movimiento> movimientos) throws SQLException {

        if(indexNombre != -1){
            ObservableList<Movimiento> movimientosFiltrados = FXCollections.observableArrayList();
            Trabajador trabajador = trabajadores.get(indexNombre);
            for (Movimiento mov:movimientos) {
                if(mov.getDni().equals(trabajador.getDni())){
                    movimientosFiltrados.add(mov);
                }
            }
            return movimientosFiltrados;
        }
        return movimientos;
    }

    public ObservableList<Movimiento> filtrarMovimientosPorCategoria(
            int indexCategoria,
            ObservableList<Categoria> categorias,
            ObservableList<Movimiento> movimientos) {

        if(indexCategoria != -1){
            ObservableList<Movimiento> movimientosFiltrados = FXCollections.observableArrayList();
            Categoria categoria = categorias.get(indexCategoria);
            for (Movimiento mov:movimientos) {
                if(mov.getNombreCategoria().equals(categoria.getNombre())){
                    movimientosFiltrados.add(mov);
                }
            }
            return movimientosFiltrados;
        }
        return movimientos;
    }

    public ObservableList<Movimiento> filtrarMovimientosPorCentro(
            int indexCentro,
            ObservableList<Centro> centros,
            ObservableList<Movimiento> movimientos) {

        if(indexCentro != -1){
            ObservableList<Movimiento> movimientosFiltrados = FXCollections.observableArrayList();
            Centro centro = centros.get(indexCentro);
            for (Movimiento mov:movimientos) {
                if(mov.getNombreCentro().equals(centro.getNombre())){
                    movimientosFiltrados.add(mov);
                }
            }
            return movimientosFiltrados;
        }
        return movimientos;
    }

    public ObservableList<Movimiento> filtrarMovimientosPorFechas(
            LocalDate inicio,
            LocalDate fin,
            ObservableList<Movimiento> movimientos) {
        System.out.println(inicio);
        System.out.println(fin);
        if(inicio != null && fin == null){
            System.out.println("entra inicio no nulo fin nulo");
            //Inicio seleccionado y Fin sin seleccionar
            return movimientosDesdeInicio(inicio, movimientos);
        } else if (inicio == null && fin != null){
            System.out.println("entra fin no nulo inicio nulo");
            //Fin seleccionado e Inicio sin seleccionar
            return movimientosHastaFin(fin, movimientos);
        }else if(inicio != null && fin != null){
            System.out.println("entra inicio no nulo fin no nulo");
            //Inicio y fin seleccionados
            ObservableList<Movimiento> desdeInicio = movimientosDesdeInicio(inicio, movimientos);
            return movimientosHastaFin(fin, desdeInicio);
        }
        //Inicio y fin sin seleccionar
        return movimientos;
    }

    //El método compareTo para comparar las fechas devuelve:
    //0: son iguales
    //Valor Positivo: la primera es mayor que la segunda
    //Valor negativo: la primera es menor que la segunda
    public ObservableList<Movimiento> movimientosDesdeInicio(
            LocalDate inicio,
            ObservableList<Movimiento> movimientos){
        ObservableList<Movimiento> desdeInicio = FXCollections.observableArrayList();

        for (Movimiento mov: movimientos) {
            /*System.out.println(mov.getFechaFin());
            System.out.println(inicio);
            System.out.println(mov.getFechaFin().compareTo(inicio));*/
            //ES LA MISMA CONDICIÓN PARA AMBOS TIPOS DE MOVIMIENTO
            if(mov.getFechaFin().compareTo(inicio) >= 0){
                System.out.println("entra");
                desdeInicio.add(mov);
            }
        }
        return desdeInicio;
    }

    public ObservableList<Movimiento> movimientosHastaFin(
            LocalDate fin,
            ObservableList<Movimiento> movimientos){
        ObservableList<Movimiento> hastaFin = FXCollections.observableArrayList();
        for (Movimiento mov: movimientos) {
            if(mov.getFechaFin().compareTo(fin) <= 0){
                hastaFin.add(mov);
            }
        }

        return hastaFin;

    }

    public String devolverTipoMovimiento(String nombreTipoMovimiento){

        if(nombreTipoMovimiento.toUpperCase().equals("LLAMAMIENTO")||
                nombreTipoMovimiento.toUpperCase().equals("ALTA NUEVA")||
                nombreTipoMovimiento.toUpperCase().equals("CAMBIO CATEGORIA")||
                nombreTipoMovimiento.toUpperCase().equals("CAMBIO TIPO CONTRATO")||
                nombreTipoMovimiento.toUpperCase().equals("MODIFICACION")){
            return "ALTA";
        }
        return "BAJA";
    }
}
