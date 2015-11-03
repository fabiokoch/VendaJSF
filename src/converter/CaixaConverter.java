package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import beans.Caixa;
import util.GerarEntityManager;

@FacesConverter(value = "caixaConverter")
public class CaixaConverter implements Converter{
    @Override 
    public Caixa getAsObject(FacesContext fc, UIComponent uic, String value) {
    	if(value != null && value.trim().length() > 0) {
    		try {
   	           EntityManager em = GerarEntityManager.getInstance().getEntityManager();
   	           Caixa ret = em.find(Caixa.class, Integer.parseInt(value));
   	           em.close();
   	           return ret;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Convers�o da Caixa", "Caixa inv�lida."));
            }    		
    	}else
    		return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
    	 if(object != null) {
             return String.valueOf(((Caixa) object).getId_caixa());
         }
         else {
             return null;
         }
    }
}
