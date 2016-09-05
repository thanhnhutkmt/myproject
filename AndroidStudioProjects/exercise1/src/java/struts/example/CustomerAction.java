package java.struts.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CustomerAction handles all form submission requests for CustomerForm.
 *
 * @author Srikanth Shenoy
 * @version $Revision:   $ $Date:   $
 */
public class CustomerAction extends Action 
{

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	ActionForward nextPage = null;
        if (isCancelled(request)) 
        {
            System.out.println("Cancel Operation Performed");
            return mapping.findForward("mainpage");
        }

        CustomerForm custForm = (CustomerForm) form;
        
        if ("Save".equals(custForm.getStep()))
        {
			String firstName = custForm.getFirstName();
			String lastName = custForm.getLastName();

			System.out.println("Customer First name is " + firstName);
			System.out.println("Customer Last name is " + lastName);

			nextPage = mapping.findForward("success");
        }
        return nextPage;
    }

}