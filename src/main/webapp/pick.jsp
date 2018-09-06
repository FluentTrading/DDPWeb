<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.util.DDPUtil,com.ddp.nfl.web.pickem.PickResult" %>
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
		
		.pickGrid {
			font: 18px/1 'Open Sans', sans-serif;
			overflow: hidden;
			border: 5px solid #545f60;	
			white-space: nowrap;			
		}
		
		.pickGrid table { 
			border-collapse: collapse; 
		} 
		
		.pickGrid table td { 
			background:#ffffff;
			color: #000;  /*Color of the text in the td */
			font-size: 16px;
			font-weight: bold;
			padding: 15px 10px 0px 10px; /* Top, Right, Bottom, Left */  
		}
	
		.loadResult table { 
			border-collapse: collapse; 
		} 
		
		.loadResult table td { 
			background:#545f60;
			color: #ffffff;  /*Color of the text in the td */
			font-size: 14px;
			font-weight: bold;
			padding: 5px 10px 0px 10px; /* Top, Right, Bottom, Left */  
		}
		
		.pickGrid-content{
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
	
		.pickGridPass{
			width: 150px;
			height: 30px;
			padding: 0 0 10px;
			margin-bottom: 10px;
   			background: #4CAF50;
   			color: #000000;
   			font-size: 15px;
   			border: 2px solid #4CAF50;
			outline: none;
			font-weight: bold;	
		}
	
		.pickGridBtn{
			width: 150px;
			height: 30px;
			padding: 0 0 10px;
			margin-bottom: 10px;
			background: #4CAF50;
   			color: #2C353C;
   			font-size: 15px;	
			font-weight: bold;
			border: none;
   			cursor: pointer;
   			opacity: 0.9;
   		}
		
		.pickGrid input {
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
	  
  	<div class="pickGrid" align="center">
  	
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
								<select class="pickGrid-content" name="weekNumber">
									<c:forEach var="week" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getUnpickedWeeks( )}">
    									<option value="${week}">${week}</option>
    								</c:forEach>
  								</select>  								
							</td>
							
							<td align="center">
							  	<select class="pickGrid-content" name="player">
							  		<c:forEach var="entry" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getAllPlayers( ).values()}">
    									<option value="${entry.getName( )}">${entry.getName( )}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
  								
							</td>
						
							<td align="center">
							  	<select class="pickGrid-content" name="team1">
							  		<c:forEach var="entry1" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getAllTeams().values()}">
    									<option value="${entry1.getName( )}">${entry1.getDisplayName( )}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
							</td>
							
							<td align="center"> 
							  	<select class="pickGrid-content" name="team2">
							  		<c:forEach var="entry2" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getAllTeams().values()}">
    									<option value="${entry2.getName( )}">${entry2.getDisplayName( )}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
  								
							</td>
							
							
							<td align="center">
							  	<select class="pickGrid-content" name="team3">
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
								<input class="pickGridPass" name="password" type="password" value=""></input>
							</td>
							
							<td align="center" colspan="1">
								<button class="pickGridBtn" name="action" value="save" type="submit">Save</button>
							</td>
							
							<td align="center" colspan="1">
								<button class="pickGridBtn" name="action" value="load" type="submit">Load</button>
							</td>
		
							<td></td>
													
						</tr>
					
					</form>
					
			</table>
			</div>

			<div class="loadResult" align="center">
			<c:if test="${requestScope[DDPUtil.PICK_RESULT_KEY] != null}">
				
				<h2 style="color:#ffffff;">${requestScope[DDPUtil.PICK_RESULT_KEY].getMessage()}</h2>
				
				<c:forEach items="${requestScope[DDPUtil.PICK_RESULT_KEY].getPicks( )}" var="ddpPick"> 
				<table>
  					<tr>
  						<td>
    						<img src=${ddpPick.getPlayer( ).getIcon( )} title="${ddpPick.getPlayer( ).getName( )}" height="64" width="64"/>  											
  						</td>
  						<td>
  							<h1>${DDPUtil.getFormattedTeam( ddpPick.getTeams()[0] )}</h1>
  						</td>
  						<td>
  							<h1>${DDPUtil.getFormattedTeam( ddpPick.getTeams()[1] )}</h1>
  						</td>
  						<td>
  							<h1>${DDPUtil.getFormattedTeam( ddpPick.getTeams()[2] )}</h1>
  						</td>  					
  					</tr>	
  				</table>
  				</c:forEach>	
  										
    		</c:if>	
    			
    			
			</c:when>
				<c:otherwise>
					<tr>
						<td align="center">
							<h1 style="color:red;">DDP Pick'em is unavailable!</h1>
						</td>
					</tr>						
				</c:otherwise>
			</c:choose>
		</div>
		  
</body>
</html>