package app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

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
        nssLista.add("NºSS");
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
        	tipoMovmientosLista.add(bbdd.obtenerTipoMovimiento(movimientosAltas.get(i).getIdTipoMovimiento()).getNombre());
        	
            Trabajador trabaj= bbdd.obtenerTrabajador(movimientosAltas.get(i).getDni());
            nombresLista.add(trabaj.getApellido1()+" " + trabaj.getApellido2()+", "+ trabaj.getNombre());
            
            dniLista.add(trabaj.getDni());
            
            nssLista.add(trabaj.getnss());
            
            fechaNacimientoLista.add(trabaj.getFechaNacimiento().toString());
            
            domicilioLista.add(trabaj.getDomicilio());
            
            ciudadLista.add(trabaj.getCiudad());
            
            nacionalidadLista.add(trabaj.getNacionalidad());
            
            fechaInicioLista.add(movimientosAltas.get(i).getFechaInicio().toString());
            
            fechaFinLista.add(movimientosAltas.get(i).getFechaFin().toString());
            
            centrosLista.add(bbdd.obtenerCentro(movimientosAltas.get(i).getIdCentro()).getNombre());
            
            categoriasLista.add(bbdd.obtenerCategoria(movimientosAltas.get(i).getIdCategoria()).getNombre());
            
            horariosLista.add(bbdd.obtenerHorario(movimientosAltas.get(i).getIdHorario()).getHorario());
            
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
		        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        my_style.setFont(my_font);
	        }
	        my_style.setAlignment(HorizontalAlignment.CENTER);
	        my_style.setVerticalAlignment(VerticalAlignment.CENTER);
	        cell.setCellStyle(my_style);
		}
	}
	
	
	public void crearExcelLlamamientos() throws IOException {
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
        	tipoMovmientosLista.add(bbdd.obtenerTipoMovimiento(movimientosLlamamientos.get(i).getIdTipoMovimiento()).getNombre());
        	
            Trabajador trabaj= bbdd.obtenerTrabajador(movimientosLlamamientos.get(i).getDni());
            nombresLista.add(trabaj.getApellido1()+" " + trabaj.getApellido2()+", "+ trabaj.getNombre());
            
            dniLista.add(trabaj.getDni());
            
            fechaInicioLista.add(movimientosLlamamientos.get(i).getFechaInicio().toString());
            
            fechaFinLista.add(movimientosLlamamientos.get(i).getFechaFin().toString());
            
            centrosLista.add(bbdd.obtenerCentro(movimientosLlamamientos.get(i).getIdCentro()).getNombre());
            
            categoriasLista.add(bbdd.obtenerCategoria(movimientosLlamamientos.get(i).getIdCategoria()).getNombre());
            
            horariosLista.add(bbdd.obtenerHorario(movimientosLlamamientos.get(i).getIdHorario()).getHorario());
            
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
        	tipoMovmientosLista.add(bbdd.obtenerTipoMovimiento(movimientosModificaciones.get(i).getIdTipoMovimiento()).getNombre());
        	
            Trabajador trabaj= bbdd.obtenerTrabajador(movimientosModificaciones.get(i).getDni());
            nombresLista.add(trabaj.getApellido1()+" " + trabaj.getApellido2()+", "+ trabaj.getNombre());
            
            dniLista.add(trabaj.getDni());
            
            fechaInicioLista.add(movimientosModificaciones.get(i).getFechaInicio().toString());
            
            fechaFinLista.add(movimientosModificaciones.get(i).getFechaFin().toString());
            
            centrosLista.add(bbdd.obtenerCentro(movimientosModificaciones.get(i).getIdCentro()).getNombre());
            
            categoriasLista.add(bbdd.obtenerCategoria(movimientosModificaciones.get(i).getIdCategoria()).getNombre());
            
            tipoContratoLista.add(bbdd.obtenerCategoria(movimientosModificaciones.get(i).getIdTipoContrato()).getNombre());
            
            horariosLista.add(bbdd.obtenerHorario(movimientosModificaciones.get(i).getIdHorario()).getHorario());
            
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
        	tipoMovmientosLista.add(bbdd.obtenerTipoMovimiento(movimientosBajas.get(i).getIdTipoMovimiento()).getNombre());
        	
            Trabajador trabaj= bbdd.obtenerTrabajador(movimientosBajas.get(i).getDni());
            nombresLista.add(trabaj.getApellido1()+" " + trabaj.getApellido2()+", "+ trabaj.getNombre());
            
            dniLista.add(trabaj.getDni());
            
            fechaFinLista.add(movimientosBajas.get(i).getFechaFin().toString());
            
            importeLista.add(Integer.toString(movimientosBajas.get(i).getImporteBaja()));
            
		}
        
        for (int i = 0; i < movimientosBajas.size() ; i++) {
            
            vacacionesLista.add("DISFRUTADAS");
            
            observacionesLista.add("DISFRUTADAS");
            
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
		
		for(Movimiento mov:movimientos) {
			System.out.println("llega");
			TipoMovimiento actualTipo = bbdd.obtenerTipoMovimiento(mov.getIdTipoMovimiento());
			System.out.println(actualTipo.getNombre());
			if(actualTipo.getNombre().equals("Alta Nueva")) {
				System.out.println("entra");
				movimientosAltas.add(mov);
			}
			
			if(actualTipo.getNombre()=="Baja Fin de Actividad"||actualTipo.getNombre()=="Baja Voluntaria" 
					|| actualTipo.getNombre().equals("Despido Procedente") || actualTipo.getNombre().equals("Despido Improcedente")
					|| actualTipo.getNombre().equals("Periodo No Superado")) {
				movimientosBajas.add(mov);
			}
			
			if(actualTipo.getNombre().equals("Llamamiento")) {
				movimientosLlamamientos.add(mov);
			}
			
			if(actualTipo.getNombre().equals("Cambio Categoría") || actualTipo.getNombre().equals("Cambio Tipo Contrato") 
					|| actualTipo.getNombre().equals("Modificacion")) {
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
