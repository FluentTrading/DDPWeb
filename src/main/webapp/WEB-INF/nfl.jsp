<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.match.GameResultManager,com.ddp.nfl.web.util.DDPUtil" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns= "http://www.w3.org/1999/xhtml">

<head>
	
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="432000"/>
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
  	2019 DDP Week
    <c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY] != null}">
    	 ${requestScope[DDPUtil.RESULT_MANAGER_KEY].getMeta().getGameWeek( )}
    </c:if>  
  </label>
  
  <input id="tab2" type="radio" name="tabs">
  <label for="tab2">
  <i class="fas fa-calendar-alt"></i> Schedule
  </label>
    
  <input id="tab3" type="radio" name="tabs">
  <label for="tab3">
  <i class="fas fa-dollar-sign"></i> Cash Money
  </label>
        
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
    
    	<c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY].noGamesStarted( )}">
    		<img src=${DDPUtil.generateBackgroundImage()} class="backgroundImage">
    	</c:if>
    	
    	<c:forEach items="${requestScope[DDPUtil.RESULT_MANAGER_KEY].getResultList( )}" var="gameResult">
    		
    		<div class="newGridTable" >
    
	   			<div class="scoreRow">
  					
  					<div class="player-icon-cell">
  						<img src="${gameResult.getPlayer( ).getIcon( )}" title=${gameResult.getPlayer( ).getName( )} height="52" width="52">
  					</div>
  		
  					<div class="score-cell-div">
  						<div class="topLeft">${gameResult.getMy1TeamName( )}</div>
  						<div class="topRight">${gameResult.getMy1TeamScore( )}</div>
						<div class="bottomLeft">${gameResult.getOpp1TeamName( )}</div>
						<div class="bottomRight">${gameResult.getOpp1TeamScore( )}</div>						
					</div>
  		
  					<div class="team-icon-cell">
  						<img src="${gameResult.getGame1WinnerIcon()}" height="55" width="82"/>
			  		</div>
  		
  					<div class="score-cell-div">
  						<div class="topLeft">${gameResult.getMy2TeamName( )}</div>
						<div class="topRight">${gameResult.getMy2TeamScore( )}</div>
						<div class="bottomLeft">${gameResult.getOpp2TeamName( )}</div>
						<div class="bottomRight">${gameResult.getOpp2TeamScore( )}</div>
					</div>
  		
  					<div class="team-icon-cell">
  						<img src="${gameResult.getGame2WinnerIcon()}"/>
  					</div>
  		
  					<div class="score-cell-div">
  						<div class="topLeft">${gameResult.getMy3TeamName( )}</div>
						<div class="topRight">${gameResult.getMy3TeamScore( )}</div>
						<div class="bottomLeft">${gameResult.getOpp3TeamName( )}</div>
						<div class="bottomRight">${gameResult.getOpp3TeamScore( )}</div>	
					</div>
  			
  					<div class="team-icon-cell">
  						<img src="${gameResult.getGame3WinnerIcon()}"/>
  					</div>
  					  		
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
		
				<div class="${gameResult.getGame3MsgInfoClass()}">
					<div class="leftDriveInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Drive( gameResult )}</div>
					<div class="rightQuarterInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Quarter( gameResult )}</div>
					${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Summary( gameResult )}
				</div>
  				  		  		
		</div>
    
    </div>
    
    
    
    <div class="newGridTable" >
   	
   			<div class="statsRow">
  		
  				<div class="totalScore">
  					${gameResult.getAllTotalHomeScore( )}
  				</div>
  				  		
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
							
								<c:choose>
							    	<c:when test="${entry.isGameOver()}">
        								<tr bgcolor="#a0a5a5">
    								</c:when>
    								<c:otherwise>
        								<tr bgcolor="#ffffff">
    								</c:otherwise>
								</c:choose>
    								
										<td align="left" height="50" width="15%"></td>
  										
  										<td align="left">
    										<h5>${entry.getGameScheduleTime()}</h5>
	   									</td>
	   									
	   									<td align="center" width="15%"></td>
	   							    											
    									<td align="right" bgcolor="${entry.getAwayRecord( ).getBgcolor()}">
    										${entry.getAwayTeam( ).getCamelCaseName( )}&nbsp;
    										<h6>${entry.getAwayRecord( ).getRecord( )}&nbsp;&nbsp;</h6>
    									</td>
    									
    									<td align="right">
    										<img src=${entry.getAwayTeam().getSquareTeamIcon( )} title="${entry.getAwayTeam( ).getCamelCaseName( )}" height="30" width="52"/>
    									</td>
    									
    									<td align="center" width="10%">
    										<h6>at</h6>
    									</td>
    									
    									<td align="center">
    										<img src=${entry.getHomeTeam().getSquareTeamIcon( )} title="${entry.getHomeTeam( ).getCamelCaseName( )}" height="30" width="52"/>    									
    									</td>
    									
    									<td align="left" bgcolor="${entry.getHomeRecord( ).getBgcolor()}">
    										&nbsp;&nbsp;&nbsp;${entry.getHomeTeam( ).getCamelCaseName( )}
    										<h6>&nbsp;&nbsp;&nbsp;&nbsp;${entry.getHomeRecord( ).getRecord( )}</h6>    										
    									</td>
    									
    									<td align="center" width="15%"></td>
    								</tr>
  								</c:forEach>																
							
						</table>
					</div>
    		
    			</c:when>
				<c:otherwise>
					<h3><center>Schedule for this week isn't available!</center></h3>
				</c:otherwise>
			</c:choose>	
   		</div>
	</section>
    
    
      <section id="content3">
    	
    			
    	<div class="cashTable">
    		
    		<c:choose>
				
				<c:when test="${applicationScope[DDPUtil.CASH_MANAGER_KEY] != null} || ${applicationScope[DDPUtil.CASH_MANAGER_KEY].isWinningsNotAvailable()}">
									
											
					<c:forEach var="entry" items="${applicationScope[DDPUtil.CASH_MANAGER_KEY].getWinSummary()}">

						<div class="cashRow">
  					    					
    						<div class="cashPlayer">
    							${entry.value.getPlayer( ).getName()}    	
    						</div>
    								
    						<div class="cashTotalScore">
    							${entry.value.getTotalScore( )}
    						</div>
    						
    						<div class="cashAmount">
    							${entry.value.getTotalCashFormatted( )}
    						</div>
    						
    						
    					<c:forEach items="${entry.value.getResults( )}" var="winResult">
    						
    						<div class="cashDetailsRow">
    							
    							<div class="cashWeekNumber">
    								${winResult.getWeekNumberHtml( )}
    							</div>
    							
    							<div class="cashWeekScore">
    								${winResult.getTotalFormattedScore( )}
    							</div>    							
    							
    							<div class="cashDetails">
    								<div class="cashDetailsTeam">
    									${winResult.getTeam1( ).getNickName( )}
    								</div>
    								<div class="cashDetailsScore">
    									${winResult.get_1Score( )}
    								</div>
    							</div>
    							
    							<div class="cashDetails">
    								<div class="cashDetailsTeam">
    									${winResult.getTeam2( ).getNickName( )}
    								</div>
    								<div class="cashDetailsScore">
    									${winResult.get_2Score( )}
    								</div>
    							</div>
    							
    							
    							<div class="cashDetails">
    								<div class="cashDetailsTeam">
    									${winResult.getTeam3( ).getNickName( )}
    								</div>
    								<div class="cashDetailsScore">
    									${winResult.get_3Score( )}
    								</div>    	
    							</div>    	
    							    							
    						</div>
    						</c:forEach>
    				</div>
  					</c:forEach>
  						    			
    			</c:when>
				<c:otherwise>
					<h3><center>DDP Cash hasn't been awarded yet!<center></h3>
				</c:otherwise>
			</c:choose>	
    		
    		</div>

        
	 </section>
	
</main>
  
</body>
</html>