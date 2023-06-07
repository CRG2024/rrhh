package app.util;

import java.io.*;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Font;
import app.model.Movimiento;
import app.model.TipoMovimiento;
import app.model.Trabajador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExcelCreator {

	private Font boldunderlineFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD|Font.UNDERLINE);
    private Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font timesFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    
    private DataBase bbdd;
    
	public ExcelCreator() {
		bbdd = new DataBase();
        crearCarpeta();
	}


    public void crearCarpeta(){
        File directorio = new File("src/LlamamientosDoc/");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }

	public void crearExcelNuevasAltas() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Altas");
        FileOutputStream fileOut = new FileOutputStream("src/LlamamientosDoc/ALTAS.xlsx");
        workbook.write(fileOut);
        fileOut.close();
	}
	
	private void añadirAltas(ObservableList<Movimiento> movimientosAltas)throws IOException, SQLException {
		
		FileInputStream fsIP = new FileInputStream(new File("src/LlamamientosDoc/ALTAS.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        
        XSSFSheet spreadsheet = workbook.getSheet("Altas");
        
        ObservableList<String> tipoMovmientosLista = FXCollections.observableArrayList();
        tipoMovmientosLista.add("Alta/Baja/Modificacion");
        ObservableList<String> nombresLista = FXCollections.observableArrayList();
        nombresLista.add("Nombre");
        ObservableList<String> dniLista = FXCollections.observableArrayList();
        dniLista.add("Dni");
        ObservableList<String> nssLista = FXCollections.observableArrayList();
        nssLista.add("N�SS");
        ObservableList<String> fechaNacimientoLista = FXCollections.observableArrayList();
        fechaNacimientoLista.add("Fecha Nacimiento");
        ObservableList<String> domicilioLista = FXCollections.observableArrayList();
        domicilioLista.add("Domicilio");
        ObservableList<String> ciudadLista = FXCollections.observableArrayList();
        ciudadLista.add("Ciudad");
        ObservableList<String> nacionalidadLista = FXCollections.observableArrayList();
        nacionalidadLista.add("Nacionalidad");
        ObservableList<String> fechaInicioLista = FXCollections.observableArrayList();
        fechaInicioLista.add("Fecha Inicio");
        ObservableList<String> fechaFinLista = FXCollections.observableArrayList();
        fechaFinLista.add("Fecha Fin");
        ObservableList<String> centrosLista = FXCollections.observableArrayList();
        centrosLista.add("Centro");
        ObservableList<String> categoriasLista = FXCollections.observableArrayList();
        categoriasLista.add("Categoria");
        ObservableList<String> horariosLista = FXCollections.observableArrayList();
        horariosLista.add("Horario");
        
        
        
        for (int i = 0; i < movimientosAltas.size() ; i++) {

            Movimiento movActual = movimientosAltas.get(i);

            tipoMovmientosLista.add(movActual.getNombreTipoMovimiento());

            Trabajador trabaj= bbdd.obtenerTrabajador(movimientosAltas.get(i).getDni());
            nombresLista.add( movActual.getNombreTrabajador());

            dniLista.add(trabaj.getDni());
            
            nssLista.add(trabaj.getnss());
            
            fechaNacimientoLista.add(trabaj.getFechaNacimiento().toString());
            
            domicilioLista.add(trabaj.getDomicilio());
            
            ciudadLista.add(trabaj.getCiudad());
            
            nacionalidadLista.add(trabaj.getNacionalidad());
            
            fechaInicioLista.add(movActual.getFechaInicio().toString());
            
            fechaFinLista.add(movActual.getFechaFin().toString());
            
            centrosLista.add(movActual.getNombreCentro());
            
            categoriasLista.add(movActual.getNombreCategoria());
            
            horariosLista.add(bbdd.obtenerHorarioPorNombre(movActual.getNombreHorario()).getHorario());
            
		}

        XSSFRow rowTipoMovimiento = spreadsheet.createRow(0);
        crearFilas(workbook, rowTipoMovimiento, tipoMovmientosLista);
        
        
        XSSFRow rowNombre = spreadsheet.createRow(1);
        crearFilas(workbook, rowNombre, nombresLista);
        
        XSSFRow rowDni = spreadsheet.createRow(2);
        crearFilas(workbook, rowDni, dniLista);
        
        XSSFRow rowNSS = spreadsheet.createRow(3);
        crearFilas(workbook, rowNSS, nssLista);
        
        XSSFRow rowFechaNac = spreadsheet.createRow(4);
        crearFilas(workbook, rowFechaNac, fechaNacimientoLista);
        
        XSSFRow rowDomicilio = spreadsheet.createRow(5);
        crearFilas(workbook, rowDomicilio, domicilioLista);
        
        XSSFRow rowCiudad = spreadsheet.createRow(6);
        crearFilas(workbook, rowCiudad, ciudadLista);
        
        XSSFRow rowNacionalidad = spreadsheet.createRow(7);
        crearFilas(workbook, rowNacionalidad, nacionalidadLista);
        
        XSSFRow rowFechaInicio = spreadsheet.createRow(8);
        crearFilas(workbook, rowFechaInicio, fechaInicioLista);
        
        XSSFRow rowFechaFin = spreadsheet.createRow(9);
        crearFilas(workbook, rowFechaFin, fechaFinLista);
        
        XSSFRow rowCentro = spreadsheet.createRow(10);
        crearFilas(workbook, rowCentro, centrosLista);
        
        XSSFRow rowCategoria = spreadsheet.createRow(11);
        crearFilas(workbook, rowCategoria, categoriasLista);
        
        XSSFRow rowHorario = spreadsheet.createRow(12);
        crearFilas(workbook, rowHorario, horariosLista);
        
        
        for (int i = 0; i < nombresLista.size(); i++){
        	spreadsheet.autoSizeColumn(i);
        }
        fsIP.close();
        FileOutputStream output = new FileOutputStream(new File("src/LlamamientosDoc/ALTAS.xlsx"));
        workbook.write(output);
        output.close();
	}


	
	private void crearFilas(XSSFWorkbook workbook, XSSFRow row, ObservableList<String> contenidoFIla) {
		
		for (int i= 0; i< contenidoFIla.size(); i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(contenidoFIla.get(i));
			
	        XSSFCellStyle my_style = workbook.createCellStyle();
	        my_style.setWrapText(true);
	        if(i==0) {
	        	XSSFFont my_font= workbook.createFont();
		        my_font.setBold(true);
		        my_style.setFont(my_font);
	        }
	        my_style.setAlignment(HorizontalAlignment.CENTER);
	        my_style.setVerticalAlignment(VerticalAlignment.CENTER);
	        cell.setCellStyle(my_style);
		}
	}
	
	
	public void crearExcelLlamamientos() throws IOException {

        File directorio = new File("/src/LlamamientosDoc");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Llamamientos");     
        FileOutputStream fileOut = new FileOutputStream("src/LlamamientosDoc/LLAMAMIENTOS.xlsx");
        workbook.write(fileOut);
        fileOut.close();
		
	}
	
	private void añadirLlamamientos(ObservableList<Movimiento> movimientosLlamamientos)throws IOException, SQLException {
		
		FileInputStream fsIP = new FileInputStream(new File("src/LlamamientosDoc/LLAMAMIENTOS.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        
        XSSFSheet spreadsheet = workbook.getSheet("Llamamientos");
        
        ObservableList<String> tipoMovmientosLista = FXCollections.observableArrayList();
        tipoMovmientosLista.add("Movimiento");
        ObservableList<String> nombresLista = FXCollections.observableArrayList();
        nombresLista.add("Nombre");
        ObservableList<String> dniLista = FXCollections.observableArrayList();
        dniLista.add("Dni");
        ObservableList<String> fechaInicioLista = FXCollections.observableArrayList();
        fechaInicioLista.add("Fecha Inicio");
        ObservableList<String> fechaFinLista = FXCollections.observableArrayList();
        fechaFinLista.add("Fecha Fin");
        ObservableList<String> centrosLista = FXCollections.observableArrayList();
        centrosLista.add("Centro");
        ObservableList<String> categoriasLista = FXCollections.observableArrayList();
        categoriasLista.add("Categoria");
        ObservableList<String> horariosLista = FXCollections.observableArrayList();
        horariosLista.add("Horario");
        
        
        
        for (int i = 0; i < movimientosLlamamientos.size() ; i++) {

            Movimiento movActual = movimientosLlamamientos.get(i);
        	tipoMovmientosLista.add(movActual.getNombreTipoMovimiento());
        	
            Trabajador trabaj= bbdd.obtenerTrabajador(movActual.getDni());
            nombresLista.add(trabaj.getApellido1()+" " + trabaj.getApellido2()+", "+ trabaj.getNombre());
            
            dniLista.add(trabaj.getDni());
            
            fechaInicioLista.add(movimientosLlamamientos.get(i).getFechaInicio().toString());
            
            fechaFinLista.add(movimientosLlamamientos.get(i).getFechaFin().toString());
            
            centrosLista.add(movActual.getNombreCentro());
            
            categoriasLista.add(movActual.getNombreCategoria());
            
            horariosLista.add(bbdd.obtenerHorarioPorNombre(movActual.getNombreHorario()).getHorario());
            
		}

        XSSFRow rowTipoMovimiento = spreadsheet.createRow(0);
        crearFilas(workbook, rowTipoMovimiento, tipoMovmientosLista);
        
        
        XSSFRow rowNombre = spreadsheet.createRow(1);
        crearFilas(workbook, rowNombre, nombresLista);
        
        XSSFRow rowDni = spreadsheet.createRow(2);
        crearFilas(workbook, rowDni, dniLista);
        
        XSSFRow rowFechaInicio = spreadsheet.createRow(3);
        crearFilas(workbook, rowFechaInicio, fechaInicioLista);
        
        XSSFRow rowFechaFin = spreadsheet.createRow(4);
        crearFilas(workbook, rowFechaFin, fechaFinLista);
        
        XSSFRow rowCentro = spreadsheet.createRow(5);
        crearFilas(workbook, rowCentro, centrosLista);
        
        XSSFRow rowCategoria = spreadsheet.createRow(6);
        crearFilas(workbook, rowCategoria, categoriasLista);
        
        XSSFRow rowHorario = spreadsheet.createRow(7);
        crearFilas(workbook, rowHorario, horariosLista);
        
        
        for (int i = 0; i < nombresLista.size(); i++){
        	spreadsheet.autoSizeColumn(i);
        }
        fsIP.close();
        FileOutputStream output = new FileOutputStream(new File("src/LlamamientosDoc/LLAMAMIENTOS.xlsx"));
        workbook.write(output);
        output.close();
	}
	
	public void crearExcelModiificaciones() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Modificaciones");
        FileOutputStream fileOut = new FileOutputStream("src/LlamamientosDoc/MODIFICACIONES.xlsx");
        workbook.write(fileOut);
        fileOut.close();
		
	}
	
	private void añadirModificaciones(ObservableList<Movimiento> movimientosModificaciones)throws IOException, SQLException {
		
		FileInputStream fsIP = new FileInputStream(new File("src/LlamamientosDoc/MODIFICACIONES.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        
        XSSFSheet spreadsheet = workbook.getSheet("Modificaciones");
        
        ObservableList<String> tipoMovmientosLista = FXCollections.observableArrayList();
        tipoMovmientosLista.add("Movimiento");
        ObservableList<String> nombresLista = FXCollections.observableArrayList();
        nombresLista.add("Nombre");
        ObservableList<String> dniLista = FXCollections.observableArrayList();
        dniLista.add("Dni");
        ObservableList<String> fechaInicioLista = FXCollections.observableArrayList();
        fechaInicioLista.add("Fecha Inicio");
        ObservableList<String> fechaFinLista = FXCollections.observableArrayList();
        fechaFinLista.add("Fecha Fin");
        ObservableList<String> centrosLista = FXCollections.observableArrayList();
        centrosLista.add("Centro");
        ObservableList<String> categoriasLista = FXCollections.observableArrayList();
        categoriasLista.add("Categoria");
        ObservableList<String> tipoContratoLista = FXCollections.observableArrayList();
        categoriasLista.add("Conversion A");
        ObservableList<String> horariosLista = FXCollections.observableArrayList();
        horariosLista.add("Horario");
        
        
        
        for (int i = 0; i < movimientosModificaciones.size() ; i++) {
            Movimiento movActual = movimientosModificaciones.get(i);
        	tipoMovmientosLista.add(movActual.getNombreTipoMovimiento());
        	
            Trabajador trabaj= bbdd.obtenerTrabajador(movActual.getDni());
            nombresLista.add(trabaj.getApellido1()+" " + trabaj.getApellido2()+", "+ trabaj.getNombre());
            
            dniLista.add(trabaj.getDni());
            
            fechaInicioLista.add(movActual.getFechaInicio().toString());
            
            fechaFinLista.add(movActual.getFechaFin().toString());
            
            centrosLista.add(movActual.getNombreCentro());
            
            categoriasLista.add(movActual.getNombreCategoria());
            
            tipoContratoLista.add(movActual.getNombreTipoContrato());
            
            horariosLista.add(bbdd.obtenerHorarioPorNombre(movActual.getNombreHorario()).getHorario());
            
		}

        XSSFRow rowTipoMovimiento = spreadsheet.createRow(0);
        crearFilas(workbook, rowTipoMovimiento, tipoMovmientosLista);
        
        
        XSSFRow rowNombre = spreadsheet.createRow(1);
        crearFilas(workbook, rowNombre, nombresLista);
        
        XSSFRow rowDni = spreadsheet.createRow(2);
        crearFilas(workbook, rowDni, dniLista);
        
        XSSFRow rowFechaInicio = spreadsheet.createRow(3);
        crearFilas(workbook, rowFechaInicio, fechaInicioLista);
        
        XSSFRow rowFechaFin = spreadsheet.createRow(4);
        crearFilas(workbook, rowFechaFin, fechaFinLista);
        
        XSSFRow rowCentro = spreadsheet.createRow(5);
        crearFilas(workbook, rowCentro, centrosLista);
        
        XSSFRow rowCategoria = spreadsheet.createRow(6);
        crearFilas(workbook, rowCategoria, categoriasLista);
        
        XSSFRow rowTipoContrato = spreadsheet.createRow(7);
        crearFilas(workbook, rowTipoContrato, tipoContratoLista);
        
        XSSFRow rowHorario = spreadsheet.createRow(8);
        crearFilas(workbook, rowHorario, horariosLista);
        
        
        for (int i = 0; i < nombresLista.size(); i++){
        	spreadsheet.autoSizeColumn(i);
        }
        fsIP.close();
        FileOutputStream output = new FileOutputStream(new File("src/LlamamientosDoc/MODIFICACIONES.xlsx"));
        workbook.write(output);
        output.close();
	}
	
	public void crearExcelBajas() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Bajas");
        FileOutputStream fileOut = new FileOutputStream("src/LlamamientosDoc/BAJAS.xlsx");
        workbook.write(fileOut);
        fileOut.close();
		
	}
	
	private void añadirBajas(ObservableList<Movimiento> movimientosBajas)throws IOException, SQLException {
		
		FileInputStream fsIP = new FileInputStream(new File("src/LlamamientosDoc/BAJAS.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        
        XSSFSheet spreadsheet = workbook.getSheet("Bajas");
        
        ObservableList<String> tipoMovmientosLista = FXCollections.observableArrayList();
        tipoMovmientosLista.add("Movimiento");
        ObservableList<String> nombresLista = FXCollections.observableArrayList();
        nombresLista.add("Nombre");
        ObservableList<String> dniLista = FXCollections.observableArrayList();
        dniLista.add("Dni");
        ObservableList<String> fechaFinLista = FXCollections.observableArrayList();
        fechaFinLista.add("Fecha Fin");
        ObservableList<String> importeLista = FXCollections.observableArrayList();
        importeLista.add("Importe");
        ObservableList<String> vacacionesLista = FXCollections.observableArrayList();
        vacacionesLista.add("Vacaciones");
        ObservableList<String> observacionesLista = FXCollections.observableArrayList();
        observacionesLista.add("Observaciones");
        
        
        
        for (int i = 0; i < movimientosBajas.size() ; i++) {
            Movimiento movActual = movimientosBajas.get(i);
        	tipoMovmientosLista.add(movActual.getNombreTipoMovimiento());
        	
            Trabajador trabaj= bbdd.obtenerTrabajador(movActual.getDni());
            nombresLista.add(trabaj.getApellido1()+" " + trabaj.getApellido2()+", "+ trabaj.getNombre());
            
            dniLista.add(trabaj.getDni());
            
            fechaFinLista.add(movActual.getFechaFin().toString());
            
            importeLista.add(Integer.toString(movActual.getImporteBaja()));
            
		}
        
        for (int i = 0; i < movimientosBajas.size() ; i++) {
            //TODO
            vacacionesLista.add("DISFRUTADAS");
            
            observacionesLista.add("");
            
		}

        XSSFRow rowTipoMovimiento = spreadsheet.createRow(0);
        crearFilas(workbook, rowTipoMovimiento, tipoMovmientosLista);
        
        
        XSSFRow rowNombre = spreadsheet.createRow(1);
        crearFilas(workbook, rowNombre, nombresLista);
        
        XSSFRow rowDni = spreadsheet.createRow(2);
        crearFilas(workbook, rowDni, dniLista);
        
        XSSFRow rowFechaFin = spreadsheet.createRow(3);
        crearFilas(workbook, rowFechaFin, fechaFinLista);
        
        XSSFRow rowImporte = spreadsheet.createRow(4);
        crearFilas(workbook, rowImporte, importeLista);
        
        XSSFRow rowVacaciones = spreadsheet.createRow(5);
        crearFilas(workbook, rowVacaciones, vacacionesLista);
        
        XSSFRow rowObservaciones = spreadsheet.createRow(6);
        crearFilas(workbook, rowObservaciones, observacionesLista);
        
        
        
        for (int i = 0; i < nombresLista.size(); i++){
        	spreadsheet.autoSizeColumn(i);
        }
        fsIP.close();
        FileOutputStream output = new FileOutputStream(new File("src/LlamamientosDoc/BAJAS.xlsx"));
        workbook.write(output);
        output.close();
	}
	
	public boolean checkExcelExists(String path) {
		File f = new File(path);
		if(f.exists()) { 
		    System.out.println("YA EXISTE " + path);// do something
		    return true;
		}
		return false;
	}
	
	public void crearExcels(ObservableList<Movimiento> movimientos) throws IOException, SQLException {

		ObservableList<Movimiento> movimientosAltas = FXCollections.observableArrayList();
		ObservableList<Movimiento> movimientosBajas = FXCollections.observableArrayList();
		ObservableList<Movimiento> movimientosModificaciones = FXCollections.observableArrayList();
		ObservableList<Movimiento> movimientosLlamamientos = FXCollections.observableArrayList();

        //TODO
        crearExcelBajas();
        crearExcelLlamamientos();
        crearExcelModiificaciones();
        crearExcelNuevasAltas();

		for(Movimiento mov:movimientos) {
			TipoMovimiento actualTipo = bbdd.obtenerTipoMovimientoPorNombre(mov.getNombreTipoMovimiento());
			if(actualTipo.getNombre().equals(("Alta Nueva").toUpperCase())) {
				movimientosAltas.add(mov);
			}
			
			if(actualTipo.getNombre().equals(("Baja Fin de Actividad").toUpperCase())
                    ||actualTipo.getNombre().equals(("Baja Voluntaria").toUpperCase())
					|| actualTipo.getNombre().equals(("Despido Procedente").toUpperCase())
                    || actualTipo.getNombre().equals(("Despido Improcedente").toUpperCase())
					|| actualTipo.getNombre().equals(("Periodo No Superado").toUpperCase())) {
				movimientosBajas.add(mov);
			}
			
			if(actualTipo.getNombre().equals(("Llamamiento").toUpperCase())) {
				movimientosLlamamientos.add(mov);
			}
			
			if(actualTipo.getNombre().equals(("Cambio Categoría").toUpperCase())
                    || actualTipo.getNombre().equals(("Cambio Tipo Contrato").toUpperCase())
					|| actualTipo.getNombre().equals(("Modificacion").toUpperCase())) {
				movimientosModificaciones.add(mov);
			}
		}
		
		
		if(movimientosAltas.size()!=0) {
			if (!checkExcelExists("src/LlamamientosDoc/ALTAS.xlsx")) {
	        	crearExcelNuevasAltas();	
	        }
			añadirAltas(movimientosAltas);
		}

		if(movimientosLlamamientos.size()!=0) {
			if (!checkExcelExists("src/LlamamientosDoc/LLAMAMIENTOS.xlsx")) {
				crearExcelLlamamientos();	
	        }
			añadirLlamamientos(movimientosLlamamientos);
		}
		
		if(movimientosBajas.size()!=0) {
			if (!checkExcelExists("src/LlamamientosDoc/BAJAS.xlsx")) {
				crearExcelBajas();	
	        }
			añadirBajas(movimientosBajas);
		}
		
		if(movimientosModificaciones.size()!=0) {
			if (!checkExcelExists("src/LlamamientosDoc/MODIFICACIONES.xlsx")) {
				crearExcelModiificaciones();	
	        }
			añadirModificaciones(movimientosModificaciones);

		}
		
		
		
	}
	
	
}
