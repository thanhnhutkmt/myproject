package java.struts.example;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * CustomerForm is the object representation of the html form for entering 
 * customer's first and last name in Struts.
 *
 * @author Srikanth Shenoy
 * @version $Revision:   $ $Date:   $
 */
public class CustomerForm extends ActionForm 
{

	/** Customer First Name */
    private String firstName;
    
	/** Customer Last Name */
    private String lastName;
    
	/** 
	 * This is the string that will hold the name of the submit button that 
	 * submitted the CustomerForm. This will hold the name of the submit 
	 * button only if the &lt;html:submit property="step"&gt; is used  
	 */
    private String step;

    public CustomerForm() 
    {
        firstName = "";
        lastName = "";
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public void setFirstName(String s) 
    {
        this.firstName = s;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public void setLastName(String s) 
    {
        this.lastName = s;
    }

	public String getStep() 
	{
		return step;
	}

	public void setStep(String string) 
	{
		step = string;
	}

	/**
	 * This method is called by the RequestProcessor immediately after populating the 
	 * CustomerForm only if the validate=true flag is set in the struts-config.xml
	 * Do validation here and return ActionErrors if any, to the RequestProcessor
	 */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = new ActionErrors();

        // Firstname cannot be empty
        if (firstName == null || firstName.trim().equals("")) 
        {
            errors.add("firstName", new ActionError("error.cust.firstname.empty"));
        }

		// Lastname cannot be empty
        if (lastName == null || lastName.trim().equals("")) 
        {
            errors.add("lastName", new ActionError("error.cust.lastname.empty"));
        }

        return errors;
    }

}