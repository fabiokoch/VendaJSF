package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;


import beans.Cursos;
import util.GerarEntityManager;

@FacesConverter(value = "cursosConverter")
public class CursosConverter implements Converter{
    @Override 
    public Cursos getAsObject(FacesContext fc, UIComponent uic, String value) {
    	if(value != null && value.trim().length() > 0) {
    		try {
   	           EntityManager em = GerarEntityManager.getInstance().getEntityManager();
   	           Cursos ret = em.find(Cursos.class, Integer.parseInt(value));
   	           em.close();
   	           return ret;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Convers�o da Cidade", "Curso inv�lida."));
            }    		
    	}else
    		return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
    	 if(object != null) {
             return String.valueOf(((Cursos) object).getId_cursos());
         }
         else {
             return null;
         }
    }
}
