<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.core.LoginBean,com.ddp.nfl.web.util.DDPUtil,com.ddp.nfl.web.match.GameResultManager,com.ddp.nfl.web.analytics.core.GameAnalyticsManager" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="45"/>
	<title>|| DDP NFL Web ||</title>
	<link rel="stylesheet" type="text/css" href="css/Login.css">
	<link rel="stylesheet" type="text/css" href="css/DDPStyle.css">
	<link rel="stylesheet" type="text/css" href="css/GameAnalytics.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/solid.css" integrity="sha384-VGP9aw4WtGH/uPAOseYxZ+Vz/vaTb1ehm1bwx92Fm8dTrE+3boLfF1SpAtB1z7HW" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/brands.css" integrity="sha384-rf1bqOAj3+pw6NqYrtaE1/4Se2NBwkIfeYbsFdtiR6TQz0acWiwJbv1IM/Nt/ite" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/fontawesome.css" integrity="sha384-1rquJLNOM3ijoueaaeS5m+McXPJCGdr5HcA03/VHXxcp2kX2sUrQDmFc3jR5i/C7" crossorigin="anonymous">
	
</head>

<body>

<main>
  
  <input id="tab1" type="radio" name="tabs" checked>
  <label for="tab1">
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
    
  <input id="tab4" type="radio" name="tabs">
  <label for="tab4"><i class="fab fa-sticker-mule"></i> Rules</label>
      
  <section id="content1">

 	  <div>
    			 	   		
 	   		<c:choose>
 	   	
            	<c:when test="${!requestScope[DDPUtil.RESULT_MANAGER_KEY].isValid( )}">
            		<td align="center">
            			<h1 style="color:#009DDC;">${requestScope[DDPUtil.RESULT_MANAGER_KEY].getDisplayMessage()}</h1>
            			<center>
							<img src=${DDPUtil.generateGRRImage() } title="King of Pop, Karate & Love!" height="270" width="480"/>
						</center>
					</td>			
    			</c:when>
    		

    			<c:otherwise>
    				
    				
    				<c:choose>
 	   					<c:when test="${sessionScope[DDPUtil.LOGIN_RESULT_KEY] == null || !sessionScope[DDPUtil.LOGIN_RESULT_KEY].isValid()}">
 	   						
 	   						<center>
								<img src=${DDPUtil.generateGRRImage() } title="King of Pop, Karate & Love!" height="270" width="480"/>
							</center>
 	   						
 	   						<form action="login" method="POST">
    							<div class="loginForm">
    								<input type="text" placeholder="Enter user name" name="username" required>
									<button type="submit">Login</button>
    							</div>
    						</form>
    						
						</c:when>
					
						<c:otherwise>  
						
							<c:if test="${sessionScope[DDPUtil.LOGIN_RESULT_KEY].isGameIdSet()}">
								<c:if test="${applicationScope[DDPUtil.GAME_ANALYTICS_KEY] != null}">
								
								<div class="datagrid">	
									${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGameAnalytics( sessionScope[DDPUtil.LOGIN_RESULT_KEY] )}
									
									<form action="analytics" method="POST">
   										<div class="closeAnalytics">
   											<button class="closeAnalytics" type="submit" name="${DDPUtil.ANALYTICS_GAME_ID_KEY}" value="${DDPUtil.UNSET_GAME_ID_VALUE}">
   												<h4>Close</h4>
   											</button>
   										</div>
	   								</form>
	   								 
								</div>
								</c:if>  
							</c:if>
							
								    					
    					<div class="datagrid" >
							<table>
							<form action="analytics" method="POST">
							<c:forEach items="${requestScope[DDPUtil.RESULT_MANAGER_KEY].getResultList( )}" var="gameResult"> 
  									
  									<tr><td></td></tr>
  									<tr><td></td></tr>
  									<tr><td></td></tr>
  									<tr><td></td></tr>
  									  									
  									<tr>
  									
  													
  										<td valign="middle" align="center" width="10%">
    										<img src="${gameResult.getPlayer( ).getIcon( )}" title=${gameResult.getPlayer( ).getName( )} height="52" width="52">
    									</td>
    									    				
    									
    									<td valign="middle" align="left" width="10%">
    										${gameResult.getMy1TeamName( )}
    									</td>
    									
    									    									
    									<td valign="middle" align="left" width="5%">
    										${gameResult.getMy1TeamScore( )}
    									</td>
    									
    									<td valign="middle" align="center" width="4%">
    										<img src="${gameResult.getGame1WinnerIcon()}" height="42" width="42"/>
    									</td>    									
    									
    									<td valign="middle" align="left" width="10%">
    								 		${gameResult.getMy2TeamName( )}
    									</td>
    									
    									<td valign="middle" align="left" width="5%">
    										${gameResult.getMy2TeamScore( )}
    									</td>
    									
    									<td valign="middle" align="center" width="5%">
    										<img src="${gameResult.getGame2WinnerIcon()}" height="42" width="42"/>						
    									</td>
    									
    									
    									<c:if test="${gameResult.hasAll6Teams()}">		
    											
    										<td valign="middle" align="left" width="10%">
    											${gameResult.getMy3TeamName( )}
    										</td>
    										<td valign="middle" align="left" width="5%">
    											${gameResult.getMy3TeamScore( )}
    										</td>
    										
    										<td valign="middle" align="center" width="5%">
    											<img src="${gameResult.getGame3WinnerIcon()}" height="42" width="42"/>
    										</td>
    										    										
    									</c:if>
    						    		
    						    				
  									</tr>
  									
  									<tr>
  									
  										<td valign="middle" align="center" width="10%">
    										<bold>${gameResult.getHomeTotalScore( )}</bold>  											
  										</td>
  										    									
    									<td valign="middle" align="left" width="10%">
    										${gameResult.getOpp1TeamName( )}
    									</td>
    									
    									
    									<td valign="middle" align="left" width="5%">
    										${gameResult.getOpp1TeamScore( )}
    									</td>
    									
    									<td valign="middle" align="center" width="5%">
    										${gameResult.getGame1Quarter( )}    										
    									</td>    									    									
    									    							
    									
    									<td valign="middle" align="left" width="10%">
    										${gameResult.getOpp2TeamName( )}
    									</td>
    									
    									<td valign="middle" align="left" width="5%">
    										${gameResult.getOpp2TeamScore( )}
    									</td>
    									
    									<td valign="middle" align="center" width="5%">
    										${gameResult.getGame2Quarter( )}    																			
    									</td>
    									
    									
    									<c:if test="${gameResult.hasAll6Teams()}">
    										<td valign="middle" align="left" width="10%">
    											${gameResult.getOpp3TeamName( )}
    										</td>
    										
    										<td valign="middle" align="left" width="5%">
    											${gameResult.getOpp3TeamScore( )}
    										</td>
    										
    										<td valign="middle" align="center" width="5%">    	
    											${gameResult.getGame3Quarter( )}    																											
    										</td>
    									</c:if>
    									    									
  									</tr>
  						  									
  									<tr class="alt">
  										<td></td>
  										<td></td><td></td>
  										<td></td><td></td>
  										<td></td><td></td>
  										<td></td><td></td>											
  										<td></td><td></td>
  										<td></td><td></td>
  										<td></td>
  									</tr>	
									</c:forEach>	
									
									<tr><td><h3></h3></td></tr>
								
							</table>
							</form>
						</div>
    				</c:otherwise>
    			</c:choose>
    			
    		</c:otherwise>
 	   	</c:choose>
 	   		
 	   	
    </div>
  		
  </section>
    
    
   	<section id="content2">
    	
    	<div>    	
    	
    		<c:choose>
				<c:when test="${applicationScope[DDPUtil.SCHEDULE_KEY] != null}">
					
    				<div class="schedulegrid">
						<table>
							<c:forEach var="entry" items="${applicationScope[DDPUtil.SCHEDULE_KEY].getSchedules( ).values()}">
									<tr>
  										<td align="right">
    										${entry.getGameDateTime()}
	   									</td>
	   									
    									<td align="right">
    										${entry.getAwayTeam( ).getUpperCaseName( )}
    									</td>
    						    									
    									<td align="center">
    										<img src=${entry.getAwayTeam().getRoundTeamIcon( )} title="${entry.getAwayTeam( ).getUpperCaseName( )}" height="42" width="42"/>
    									</td>
    									<td align="center">
    										<h5>AT</h5>
    									</td>
    									<td align="center">
    										<img src=${entry.getHomeTeam().getRoundTeamIcon( )} title="${entry.getHomeTeam( ).getUpperCaseName( )}" height="42" width="42"/>    									
    									</td>
    									<td align="left">
    										${entry.getHomeTeam( ).getUpperCaseName( )}    										
    									</td>
    								</tr>
  								</c:forEach>																
							
						</table>
					</div>
    		
    			</c:when>
				<c:otherwise>
						<tr>
							<td align="center">
								<h1 style="color:#009DDC;">Schedule for this week isn't available!</h1>
							</td>
						</tr>
				</c:otherwise>
			</c:choose>	
   		</div>
	</section>
    
    
      <section id="content3">
    	<div>    	
    		
    		<c:choose>
				
				<c:when test="${applicationScope[DDPUtil.WINNINGS_MAP_KEY] != null}">
					
    				<div class="wingrid">
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
  								
  								<tr class="alt">
  										<td></td><td></td>
  										<td></td><td></td>
  										<td></td><td></td>
  										<td></td><td></td>
  										<td></td><td></td>		
  								</tr>
  							</c:forEach>
  															
						</table>
					</div> 		
    			</c:when>
				<c:otherwise>
					<tr>
						<td align="center">
							<h1 style="color:#009DDC;">DDP Cash hasn't been awarded yet!</h1>
						</td>
					</tr>		
													
				</c:otherwise>
			</c:choose>	
    		
    		</div>
    
	 </section>
	 
	   <section id="content4">
    		<div>	  	
    			<tr>
					<td align="center">
						<h1 style="color:#009DDC;">State Property</h1>
					</td>
				</tr>
    		</div>
	   	</section>
	
</main>
  
</body>
</html>