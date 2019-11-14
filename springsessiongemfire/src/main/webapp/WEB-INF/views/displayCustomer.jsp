<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="/resources/styles/bootstrap/3.3.5/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/resources/styles/bootstrap/3.3.5/css/bootstrap-theme.min.css" />
    <link rel="stylesheet" href="/resources/styles/pivotal.css" />
    <title>Hello Customer</title>

</head>
<body>
<div class="container">
    <div class="row">
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a title="Pivotal Cloud Cache" href="https://pivotal.io/platform/services-marketplace/data-management/pivotal-cloud-cache">
                        <img alt="PCC" title="Pivotal Cloud Cache" height="50" src="/resources/images/Pivotal-Cloud-Cache.png" />
                    </a>

                </div>
                <div>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="http://www.pivotal.io">
                                <img alt="Pivotal" title="Pivotal" height="20" src="resources/images/pivotal-logo-600.png"  />
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div style="text-align: "left">[ <a href="/" >Home</a> | <a href="/enterCustomer">Change Customer</a> ] </div>
</div>
<br><br>
<div class="row">
    <h1>Customer Details</h1>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3">Customer number:</div>
            <div class="col-sm-9"><c:out value="${customer.customerNumber}"></c:out></div>
        </div>
        <div class="row">
            <div class="col-sm-3">First Name:</div>
            <div class="col-sm-9"><c:out value="${customer.firstName}"></c:out></div>
        </div>
        <div class="row">
            <div class="col-sm-3">Last Name:</div>
            <div class="col-sm-9"><c:out value="${customer.lastName}"></c:out></div>
        </div>
        <div class="row">
             <div class="col-sm-3">Tel No#:</div>
             <div class="col-sm-9"><c:out value="${customer.telephoneNumber}"></c:out></div>
        </div>
        <div class="row">
             <div class="col-sm-3">Address:</div>
             <div class="col-sm-9"><c:out value="${customer.primaryAddress.addressLine1}"></c:out>, <c:out value="${customer.primaryAddress.addressLine2}"></c:out>, <c:out value="${customer.primaryAddress.city}"></c:out>, <c:out value="${customer.primaryAddress.state}"></c:out>
             <c:out value="${customer.primaryAddress.postalCode}"></c:out>, <c:out value="${customer.primaryAddress.country}"></c:out>
             </div>
        </div>
    </div>
</div>
<table border="1" >
	<caption><h2>Books</h2></caption>
                <tr>
                    <th>Item Number</th>
                    <th>Description</th>
                    <th>Title</th>
                    <th>Author</th>
                </tr>
	<c:forEach items="${books}" var="book">
            	<tr>
            		 <td><c:out value="${book.itemNumber}" /></td>
            		 <td><c:out value="${book.description}" /></td>
            		 <td><c:out value="${book.title}" /></td>
            		 <td><c:out value="${book.author}" /></td>
                </tr>
    </c:forEach>
	</table>
</body>
</html>