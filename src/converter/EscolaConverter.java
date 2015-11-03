package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import beans.Escola;
import util.GerarEntityManager;

@FacesConverter(value = "escolaConverter")
public class EscolaConverter implements Converter{
    @Override 
    public Escola getAsObject(FacesContext fc, UIComponent uic, String value) {
    	if(value != null && value.trim().length() > 0) {
    		try {
   	           EntityManager em = GerarEntityManager.getInstance().getEntityManager();
   	           Escola ret = em.find(Escola.class, Integer.parseInt(value));
   	           em.close();
   	           return ret;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Convers�o da Escola", "Escola inv�lida."));
            }    		
    	}else
    		return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
    	 if(object != null) {
             return String.valueOf(((Escola) object).getId_escola());
         }
         else {
             return null;
         }
    }
}
