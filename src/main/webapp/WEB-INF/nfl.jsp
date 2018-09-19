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
	<link rel="stylesheet" type="text/css" href="css/DDPStyle.css">
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
    
    	<c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY].hasAnyGamesStarted( )}">
    		<img src="images/result/Ricky.jpg" class="backgroundImage" >
    	</c:if>
    	
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
  		
  					<div class="team-icon-bar">
  						<img src="${gameResult.getGame1WinnerIcon()}"/>
			  		</div>
  		
  					<div class="${gameResult.getGame2ScoreClass()}">
  						<div class="topLeft">${gameResult.getMy2TeamName( )}</div>
						<div class="topRight">${gameResult.getMy2TeamScore( )}</div>
						<div class="bottomLeft">${gameResult.getOpp2TeamName( )}</div>
						<div class="bottomRight">${gameResult.getOpp2TeamScore( )}</div>
					</div>
  		
  					<div class="team-icon-bar">
  						<img src="${gameResult.getGame2WinnerIcon()}"/>
  					</div>
  		
  					<c:if test="${gameResult.hasAll6Teams()}">		
  						<div class="${gameResult.getGame3ScoreClass()}">
  							<div class="topLeft">${gameResult.getMy3TeamName( )}</div>
							<div class="topRight">${gameResult.getMy3TeamScore( )}</div>
							<div class="bottomLeft">${gameResult.getOpp3TeamName( )}</div>
							<div class="bottomRight">${gameResult.getOpp3TeamScore( )}</div>	
						</div>
  			
  						<div class="team-icon-bar">
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
  					<div class="leftDriveInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame1Drive( gameResult )}</div>
					<div class="rightQuarterInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame1Quarter( gameResult )}</div>
  					${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame1Summary( gameResult )}										
				</div>
  		
  				<div class="${gameResult.getGame2MsgInfoClass()}">
  					<div class="leftDriveInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame2Drive( gameResult )}</div>
					<div class="rightQuarterInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame2Quarter( gameResult )}</div>
  					${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame2Summary( gameResult )}
				</div>
		
				<c:if test="${gameResult.hasAll6Teams()}">
				
					<div class="${gameResult.getGame3MsgInfoClass()}">
						<div class="leftDriveInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Drive( gameResult )}</div>
						<div class="rightQuarterInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Quarter( gameResult )}</div>
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Summary( gameResult )}
  					</div>
  				</c:if>
  		  		
		</div>
    
    </div>
    
    
    
    <div class="newGridTable" >
   	
   			<div class="thirdRow">
  		
  				<div class="totalScore"></div>
  		
  				<div class="${gameResult.getGame1MsgInfoClass()}">
  					<div class="statsName">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame1Stats( gameResult ).getPassName()}
					</div>
					<div class="statsDesc">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame1Stats( gameResult ).getPassDesc()}
					</div>
					
					<div class="statsName">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame1Stats( gameResult ).getRushName()}
					</div>
					<div class="statsDesc">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame1Stats( gameResult ).getRushDesc()}
					</div>
					
				</div>
  		
  				<div class="${gameResult.getGame2MsgInfoClass()}">
  					<div class="statsName">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame2Stats( gameResult ).getPassName()}
					</div>
					<div class="statsDesc">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame2Stats( gameResult ).getPassDesc()}
					</div>
					
					<div class="statsName">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame2Stats( gameResult ).getRushName()}
					</div>
					<div class="statsDesc">
						${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame2Stats( gameResult ).getRushDesc()}
					</div>
					
				</div>
		
				<c:if test="${gameResult.hasAll6Teams()}">
				
					<div class="${gameResult.getGame3MsgInfoClass()}">
						<c:if test="${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Stats( gameResult ) != null}">
						
							<div class="statsName">
								${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Stats( gameResult ).getPassName()}
							</div>
							<div class="statsDesc">
								${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Stats( gameResult ).getPassDesc()}
							</div>
							
							<div class="statsName">
								${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Stats( gameResult ).getRushName()}
							</div>
							<div class="statsDesc">
								${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Stats( gameResult ).getRushDesc()}
							</div>
					
						</c:if>
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
					
					<img src="images/result/AlBundy.jpg" class="scheduleBackgroundImage" >
						
    				<div>
						<table>
							
							<c:forEach var="entry" items="${applicationScope[DDPUtil.SCHEDULE_KEY].getSchedules( ).values()}">
							
									<tr>
										<td align="left" width="15%"></td>
  										
  										<td align="left">
    										${entry.getGameScheduleTime()}
	   									</td>
	   									
	   									<td align="center" width="15%"></td>
	   									
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
    	
    			
    	<div class="cashTable" >
    		
    		<c:choose>
				
				<c:when test="${applicationScope[DDPUtil.WINNINGS_MAP_KEY] != null}">
										
					<div class="cashHeaderRow">	
  						<div class="cashHeader">Player</div>
						<div class="cashHeader">Week</div>
						<div class="cashHeader">Cash</div>
						<div class="cashHeader">Points</div>
						<div class="cashHeader">Details</div>
					</div>
							
    				
    				<c:forEach var="entry" items="${applicationScope[DDPUtil.WINNINGS_MAP_KEY]}">

						<div class="cashSecondRow">
  					    								
    						<div class="cashPlayer">
    							<img src=${entry.value.getPlayer( ).getIcon( )} title=${entry.value.getPlayer( ).getName()} height="64" width="64"/>    									
    						</div>
    								
    						<div class="cashData">
    							${entry.value.getWeekNumber( )}
    						</div>
    								
    						<div class="cashData">
    							$${entry.value.getCashWon( )}
    						</div>
    						
    						<div class="cashData">
    							${entry.value.getTotalScore( )}
    						</div>
    						
    						<div class="cashData">
    							<a href="${entry.value.getCardLink()}" class="cashLink">${entry.value.getCardName()}</a>
    						</div>
  						
  						</div>
  			
  						
  								
  					</c:forEach>
  						
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