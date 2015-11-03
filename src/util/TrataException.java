package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class TrataException {

	public static void pegaMensagem(Exception e, FacesContext currentInstance) {
		// TODO Auto-generated method stub
		Throwable t = e.getCause();
		while(t.getCause() != null){
			t = t.getCause();
			
		}
		
		// personalizar a mensagem
		String msgErro = t.getLocalizedMessage();
		if (msgErro.contains("chave na mensagem ")){
			msgErro = "Mensagem personalizada!";
		}
		
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_ERROR,
        		"Erro ao gravar: "+ msgErro, 
        		"");
		currentInstance.addMessage("Erro", msg);

	}

}
