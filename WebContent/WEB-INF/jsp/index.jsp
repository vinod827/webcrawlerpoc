<%-- <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Wipro Web Crawler</title>
  
  <script type="text/javascript">
  function setOnLoadProperties(){
	   document.getElementById("urlId").focus();
  }
  
  function onSubmit(){
	   var inputURL = document.getElementById("urlId").value; 
	   if(null==inputURL || ""==inputURL){
		 alert("Please enter the URL");
		 document.getElementById("urlId").focus();
	   }else{
	   	 document.crawlerform.submit();
	   }	
   } 
  
  function resetFields(){
	   document.getElementById("urlId").value="";
	   document.getElementById("urlId").focus();
   }
 </script>
  
 </head>
 <body onload="setOnLoadProperties();">
  <form:form name="crawlerform" method="post" modelAttribute="wiproWebCrawlerForm" action="submit">
   <table border="0" class="table table-striped">
        <tr bgcolor="blue">
          <td colspan="2">
            <font color="white">${message}</font>
          </td>
        </tr>
        <tr>
        </tr>
        <tr>
        </tr>
        <tr>
        </tr>
        <tr>
          <td valign="top">Enter the Wipro site URL:<form:input path="url" id="urlId" maxlength="100" size="100" type="text" />
        </tr>
        <tr> 
         <td align="center"> 
          <input type="button" name="Submit" value="Submit" onclick="onSubmit();"/>
          <input type="button" name="Reset" value="Reset" onclick="resetFields();"/>
          </td>
        </tr>
    </table>    
  </form:form>  
  
  <br><br>
  
  <table class="table table-striped">
			<thead>
				<tr bgcolor=colorCd}>
          			<td colspan="2">
            		<font color="white">${searchedTitle}</font>
          		</td>
        </tr>
			</thead>
			<c:if test="${not empty urls}">
  			<c:forEach var="url" items="${urls}">
			    <tr><td>${url}</td></tr>
		   </c:forEach>
		   </c:if>
		   <c:if test="${empty urls}">
		        <tr><td></td></tr>
		   </c:if>
  </table>		     
 </body>  
</html>