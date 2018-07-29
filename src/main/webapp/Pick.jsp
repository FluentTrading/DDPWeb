<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.util.DDPUtil" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<title>|| DDP NFL || Pick</title>
	
	<style>
	
		body {
  			font: 20px/1 'Open Sans', sans-serif;
  			font-family: 'Open Sans', sans-serif;
  			color: #555;
  			background: #545f60; /* Color of the rest of the page */ 
		}
		
		.pickgrid {
			font: 18px/1 'Open Sans', sans-serif;
			overflow: hidden;
			border: 5px solid #545f60;	
			white-space: nowrap;			
		}
		
		.pickgrid table { 
			border-collapse: collapse; 
		} 
		
		.pickgrid table td { 
			background:#ffffff;
			color: #000;  /*Color of the text in the td */
			font-size: 16px;
			font-weight: bold;
			padding: 15px 10px 0px 10px; /* Top, Right, Bottom, Left */  
		}
	
	
		.pickgrid-content{
			width: 150px;
			height: 30px;
			background: #2C353C;
   			color: #ffffff;
   			font-size: 13px;
   			margin-bottom: 10px;
			border: none;
			outline: none;
			padding: 0 10px;
   			display: inline-block;
   			font-weight: bold;	
		}
	
		.pickgrid-pass{
			width: 150px;
			height: 30px;
			background: #2C353C;
   			color: #000000;
   			font-size: 13px;
   			margin-bottom: 10px;
			border: none;
			outline: none;
			padding: 0 10px;
   			display: inline-block;
   			font-weight: bold;	
		}
	
		.pickgridSaveBtn{
			width: 150px;
			height: 30px;
			padding: 0 0 10px;
			margin-bottom: 10px;
			background: #4CAF50;
   			color: white;
   			font-size: 15px;	
			border: none;
   			cursor: pointer;
   			opacity: 0.9;
   		}
		
		.pickgrid input {
			width: 100px;
			height: 5px;
			color: white;
			padding: 14px 20px;
    		margin: 8px 0;
    		border: none; 
		}
	
	</style>
	
</head>

<body>

	<p></p>
	  
  	<div class="pickgrid" align="center">
  	
  	<c:choose>
		<c:when test="${applicationScope[DDPUtil.PICK_MANAGER_KEY] != null}">
		
			<table>
							
					<form action="Pick" method="POST">
						
						<tr>
							<td align="center">Week</td>		
							<td align="center">Player</td>
							<td align="center">Team 1</td>
							<td align="center">Team 2</td>
							<td align="center">Team 3</td>							
						</tr>
												
						<tr>
							
							<td width="150px" align="center">
								<select class="pickgrid-content" name="weekNumber">
									<c:forEach var="week" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getUnpickedWeeks( )}">
    									<option value="${week}">${week}</option>
    								</c:forEach>
  								</select>  								
							</td>
							
							<td align="center">
							  	<select class="pickgrid-content" name="player">
							  		<c:forEach var="entry" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getAllPlayers( ).values()}">
    									<option value="${entry.getName( )}">${entry.getName( )}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
  								
							</td>
						
							<td align="center">
							  	<select class="pickgrid-content" name="team1">
							  		<c:forEach var="entry1" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getAllTeams().values()}">
    									<option value="${entry1.getName( )}">${entry1.getDisplayName( )}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
							</td>
							
							<td align="center"> 
							  	<select class="pickgrid-content" name="team2">
							  		<c:forEach var="entry2" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getAllTeams().values()}">
    									<option value="${entry2.getName( )}">${entry2.getDisplayName( )}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
  								
							</td>
							
							
							<td align="center">
							  	<select class="pickgrid-content" name="team3">
							  		<c:forEach var="entry3" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getAllTeams().values()}">
    									<option value="${entry3.getName( )}">${entry3.getDisplayName( )}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
							</td>
														
						</tr>
						
						<tr>
						
							<td></td>
	
							<td align="center" colspan="1">
								<button class="pickgridSaveBtn" name="action" value="load" type="submit">Load</button>
							</td>
												
							<td align="center" colspan="1">
								<input class="pickgridSaveBtn" name="password" type="password" value=""></input>
							</td>
							
							<td align="center" colspan="1">
								<button class="pickgridSaveBtn" name="action" value="save" type="submit">Save</button>
							</td>
		
							<td></td>
													
						</tr>
					
					</form>
					
			</table>
			
				<c:if test="${requestScope[DDPUtil.PICK_RESULT_KEY] != null}">
					<p style="color:white;"><strong>${requestScope[DDPUtil.PICK_RESULT_KEY]}</strong></p>
    			</c:if>	
    			
			</c:when>
				<c:otherwise>
					<table>
						<tr>
							<td align="center">
								<h4>DDP Pick'em is unavailable!</h4>
							</td>
						</tr>						
				</c:otherwise>
			</c:choose>
		</div>
		  
</body>
</html>