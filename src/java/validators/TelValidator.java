/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
 
@FacesValidator("telValidator")
public class TelValidator implements Validator {
 
    private final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
     
    private final static Pattern EMAIL_COMPILED_PATTERN = Pattern.compile(EMAIL_PATTERN);
     
    /**
     * Validate method.
     */
    @Override
    public void validate(FacesContext fc, UIComponent c, Object o) throws ValidatorException {
        
         
        String cadena=(String)o;
        if (cadena.length()>20) {
            FacesMessage msg = new FacesMessage("Maximo de 20 caracteres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if(cadena.length()>0&&(!cadena.matches("[0-9]+"))&&!(cadena.matches("^[+][0-9]+"))){
            FacesMessage msg = new FacesMessage("Debe contener numeros");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
