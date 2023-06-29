package app.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import app.model.Horario;
import app.model.Movimiento;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import app.model.Trabajador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PdfCreator {

	private Font boldunderlineFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD|Font.UNDERLINE);

    private Font boldunderlineFontLargos = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD|Font.UNDERLINE);

    private Font boldFontLargos = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

    private Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font timesFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    
    private DataBase bbdd;
    
	public PdfCreator() {
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
	public String crearAnexoTrabajador(Trabajador trabajador, LocalDate inicio, LocalDate fin) throws DocumentException {
		
        try {
    	Document documento = new Document();
    	documento.setMargins(50f, 50f, 20f, 30f);
    	
        FileOutputStream ficheroPdf = null;
        String ruta ="src/LlamamientosDoc/";
        
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        String textInicio = inicio.format(formatters);
        
        String nombreArchivo = textInicio+" ANEXO "+ trabajador.getDniNombreCompleto()+".pdf";
        if(ruta.equals("")){
            ficheroPdf = new FileOutputStream(nombreArchivo);
        }else{
            ficheroPdf = new FileOutputStream(ruta + "/"+nombreArchivo);
        }
        
        com.itextpdf.text.pdf.PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20.0F);
        documento.open();
        
        Paragraph footer = new Paragraph();
        footer.add(new Phrase("COMUNICACIÓN LLAMAMIENTO", boldunderlineFontLargos));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingAfter(20f);
        documento.add(footer);


        footer = new Paragraph();
        footer.add(new Phrase("VILLA SOFIA S.L.", boldFontLargos));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("C/ LA CORTE Nº 27 (12560 - BENICASSIM)", boldFontLargos));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("CIF: B12981643", boldFontLargos));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("C.C.C. 12/111465062", boldFontLargos));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingAfter(20f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Por la presente se informa que los llamamientos" +
                " a la actividad de los contratos fijos discontinuos se realizarán por" +
                " los criterios de categoría, puesto de trabajo y antigüedad en la misma," +
                " por ese orden de preferencia.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(10f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Así mismo, se indica que se realizarán los llamamientos de forma" +
                " telefónica siempre desde los mismos números de teléfono de contacto que han venido" +
                " siendo hasta ahora, por lo que, si recibe una llamada desde dicho número, mensaje" +
                " de texto o WhatsApp, y no puede atenderlo en ese momento, póngase en contacto con" +
                " la empresa para poder indicarle la fecha de incorporación, de no hacerlo en un plazo" +
                " de máximo 24h se entenderá que rechaza el llamamiento y se procederá a llamar al" +
                " siguiente en la lista y anotando en su historial el rechazo del mismo.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(10f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Una vez el trabajador acumule 3 llamamientos negativos consecutivos" +
                " en los cuales no se incorpore (y no justifique dicha incorporación) a la actividad," +
                " la empresa entenderá que no desea continuar con la relación laboral por lo que procederá" +
                " a rescindir su contrato por incumplimiento por su parte del mismo, sin dar derecho" +
                " a indemnización de ningún tipo y quedando rescindido su contrato y vinculación" +
                " laboral con la empresa.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(10f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("En el caso que por circunstancias no pudiera incorporarse deberá justificar a " +
                "la empresa el no poder atender el llamamiento a la actividad, para que, de tal modo, " +
                "dicho llamamiento no se tenga en cuenta para la rescisión del contrato, y demostrar la voluntad" +
                " por su parte de querer continuar con la relación laboral.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(10f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("La firma del presente contrato indica la aceptación integra de dicha cláusula y" +
                " de la cual ha sido informado.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(10f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Atentamente,", timesFont));
        footer.setAlignment(Element.ALIGN_LEFT);
        footer.setSpacingAfter(10f);
        documento.add(footer);
   
        PdfPTable table = new PdfPTable(3);
        
        PdfPCell celda = new PdfPCell();
        celda.addElement(new Phrase("Firma de la empresa", timesFont));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);
        
        celda = new PdfPCell();
        celda.addElement(new Phrase(""));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);
        
        PdfPCell celda2 = new PdfPCell();
        celda2.addElement(new Phrase(trabajador.getNombre()+" "+ trabajador.getApellido1()+" "+trabajador.getApellido2(), timesFont));
        celda2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda2.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda2);
       
        celda = new PdfPCell();
        celda.addElement(new Phrase(LocalDate.now().format(formatters), timesFont));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);
        
        celda = new PdfPCell();
        celda.addElement(new Phrase(""));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);
        
        celda = new PdfPCell();
        celda.addElement(new Phrase(trabajador.getDni(), timesFont));
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);

        celda = new PdfPCell();
        celda.addElement(new Phrase(""));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);

        celda = new PdfPCell();
        celda.addElement(new Phrase(""));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);

        celda = new PdfPCell();
        celda.addElement(new Phrase(LocalDate.now().format(formatters), timesFont));
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);
        
        Image img = Image.getInstance("rrhhLaboral/src/images/firmaSello.png");
        img.scaleAbsolute(200, 200);

        celda = new PdfPCell();
        celda.addElement(img);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);

        celda = new PdfPCell();
        celda.addElement(new Phrase(""));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);

        celda = new PdfPCell();
        celda.addElement(new Phrase(""));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setBorder(Rectangle.NO_BORDER);
        table.addCell(celda);
        
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100);
       
        
        documento.add(table);
        
        documento.close();
            return nombreArchivo;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
	}
	
	public String crearConsentimientoTrabajador(Trabajador trabajador, LocalDate inicio) throws DocumentException {
		
        try {
    	Document documento = new Document();
    	documento.setMargins(50f, 50f, 50f, 30f);
    	
        FileOutputStream ficheroPdf = null;
        String ruta ="src/LlamamientosDoc/";
        
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        String textInicio = inicio.format(formatters);
        
        String nombreArchivo = textInicio+" CONSENTIMIENTO ALTA SS "+ trabajador.getDniNombreCompleto()+".pdf";
        if(ruta.equals("")){
            ficheroPdf = new FileOutputStream(nombreArchivo);
        }else{
            ficheroPdf = new FileOutputStream(ruta + "/"+nombreArchivo);
        }
        
        com.itextpdf.text.pdf.PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20.0F);
        documento.open();
        
        
        
        
        Paragraph footer = new Paragraph();
        footer.setSpacingBefore(50f);
        footer.add(new Phrase("COMUNICACIÓN DE CONSENTIMIENTO DE ALTA EN S.S",boldunderlineFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingAfter(50f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("VILLA SOFIA S.L.", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("C/ LA CORTE Nº 27 (12560 - BENICASSIM)", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("CIF: B12981643", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("C.C.C. 12/111465062", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingAfter(40f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("En Benicasim a "+ LocalDate.now().getDayOfMonth()+" de "+ LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"))+" de "+ LocalDate.now().getYear(), timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(30f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Estimados Sres.:", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(30f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Don/Doña "+ trabajador.getNombre()+ " " +trabajador.getApellido1()+" "+trabajador.getApellido2()+
        		"  con DNI/NIE "+ trabajador.getDni()+" les autorizo a que realicen mi alta en la Seguridad Social con fecha "
        				+ inicio.getDayOfMonth()+" de "+ inicio.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"))+" de "+inicio.getYear()+"", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(40f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Atentamente,", timesFont));
        footer.setAlignment(Element.ALIGN_LEFT);
        footer.setSpacingAfter(30f);
        documento.add(footer);
   
        footer = new Paragraph();
        footer.add(new Phrase("Firma del trabajador", timesFont));
        footer.setAlignment(Element.ALIGN_LEFT);
        footer.setSpacingAfter(20f);
        documento.add(footer);
  
        
        documento.close();
        return nombreArchivo;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
	}

	public String crearLlamamientoRealizadoTrabajador(Trabajador trabajador, LocalDate inicio, LocalDate fin) throws DocumentException {
	
													try {
		Document documento = new Document();
		documento.setMargins(50f, 50f, 50f, 30f);
		
	    FileOutputStream ficheroPdf = null;
	    String ruta ="src/LlamamientosDoc/";

	    
	    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
	    String textInicio = inicio.format(formatters);
	    
	    String nombreArchivo = textInicio+" LLAMAMIENTO FD "+ trabajador.getDniNombreCompleto()+".pdf";
	    if(ruta.equals("")){
	        ficheroPdf = new FileOutputStream(nombreArchivo);
	    }else{
	        ficheroPdf = new FileOutputStream(ruta + "/"+nombreArchivo);
	    }
	    
	    com.itextpdf.text.pdf.PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20.0F);
	    documento.open();
	    
	    Paragraph footer = new Paragraph();
	    footer.setSpacingBefore(50f);
	    footer.add(new Phrase("COMUNICACIÓN LLAMAMIENTO FD", boldunderlineFont));
	    footer.setAlignment(Element.ALIGN_CENTER);
	    footer.setSpacingAfter(20f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("VILLA SOFIA S.L.", boldFont));
	    footer.setAlignment(Element.ALIGN_CENTER);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("C/ LA CORTE Nº 27 (12560 - BENICASSIM)", boldFont));
	    footer.setAlignment(Element.ALIGN_CENTER);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("CIF: B12981643", boldFont));
	    footer.setAlignment(Element.ALIGN_CENTER);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("C.C.C. 12/111465062", boldFont));
	    footer.setAlignment(Element.ALIGN_CENTER);
	    footer.setSpacingAfter(40f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("En Benicasim a "+ LocalDate.now().getDayOfMonth()+" de "+ LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"))+" de "+ LocalDate.now().getYear(), timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(30f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("Don/Doña "+ trabajador.getNombre()+ " " +trabajador.getApellido1()+" "+trabajador.getApellido2(), timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(10f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("con DNI/NIE "+ trabajador.getDni(), timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(30f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("Le comunicamos que, teniendo Vd. la condición de trabajador/a fijo discontinuo de esta empresa, deberá empezar a prestar sus servicios con fecha "
	    		+ inicio.getDayOfMonth()+" de "+ inicio.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"))+" de "+inicio.getYear()+", constando como fecha de finalización el "
	    				+ fin.getDayOfMonth()+" de "+ fin.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"))+" de "+fin.getYear()+".", timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(20f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("Fecha, esta última, en la que está prevista concluyan las actividades para las que ha sido contratado.", timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(20f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("OBSERVACIONES: Las fechas comunicadas estarán sujetas a variaciones en la producción, pudiendo ser ampliadas o reducidas si las circunstancias lo requieren.", timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(30f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("Atentamente,", timesFont));
	    footer.setAlignment(Element.ALIGN_LEFT);
	    footer.setSpacingAfter(30f);
	    documento.add(footer);
	
	    PdfPTable table = new PdfPTable(3);
	    
	    PdfPCell celda = new PdfPCell();
	    celda.addElement(new Phrase("Firma de la empresa", timesFont));
	    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    celda = new PdfPCell();
	    celda.addElement(new Phrase(""));
	    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    PdfPCell celda2 = new PdfPCell();
	    celda2.addElement(new Phrase(trabajador.getNombre()+" "+ trabajador.getApellido1()+" "+trabajador.getApellido2(), timesFont));
	    celda2.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    celda2.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda2);
	   
	    celda = new PdfPCell();
	    celda.addElement(new Phrase(LocalDate.now().format(formatters), timesFont));
	    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    celda = new PdfPCell();
	    celda.addElement(new Phrase(""));
	    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    celda = new PdfPCell();
	    celda.addElement(new Phrase("Recibí el duplicado", timesFont));
	    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	   
	    celda = new PdfPCell();
	    celda.addElement(new Phrase(""));
	    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    celda = new PdfPCell();
	    celda.addElement(new Phrase(""));
	    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    celda = new PdfPCell();
	    celda.addElement(new Phrase(LocalDate.now().format(formatters), timesFont));
	    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    
	    Image img = Image.getInstance("rrhhLaboral/src/images/firmaSello.png");
	    img.scaleAbsolute(200, 200);
	    
	    celda = new PdfPCell();
	    celda.addElement(img);
	    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    celda = new PdfPCell();
	    celda.addElement(new Phrase(""));
	    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    celda = new PdfPCell();
	    celda.addElement(new Phrase(""));
	    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	    celda.setBorder(Rectangle.NO_BORDER);
	    table.addCell(celda);
	    
	    table.getDefaultCell().setBorder(0);
	    table.setWidthPercentage(100);
	    
	    
	   
	    
	    documento.add(table);
	    
	    documento.close();
        return nombreArchivo;
	    } catch (FileNotFoundException ex) {
	        System.out.println(ex.getMessage());
	    } catch (IOException ex) {
	        System.out.println(ex.getMessage());
	    }
        return null;
	}

    public void crearPdfDatosTrabajador(Trabajador selectedTrabajador) {
        try {
            Document documento = new Document();
            documento.setMargins(50f, 50f, 20f, 30f);

            FileOutputStream ficheroPdf = null;
            String ruta ="src/LlamamientosDoc/";

            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
            String textInicio = LocalDate.now().format(formatters);

            String nombreArchivo = textInicio+" DATOS "+ selectedTrabajador.getDniNombreCompleto()+".pdf";
            if(ruta.equals("")){
                ficheroPdf = new FileOutputStream(nombreArchivo);
            }else{
                ficheroPdf = new FileOutputStream(ruta + "/"+nombreArchivo);
            }

            com.itextpdf.text.pdf.PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20.0F);
            documento.open();

            Paragraph footer = new Paragraph();
            footer.add(new Phrase(selectedTrabajador.getNombreCompleto().toUpperCase(Locale.ROOT),
                    boldunderlineFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingAfter(20f);
            documento.add(footer);


            footer = new Paragraph();
            footer.add(new Phrase("VILLA SOFIA S.L.", boldFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            documento.add(footer);

            footer = new Paragraph();
            footer.add(new Phrase("C/ LA CORTE Nº 27 (12560 - BENICASSIM)", boldFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            documento.add(footer);

            footer = new Paragraph();
            footer.add(new Phrase("CIF: B12981643", boldFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            documento.add(footer);

            footer = new Paragraph();
            footer.add(new Phrase("C.C.C. 12/111465062", boldFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingAfter(20f);
            documento.add(footer);

            PdfPTable table = new PdfPTable(3);

            PdfPCell celda = new PdfPCell();
            celda.addElement(new Phrase("Nombre", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            PdfPCell celda2 = new PdfPCell();
            celda2.addElement(new Phrase(selectedTrabajador.getNombreCompleto(), timesFont));
            celda2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda2.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda2);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Dni", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getDni(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Número Seguridad Social", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getnss(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Domicilio", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getDomicilio(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Ciudad", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getCiudad(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Población", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getPoblacion(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Código Postal", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(Integer.toString(selectedTrabajador.getCp()), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Teléfono 1", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getTelefono1(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Teléfono 2", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getTelefono2(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Email", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getEmail(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Cuenta Bancaria", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(selectedTrabajador.getCuenta(), timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Carnet de conducir", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            String carnet = "";
            if(selectedTrabajador.getCarnet() == "0"){
                carnet = "No";
            }else{
                carnet = "Si";
            }
            celda = new PdfPCell();
            celda.addElement(new Phrase(carnet, timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Vehículo Propio", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            String vehiculo = "";
            if(selectedTrabajador.getCarnet() == "0"){
                vehiculo = "No";
            }else{
                vehiculo = "Si";
            }

            celda = new PdfPCell();
            celda.addElement(new Phrase(vehiculo, timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Permiso de Trabajo", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            String permiso = "";
            if(selectedTrabajador.getCarnet() == "0"){
                permiso = "No";
            }else{
                permiso = "Si";
            }

            celda = new PdfPCell();
            celda.addElement(new Phrase(permiso, timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase("Discapacidades", timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.addElement(new Phrase(""));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);

            String discapacidad = "";
            if(selectedTrabajador.getCarnet() == "0"){
                discapacidad = "No";
            }else{
                discapacidad = "Si";
            }

            celda = new PdfPCell();
            celda.addElement(new Phrase(discapacidad, timesFont));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setBorder(Rectangle.NO_BORDER);
            table.addCell(celda);
            table.getDefaultCell().setBorder(0);
            table.setWidthPercentage(100);

            documento.add(table);

            documento.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (BadElementException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void crearPdfMovimientos(ObservableList<Movimiento> movimientos) throws SQLException {
        String dni = movimientos.get(0).getDni();
        Trabajador trabajador = bbdd.obtenerTrabajador(dni);
        try {
            Document documento = new Document();
            documento.setMargins(10f, 10f, 20f, 30f);
            documento.setPageSize(PageSize.A4.rotate());

            FileOutputStream ficheroPdf = null;
            String ruta ="src/LlamamientosDoc/";

            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
            String textInicio = LocalDate.now().format(formatters);

            String nombreArchivo = textInicio+" MOVIMIENTOS "+ trabajador.getDniNombreCompleto()+".pdf";
            if(ruta.equals("")){
                ficheroPdf = new FileOutputStream(nombreArchivo);
            }else{
                ficheroPdf = new FileOutputStream(ruta + "/"+nombreArchivo);
            }

            com.itextpdf.text.pdf.PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20.0F);
            documento.open();

            Paragraph footer = new Paragraph();
            footer.add(new Phrase(trabajador.getDni().toUpperCase(Locale.ROOT) +
                    " - " +
                    trabajador.getNombreCompleto().toUpperCase(Locale.ROOT),
                    boldunderlineFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingAfter(20f);
            documento.add(footer);


            footer = new Paragraph();
            footer.add(new Phrase("VILLA SOFIA S.L.", boldFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            documento.add(footer);

            footer = new Paragraph();
            footer.add(new Phrase("C/ LA CORTE Nº 27 (12560 - BENICASSIM)", boldFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            documento.add(footer);

            footer = new Paragraph();
            footer.add(new Phrase("CIF: B12981643", boldFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            documento.add(footer);

            footer = new Paragraph();
            footer.add(new Phrase("C.C.C. 12/111465062", boldFontLargos));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingAfter(20f);
            documento.add(footer);

            PdfPTable table = new PdfPTable(5);
            for (Movimiento mov:movimientos) {
                ObservableList<PdfPCell> celdasMovimiento = crearCeldasMovimientos(mov);
                for (PdfPCell celda: celdasMovimiento) {
                    table.addCell(celda);
                }
            }

            documento.add(table);

            documento.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (BadElementException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<PdfPCell> crearCeldasMovimientos(Movimiento movimiento) throws SQLException {
        ObservableList<PdfPCell> celdas = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        PdfPCell celdaNombre = new PdfPCell();
        celdaNombre.addElement(new Phrase(movimiento.getNombreTipoMovimiento(), timesFont));
        celdaNombre.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaNombre.setBorder(Rectangle.NO_BORDER);
        celdaNombre.setBorderWidthBottom(1);
        celdas.add(celdaNombre);

        PdfPCell celdaFechaInicio = new PdfPCell();
        celdaFechaInicio.addElement(new Phrase(formatter.format(movimiento.getFechaInicio()), timesFont));
        celdaFechaInicio.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaFechaInicio.setBorder(Rectangle.NO_BORDER);
        celdaFechaInicio.setBorderWidthBottom(1);
        celdas.add(celdaFechaInicio);

        PdfPCell celdaFechaFin = new PdfPCell();
        celdaFechaFin.addElement(new Phrase(formatter.format(movimiento.getFechaFin()), timesFont));
        celdaFechaFin.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaFechaFin.setBorder(Rectangle.NO_BORDER);
        celdaFechaFin.setBorderWidthBottom(1);
        celdas.add(celdaFechaFin);

        PdfPCell celdaCentro = new PdfPCell();
        celdaCentro.addElement(new Phrase(movimiento.getNombreCentro(), timesFont));
        celdaCentro.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaCentro.setBorder(Rectangle.NO_BORDER);
        celdaCentro.setBorderWidthBottom(1);
        celdas.add(celdaCentro);

        PdfPCell celdaHorario = new PdfPCell();
        Horario horario = bbdd.obtenerHorarioPorNombre(movimiento.getNombreHorario());
        celdaHorario.addElement(new Phrase(horario.getHorarioAbreviado(), timesFont));
        celdaHorario.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaHorario.setBorder(Rectangle.NO_BORDER);
        celdaHorario.setBorderWidthBottom(1);
        celdas.add(celdaHorario);
        horario.getHorarioAbreviado();

        return celdas;
    }

}
