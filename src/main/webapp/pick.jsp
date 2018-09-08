<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.util.DDPUtil,com.ddp.nfl.web.pickem.PickResult" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<title>|| DDP NFL Web ||</title>
	<link rel="stylesheet" type="text/css" href="css/Pick.css">
</head>

<body>

	<p></p>
	  
  	<div class="pickGrid" align="center">
  	
  	<c:choose>
		<c:when test="${applicationScope[DDPUtil.PICK_MANAGER_KEY] != null}">
		
			<table>
							
					<form action="pick" method="POST">
						
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
									<option value="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getPickWeek( )}">${applicationScope[DDPUtil.PICK_MANAGER_KEY].getPickWeek( )}</option>
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
							  		<c:forEach var="entry1" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getTeamsPlaying()}">
    									<option value="${entry1}">${entry1}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
							</td>
							
							<td align="center"> 
							  	<select class="pickGrid-content" name="team2">
							  		<c:forEach var="entry2" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getTeamsPlaying()}">
    									<option value="${entry2}">${entry2}</option>
    								</c:forEach>
    								<option value="" selected>Select</option>
  								</select>
  								
							</td>
							
							
							<td align="center">
							  	<select class="pickGrid-content" name="team3">
							  		<c:forEach var="entry3" items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getTeamsPlaying()}">
    									<option value="${entry3}">${entry3}</option>
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
  							<h1>${ddpPick.getTeams()[0].getCamelCaseName()}</h1>
  						</td>
  						<td>
  							<h1>${ddpPick.getTeams()[1].getCamelCaseName()}</h1>
  						</td>
  						<td>
  							<h1>${ddpPick.getTeams()[2].getCamelCaseName()}</h1>
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