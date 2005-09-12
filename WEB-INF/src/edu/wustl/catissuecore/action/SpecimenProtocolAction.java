/**
 * <p>Title: CollectionProtocolAction Class>
 * <p>Description:	This class initializes the fields in the User Add/Edit webpage.</p>
 * Copyright:    Copyright (c) year
 * Company: Washington University, School of Medicine, St. Louis.
 * @author Gautam Shetty
 * @version 1.00
 * Created on Mar 22, 2005
 */

package edu.wustl.catissuecore.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.catissuecore.bizlogic.AbstractBizLogic;
import edu.wustl.catissuecore.bizlogic.BizLogicFactory;
import edu.wustl.catissuecore.bizlogic.UserBizLogic;
import edu.wustl.catissuecore.util.global.Constants;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.cde.CDE;
import edu.wustl.common.cde.CDEImpl;
import edu.wustl.common.cde.CDEManager;
import edu.wustl.common.cde.PermissibleValue;
import edu.wustl.common.util.logger.Logger;

/**
 * This class initializes the fields in the User Add/Edit webpage.
 * @author gautam_shetty
 */
public class SpecimenProtocolAction  extends SecureAction
{

    /**
     * Overrides the execute method of Action class.
     * Sets the various fields in User Add/Edit webpage.
     * */
    public ActionForward executeSecureAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        //Gets the value of the operation parameter.
        String operation = request.getParameter(Constants.OPERATION);

        //Sets the operation attribute to be used in the Add/Edit User Page. 
        request.setAttribute(Constants.OPERATION, operation);
        
        //Sets the activityStatusList attribute to be used in the Site Add/Edit Page.
        request.setAttribute(Constants.ACTIVITYSTATUSLIST, Constants.ACTIVITY_STATUS_VALUES);
  
        try
		{
        	UserBizLogic userBizLogic = (UserBizLogic)BizLogicFactory.getBizLogic(Constants.USER_FORM_ID);
        	Collection coll =  userBizLogic.getUsers(Constants.ACTIVITY_STATUS_ACTIVE);
        	request.setAttribute(Constants.USERLIST, coll);
        	Logger.out.debug("1");
        	// get the Specimen class and type from the cde
	    	List specimenTypeList = CDEManager.getCDEManager().getList(Constants.CDE_NAME_SPECIMEN_TYPE);
	    	request.setAttribute(Constants.SPECIMEN_TYPE_LIST, specimenTypeList);

        	CDE specimenClassCDE = CDEManager.getCDEManager().getCDE(Constants.CDE_NAME_SPECIMEN_CLASS);
	    	Set setPV = specimenClassCDE.getPermissibleValues();
	    	Logger.out.debug("2");
	    	Iterator itr = setPV.iterator();
	    
//	    	String classValues[][] = new String[setPV.size()][];
	    	List specimenClassList =  new ArrayList();
	    	Map subTypeMap = new HashMap();
	    	Logger.out.debug("\n\n\n\n**********MAP DATA************\n");
	    	specimenClassList.add(new NameValueBean(Constants.SELECT_OPTION,"-1"));
	    	while(itr.hasNext())
	    	{
	    		List innerList =  new ArrayList();
	    		Object obj = itr.next();
	    		PermissibleValue pv = (PermissibleValue)obj;
	    		String tmpStr = pv.getValue();
	    		Logger.out.debug(tmpStr);
	    		specimenClassList.add(new NameValueBean( tmpStr,tmpStr));
	    		
				Set list1 = pv.getSubPermissibleValues();
				Logger.out.debug("list1 "+list1);
	        	Iterator itr1 = list1.iterator();
	        	innerList.add(new NameValueBean(Constants.SELECT_OPTION,"-1"));
	        	while(itr1.hasNext())
	        	{
	        		Object obj1 = itr1.next();
	        		PermissibleValue pv1 = (PermissibleValue)obj1;
	        		// set specimen type
	        		String tmpInnerStr = pv1.getValue(); 
	        		Logger.out.debug("\t\t"+tmpInnerStr);
	        		innerList.add(new NameValueBean( tmpInnerStr,tmpInnerStr));  
	        	}
	        	subTypeMap.put(pv.getValue(),innerList);
	    	} // class and values set
	    	Logger.out.debug("\n\n\n\n**********MAP DATA************\n");
	    	
	    	// sets the Class list
	    	request.setAttribute(Constants.SPECIMEN_CLASS_LIST, specimenClassList);

	    	// set the map to subtype
	    	request.setAttribute(Constants.SPECIMEN_TYPE_MAP, subTypeMap);
	    	
	    	List tissueSiteList = CDEManager.getCDEManager().getList(Constants.CDE_NAME_TISSUE_SITE);
	    	request.setAttribute(Constants.TISSUE_SITE_LIST, tissueSiteList);
	    	
	    	List pathologyStatusList = CDEManager.getCDEManager().getList(Constants.CDE_NAME_PATHOLOGICAL_STATUS);
	    	request.setAttribute(Constants.PATHOLOGICAL_STATUS_LIST, pathologyStatusList);
		}
        catch(Exception e)
		{
        	Logger.out.error(e.getMessage(),e);
		}
        
        return mapping.findForward((String)request.getParameter(Constants.PAGEOF));
    }
}