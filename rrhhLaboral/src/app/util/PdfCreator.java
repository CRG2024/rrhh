package app.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import app.model.Trabajador;

public class PdfCreator {

	private Font boldunderlineFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD|Font.UNDERLINE);
    private Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font timesFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    
    private DataBase bbdd;
    
	public PdfCreator() {
		bbdd = new DataBase();
	}
	
	public void crearAnexoTrabajador(Trabajador trabajador, LocalDate inicio, LocalDate fin) throws DocumentException {
		
        try {
    	Document documento = new Document();
    	documento.setMargins(50f, 50f, 20f, 30f);
    	
        FileOutputStream ficheroPdf = null;
        //String ruta ="C:\\Usuarios\\romer\\Escritorio\\Llamamientos/";
        String ruta ="src/LlamamientosDoc/";
        
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        String textInicio = inicio.format(formatters);
        
        String nombreArchivo = textInicio+" ANEXO "+ trabajador.getApellido1()+" "+trabajador.getApellido2(
        		)+" "+trabajador.getNombre()+".pdf";
        if(ruta.equals("")){
            ficheroPdf = new FileOutputStream(nombreArchivo);
        }else{
            ficheroPdf = new FileOutputStream(ruta + "/"+nombreArchivo);
        }
        
        com.itextpdf.text.pdf.PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20.0F);
        documento.open();
        
        Paragraph footer = new Paragraph();
        footer.add(new Phrase("COMUNICACI�N LLAMAMIENTO", boldunderlineFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingAfter(20f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("VILLA SOFIA S.L.", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("C/ LA CORTE N� 27 (12560 � BENICASSIM)", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("CIF: B12981643", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("C.C.C. 12/111465062", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingAfter(20f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Por la presente se informa que los llamamientos a la actividad de los contratos fijos discontinuos se realizar�n por los criterios de categor�a, puesto de trabajo y antig�edad en la misma, por ese orden de preferencia.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(20f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("As� mismo, se indica que se realizar�n los llamamientos de forma telef�nica siempre desde los mismos n�meros de tel�fono de contacto que han venido siendo hasta ahora, por lo que, si recibe una llamada desde dicho n�mero, mensaje de texto o WhatsApp, y no puede atenderlo en ese momento, p�ngase en contacto con la empresa para poder indicarle la fecha de incorporaci�n, de no hacerlo en un plazo de m�ximo 24h se entender� que rechaza el llamamiento y se proceder� a llamar al siguiente en la lista y anotando en su historial el rechazo del mismo.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(20f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Una vez el trabajador acumule 3 llamamientos negativos consecutivos en los cuales no se incorpore (y no justifique dicha incorporaci�n) a la actividad, la empresa entender� que no desea continuar con la relaci�n laboral por lo que proceder� a rescindir su contrato por incumplimiento por su parte del mismo, sin dar derecho a indemnizaci�n de ning�n tipo y quedando rescindido su contrato y vinculaci�n laboral con la empresa.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(20f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("En el caso que por circunstancias no pudiera incorporarse deber� justificar a la empresa el no poder atender el llamamiento a la actividad, para que, de tal modo, dicho llamamiento no se tenga en cuenta para la rescisi�n del contrato, y demostrar la voluntad por su parte de querer continuar con la relaci�n laboral.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(20f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("La firma del presente contrato indica la aceptaci�n integra de dicha cl�usula y de la cual ha sido informado.", timesFont));
        footer.setAlignment(Element.ALIGN_JUSTIFIED);
        footer.setSpacingAfter(20f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("Atentamente,", timesFont));
        footer.setAlignment(Element.ALIGN_LEFT);
        footer.setSpacingAfter(20f);
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
        
        Image img = Image.getInstance("src/images/firmaSello.png");
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
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}
	
	public void crearConsentimientoTrabajador(Trabajador trabajador, LocalDate inicio) throws DocumentException {
		
        try {
    	Document documento = new Document();
    	documento.setMargins(50f, 50f, 50f, 30f);
    	
        FileOutputStream ficheroPdf = null;
        //String ruta ="C:\\Usuarios\\romer\\Escritorio\\Llamamientos/";
        String ruta ="src/LlamamientosDoc/";
        
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        String textInicio = inicio.format(formatters);
        
        String nombreArchivo = textInicio+" CONSENTIMIENTO ALTA SS "+ trabajador.getApellido1()+" "+trabajador.getApellido2(
        		)+" "+trabajador.getNombre()+".pdf";
        if(ruta.equals("")){
            ficheroPdf = new FileOutputStream(nombreArchivo);
        }else{
            ficheroPdf = new FileOutputStream(ruta + "/"+nombreArchivo);
        }
        
        com.itextpdf.text.pdf.PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20.0F);
        documento.open();
        
        
        
        
        Paragraph footer = new Paragraph();
        footer.setSpacingBefore(50f);
        footer.add(new Phrase("COMUNICACI�N DE CONSENTIMIENTO DE ALTA EN S.S",boldunderlineFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingAfter(50f);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("VILLA SOFIA S.L.", boldFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        documento.add(footer);
        
        footer = new Paragraph();
        footer.add(new Phrase("C/ LA CORTE N� 27 (12560 � BENICASSIM)", boldFont));
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
        footer.add(new Phrase("Don/Do�a "+ trabajador.getNombre()+ " " +trabajador.getApellido1()+" "+trabajador.getApellido2()+
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
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}

	public void crearLlamamientoRealizadoTrabajador(Trabajador trabajador, LocalDate inicio, LocalDate fin) throws DocumentException {
	
													try {
		Document documento = new Document();
		documento.setMargins(50f, 50f, 50f, 30f);
		
	    FileOutputStream ficheroPdf = null;
	    //String ruta ="C:\\Usuarios\\romer\\Escritorio\\Llamamientos/";
	    String ruta ="src/LlamamientosDoc/";
	    
	//    BaseFont baseFont3 = BaseFont.createFont("Xenotron.ttf", BaseFont.HELVETICA, BaseFont.NOT_EMBEDDED);
	//    Font font2 = new Font(baseFont3, 12);
	    
	    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
	    String textInicio = inicio.format(formatters);
	    
	    String nombreArchivo = textInicio+" LLAMAMIENTO FD "+ trabajador.getApellido1()+" "+trabajador.getApellido2(
	    		)+" "+trabajador.getNombre()+".pdf";
	    if(ruta.equals("")){
	        ficheroPdf = new FileOutputStream(nombreArchivo);
	    }else{
	        ficheroPdf = new FileOutputStream(ruta + "/"+nombreArchivo);
	    }
	    
	    com.itextpdf.text.pdf.PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20.0F);
	    documento.open();
	    
	    Paragraph footer = new Paragraph();
	    footer.setSpacingBefore(50f);
	    footer.add(new Phrase("COMUNICACI�N LLAMAMIENTO FD", boldunderlineFont));
	    footer.setAlignment(Element.ALIGN_CENTER);
	    footer.setSpacingAfter(20f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("VILLA SOFIA S.L.", boldFont));
	    footer.setAlignment(Element.ALIGN_CENTER);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("C/ LA CORTE N� 27 (12560 � BENICASSIM)", boldFont));
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
	    footer.add(new Phrase("Don/Do�a "+ trabajador.getNombre()+ " " +trabajador.getApellido1()+" "+trabajador.getApellido2(), timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(10f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("con DNI/NIE "+ trabajador.getDni(), timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(30f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("Le comunicamos que, teniendo Vd. la condici�n de trabajador/a fijo discontinuo de esta empresa, deber� empezar a prestar sus servicios con fecha "
	    		+ inicio.getDayOfMonth()+" de "+ inicio.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"))+" de "+inicio.getYear()+", constando como fecha de finalizaci�n el "
	    				+ fin.getDayOfMonth()+" de "+ fin.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"))+" de "+fin.getYear()+".", timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(20f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("Fecha, esta �ltima, en la que est� prevista concluyan las actividades para las que ha sido contratado.", timesFont));
	    footer.setAlignment(Element.ALIGN_JUSTIFIED);
	    footer.setSpacingAfter(20f);
	    documento.add(footer);
	    
	    footer = new Paragraph();
	    footer.add(new Phrase("OBSERVACIONES: Las fechas comunicadas estar�n sujetas a variaciones en la producci�n, pudiendo ser ampliadas o reducidas si las circunstancias lo requieren.", timesFont));
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
	    celda.addElement(new Phrase("Recib� el duplicado", timesFont));
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
	    
	    
	    Image img = Image.getInstance("src/images/firmaSello.png");
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
	    } catch (FileNotFoundException ex) {
	        System.out.println(ex.getMessage());
	    } catch (IOException ex) {
	        System.out.println(ex.getMessage());
	    }														
	}	
	
}
