<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:html xhtml="true">

<head>
	<title><bean:message key="exercise01.successpage.title"/></title>
	<html:base/>
	<link rel="stylesheet" href="css/struts-training.css" charset="ISO-8859-1" type="text/css"/>
</head>

<body background="images/blueAndWhiteBackground.gif">

	<p/><p/><p/><p/><p/>
	
<div align="center">
    <h2><bean:message key="exercise01.successpage.title" /> 
    	<bean:write name="CustomerForm" property="firstName" />
	    <bean:write name="CustomerForm" property="lastName" />
    </h2>
    <h3><bean:message key="exercise01.successpage.message" /></h3>
    
    <html:img src="images/beerchug.gif"/>
    	<p/><p/>
    
    <html:link page="/showCustomerForm.do">Go Back</html:link>
</body>

</html:html>