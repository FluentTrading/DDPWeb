<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.match.GameResultManager,com.ddp.nfl.web.util.DDPUtil" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns= "http://www.w3.org/1999/xhtml">

<head>
	
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="45"/>
	<meta http-equiv="Content-Language" content="en">
	
	<title>|| DDP NFL Web ||</title>
	<link rel="stylesheet" type="text/css" href="css/Login.css">
	<link rel="stylesheet" type="text/css" href="css/DDPStyle.css">
	<link rel="stylesheet" type="text/css" href="css/GameAnalytics.css">
	<link rel="stylesheet" type="text/css" href="css/DivTableDesign.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/solid.css" integrity="sha384-VGP9aw4WtGH/uPAOseYxZ+Vz/vaTb1ehm1bwx92Fm8dTrE+3boLfF1SpAtB1z7HW" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/brands.css" integrity="sha384-rf1bqOAj3+pw6NqYrtaE1/4Se2NBwkIfeYbsFdtiR6TQz0acWiwJbv1IM/Nt/ite" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/fontawesome.css" integrity="sha384-1rquJLNOM3ijoueaaeS5m+McXPJCGdr5HcA03/VHXxcp2kX2sUrQDmFc3jR5i/C7" crossorigin="anonymous">
	
</head>

<body>

<main>
  
  <input id="tab1" type="radio" name="tabs" checked>
  <label for="tab1">
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <i class="fas fa-football-ball"></i>
  	NFL Week
    <c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY] != null}">
    	 ${requestScope[DDPUtil.RESULT_MANAGER_KEY].getMeta().getWeek( )}
    </c:if>  
  </label>
  
  <input id="tab2" type="radio" name="tabs">
  <label for="tab2"><i class="fas fa-calendar-alt"></i> Schedule</label>
    
  <input id="tab3" type="radio" name="tabs">
  <label for="tab3"><i class="fas fa-dollar-sign"></i> Cash Money</label>
      
  <section id="content1">

 	<div>
    			 	   		
 	   	<c:choose>
 	   	
           	<c:when test="${!requestScope[DDPUtil.RESULT_MANAGER_KEY].isValid( )}">
           		<td align="center">
           			<h1 style="color:#009DDC;" align="center">
           				${requestScope[DDPUtil.RESULT_MANAGER_KEY].getDisplayMessage()}
           			</h1>
           			
           			<center>
						<img src=${DDPUtil.generateGRRImage() } title="King of Pop, Karate & Love!" height="270" width="480"/>
					</center>
				</td>			
    		</c:when>
    
    	
    	<c:otherwise>
    
    	
    	<c:forEach items="${requestScope[DDPUtil.RESULT_MANAGER_KEY].getResultList( )}" var="gameResult">
    		
    		<div class="newGridTable" >
    
	   			<div class="firstRow">
  		
  					<div class="player-bar">
  						<img src="${gameResult.getPlayer( ).getIcon( )}" title=${gameResult.getPlayer( ).getName( )} height="52" width="52">
  					</div>
  		
  					<div class="${gameResult.getGame1ScoreClass()}">
  						<div class="topLeft">${gameResult.getMy1TeamName( )}</div>
  						<div class="topRight">${gameResult.getMy1TeamScore( )}</div>
						<div class="bottomLeft">${gameResult.getOpp1TeamName( )}</div>
						<div class="bottomRight">${gameResult.getOpp1TeamScore( )}</div>						
					</div>
  		
  					<div class="icon-bar">
  						<img src="${gameResult.getGame1WinnerIcon()}"/>
			  		</div>
  		
  					<div class="${gameResult.getGame2ScoreClass()}">
  						<div class="topLeft">${gameResult.getMy2TeamName( )}</div>
						<div class="topRight">${gameResult.getMy2TeamScore( )}</div>
						<div class="bottomLeft">${gameResult.getOpp2TeamName( )}</div>
						<div class="bottomRight">${gameResult.getOpp2TeamScore( )}</div>
					</div>
  		
  					<div class="icon-bar">
  						<img src="${gameResult.getGame2WinnerIcon()}"/>
  					</div>
  		
  					<c:if test="${gameResult.hasAll6Teams()}">		
  						<div class="${gameResult.getGame3ScoreClass()}">
  							<div class="topLeft">${gameResult.getMy3TeamName( )}</div>
							<div class="topRight">${gameResult.getMy3TeamScore( )}</div>
							<div class="bottomLeft">${gameResult.getOpp3TeamName( )}</div>
							<div class="bottomRight">${gameResult.getOpp3TeamScore( )}</div>	
						</div>
  			
  						<div class="icon-bar">
  							<img src="${gameResult.getGame3WinnerIcon()}"/>
  						</div>
  					
  					</c:if>
  		
			</div>
		</div>

		<div class="newGridTable" >
   	
   			<div class="secondRow">
  		
  				<div class="totalScore">
  					${gameResult.getHomeTotalScore( )}
  				</div>
  		
  				<div class="${gameResult.getGame1MsgInfoClass()}">
  					${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGameOneSummary( gameResult )}
				</div>
  		
  				<div class="${gameResult.getGame2MsgInfoClass()}">
  					${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGameTwoSummary( gameResult )}			
				</div>
		
				<c:if test="${gameResult.hasAll6Teams()}">
					<div class="${gameResult.getGame3MsgInfoClass()}">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGameThreeSummary( gameResult )}
  					</div>
  				</c:if>
  		  		
		</div>
    
    </div>  
            
    </c:forEach>	
					    			
   </c:otherwise>
 	</c:choose>
 	   		
 	   	
    </div>
    
    	
  </section>
    
    
   	<section id="content2">
    	
    	<div class="schedulegrid">    	
    	
    		<c:choose>
				<c:when test="${applicationScope[DDPUtil.SCHEDULE_KEY] != null}">
					
    				<div>
						<table>
							
							<c:forEach var="entry" items="${applicationScope[DDPUtil.SCHEDULE_KEY].getSchedules( ).values()}">
							
									<tr>
										<td align="center" width="15%"></td>
  										
  										<td align="left">
    										${entry.getGameScheduleTime()}
	   									</td>
	   									
    									<td align="right">
    										${entry.getAwayTeam( ).getCamelCaseName( )}
    									</td>
    									
    									<td align="right">
    										<img src=${entry.getAwayTeam().getRoundTeamIcon( )} title="${entry.getAwayTeam( ).getCamelCaseName( )}" height="42" width="42"/>
    									</td>
    									
    									<td align="center" width="10%">
    										<h5>at</h5>
    									</td>
    									
    									<td align="center">
    										<img src=${entry.getHomeTeam().getRoundTeamIcon( )} title="${entry.getHomeTeam( ).getCamelCaseName( )}" height="42" width="42"/>    									
    									</td>
    									
    									<td align="left">
    										${entry.getHomeTeam( ).getCamelCaseName( )}    										
    									</td>
    									
    									<td align="center" width="15%"></td>
    									
    								</tr>
  								</c:forEach>																
							
						</table>
					</div>
    		
    			</c:when>
				<c:otherwise>
					<h3>Schedule for this week isn't available!</h3>
				</c:otherwise>
			</c:choose>	
   		</div>
	</section>
    
    
      <section id="content3">
    	
    	<div class="wingrid">    	
    		
    		<c:choose>
				
				<c:when test="${applicationScope[DDPUtil.WINNINGS_MAP_KEY] != null}">
					
    				<div>
						<table>
								<c:forEach var="entry" items="${applicationScope[DDPUtil.WINNINGS_MAP_KEY]}">

								<tr></tr>
  								<tr>
									<td align="center">Player</td>
									<td align="center">Week</td>
									<td align="center">Total</td>
									<td align="center">Cash</td>
									<td align="center">Result</td>
									<td align="center"></td>
									<td align="center"></td>									
								</tr>
								<tr>
  					    								
    								<td align="center">
    									<img src=${entry.value.getPlayer( ).getIcon( )} title=${entry.value.getPlayer( ).getName()} height="64" width="64"/>    									
    								</td>
    								
    								<td align="center">
    									${entry.value.getWeekNumber( )}
    								</td>
    								
    								
    								<td align="center">
    									${entry.value.getTotalScore( )}
    								</td>
    								
    								<td align="center">
    									$${entry.value.getCashWon( )}
    								</td>
    								
    								<td align="center">
    									<img src=${entry.value.getTeam1().getSquareTeamIcon( )} title=${entry.value.getTeam1( ).getName()} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.get_1Score( )}
    								</td>
    							    								
    								<td align="center">
    									<img src=${entry.value.getTeam2().getSquareTeamIcon( )} title=${entry.value.getTeam2( ).getName()} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.get_2Score( )}
    								</td>
    								
    								<c:if test="${entry.value.getTeam3() != null}">
    									<td align="center">
    										<img src=${entry.value.getTeam3().getSquareTeamIcon( )} title=${entry.value.getTeam3( ).getName()} height="42" width="42">
    									</td>
    									<td align="center">
    										${entry.value.get_3Score( )}
    									</td>
    								</c:if>
  								</tr>
  								
  							</c:forEach>
  															
						</table>
					</div> 		
    			</c:when>
				<c:otherwise>
					<h3>DDP Cash hasn't been awarded yet!</h3>
				</c:otherwise>
			</c:choose>	
    		
    		</div>

        
	 </section>
	
</main>
  
</body>
</html>