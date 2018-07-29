<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.util.DDPUtil,com.ddp.nfl.web.match.GameResultManager" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="60"/>
	<title>|| DDP NFL || </title>
	<link rel="stylesheet" href="css/DDPStyle.css">
	<style>
	</style>
</head>

<body>

<p></p>
<main>
  
  <input id="tab1" type="radio" name="tabs" checked>
  <label for="tab1">
  	DDP
    <c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY] != null}">
    	Week ${requestScope[DDPUtil.RESULT_MANAGER_KEY].getMeta().getWeek( )}
    </c:if>  
  </label>
    
  <input id="tab2" type="radio" name="tabs">
  <label for="tab2">Schedule</label>
    
  <input id="tab3" type="radio" name="tabs">
  <label for="tab3">Cash</label>
    
  <input id="tab4" type="radio" name="tabs">
  <label for="tab4">Pick</label>
  
  <input id="tab5" type="radio" name="tabs">
  <label for="tab5">Analytics</label>
    
    
  <section id="content1">
  	   		
  	   	<div>
    	<!-- content -->
    	<c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY] != null}">							
			
			<c:choose>
				<c:when test="${requestScope[DDPUtil.RESULT_MANAGER_KEY].isValid()}">
					<div class="datagrid" >
					
						<table>
							<c:forEach items="${requestScope[DDPUtil.RESULT_MANAGER_KEY].getResultList( )}" var="gameResult"> 
  									<tr>
  										<td align="center">
    										<img src="${gameResult.getPlayer( ).getIcon( )}" title=${gameResult.getPlayer( ).getName( )} height="42" width="42">  											
  										</td>
  										    								
    									<td>
    										${gameResult.get1HomeTeam( ).getDisplayName( )}
    									</td>
    									
    									<td>
    										${gameResult.getMatch1Info().getHomeScore( )}
    									</td>
    									
    									<td bgcolor="${gameResult.getGame1QuarterColor( )}">
    										<h5>
    											${gameResult.getMatch1Info( ).getQuarter( )}
    										</h5>
    									</td>    									    									
																			
    								 	<td>
    										${gameResult.get2HomeTeam().getDisplayName( )}
    									</td>
    									
    									<td>
    										${gameResult.getMatch2Info( ).getHomeScore( )}
    									</td>
    									
    									<td bgcolor="${gameResult.getGame2QuarterColor( )}">
    										<h5>
    											${gameResult.getMatch2Info( ).getQuarter( )}
    										</h5>
    									</td>
    									
    									<c:if test="${gameResult.hasAll6Teams()}">		
    											
    										<td>
    											${gameResult.get3HomeTeam().getDisplayName( )}
    										</td>
    										<td>
    											${gameResult.getMatch3Info( ).getHomeScore( )}
    										</td>
    										<td bgcolor="${gameResult.getGame3QuarterColor( )}">
    											<h5>
    												${gameResult.getMatch3Info( ).getQuarter()}
    											</h5>
    										</td>
    									</c:if>
    									
  									</tr>
  									
  									<tr>
  										
    									<td align="center">
  											${gameResult.getHomeTotalScore( )}    										
    									</td>    									
    									
    									<td>
    										${gameResult.get1AwayTeam().getDisplayName( )}
    									</td>
    									<td>
    										${gameResult.getMatch1Info( ).getAwayScore( )}
    									</td>
    									<td bgcolor="${gameResult.getGame1QuarterColor( )}">
    									</td>
    									    									
    									
    									<td>
    										${gameResult.get2AwayTeam( ).getDisplayName( )}
    									</td>
    									<td>
    										${gameResult.getMatch2Info( ).getAwayScore( )}
    									</td>
    									<td bgcolor="${gameResult.getGame2QuarterColor( )}">    										
    									</td>
    									
    									
    									<c:if test="${gameResult.hasAll6Teams()}">
    										<td>
    											${gameResult.get2AwayTeam( ).getDisplayName( )}
    										</td>
    										<td>
    											${gameResult.getMatch3Info( ).getAwayScore( )}
    										</td>
    										<td bgcolor="${gameResult.getGame3QuarterColor( )}">    											
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
							<c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY].isMissingPicks( )}">
								<p style="color:green;" align="center"><strong>${requestScope[DDPUtil.RESULT_MANAGER_KEY].getMessage()}</strong></p>
							</c:if>
							<c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY].isError( )}">
								<p style="color:red;" align="center"><strong>${requestScope[DDPUtil.RESULT_MANAGER_KEY].getMessage()}</strong></p>							
							</c:if>							 
						</c:otherwise>
					</c:choose>									
    			</c:if>	
    		
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
    										${entry.getFormattedGameTime()}
	   									</td>
	   									
    									<td align="right">
    										${entry.getAwayTeam( ).getDisplayName( )}
    									</td>
    						    									
    									<td align="center">
    										<img src=${entry.getAwayTeam().getIcon( )} title="${entry.getAwayTeam( ).getIcon( )}" height="42" width="42"/>
    									</td>
    									<td align="center">
    										<h5>AT</h5>
    									</td>
    									<td align="center">
    										<img src=${entry.getHomeTeam().getIcon( )} title="${entry.getHomeTeam( ).getName( )}" height="42" width="42"/>    									
    									</td>
    									<td align="left">
    										${entry.getHomeTeam( ).getDisplayName( )}    										
    									</td>
    								</tr>
  								</c:forEach>																
							
						</table>
					</div>
    		
    			</c:when>
				<c:otherwise>
					<table>
						<tr>
							<td align="center">
								<h4>Schedule for this week isn't available!</h4>
							</td>
						</tr>
						<tr>
							<td align="center">
								<img src="../images/GRR.gif" title="King of Pop & Aquafina!"/>
							</td>
						</tr>
				</c:otherwise>
			</c:choose>	
   		</div>
	</section>
    
    
      <section id="content3">
    	<div>    	
    		<c:choose>
				
				<c:when test="${requestScope[DDPUtil.WINNINGS_MAP_KEY] != null}">
					
    				<div class="wingrid">
						<table>
								<c:forEach var="entry" items="${requestScope[DDPUtil.WINNINGS_MAP_KEY]}">

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
    									<img src=${entry.value.getPlayerIcon( )} title=${entry.value.getPlayerIcon( )} />    									
    								</td>
    								
    								<td align="center">
    									${entry.value.getWeekNumber( )}
    								</td>
    								
    								
    								<td align="center">
    									${entry.value.getTotalScore( )}
    								</td>
    								
    								<td align="center">
    									${entry.value.getCash( )}
    								</td>
    								
    								<td align="center">
    									<img src=${entry.value.getGame1Team1Logo( )} title=${entry.value.getGame1Team1Name( )} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.getGame1Team1Score( )}
    								</td>
    							    								
    								<td align="center">
    									<img src=${entry.value.getGame2Team1Logo( )} title=${entry.value.getGame2Team1Name( )} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.getGame2Team1Score( )}
    								</td>
    								
    								<td align="center">
    									<img src=${entry.value.getGame3Team1Logo( )} title=${entry.value.getGame3Team1Name( )} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.getGame3Team1Score( )}
    								</td>
    								
  								</tr>
  								
  								<tr>
  									<td></td>
  									<td></td>
  									<td></td>
  									<td></td>
  								
    								<td align="center">
    									<img src=${entry.value.getGame1Team2Logo( )} title=${entry.value.getGame1Team2Name( )} height="42" width="42">
    								</td>
									<td align="center">	
    									${entry.value.getGame1Team2Score( )}
    								</td>
    								
  									<td align="center">
    									<img src=${entry.value.getGame2Team2Logo( )} title=${entry.value.getGame2Team2Name( )} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.getGame2Team2Score( )}
    								</td>
  									
  									<td align="center">
    									<img src=${entry.value.getGame3Team2Logo( )} title=${entry.value.getGame3Team2Name( )} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.getGame3Team2Score( )}
    								</td>
  								  								
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
					<table>
						<tr>
							<td align="center">
								<h4>DDP Cash hasn't been awarded yet!</h4>
							</td>
						</tr>
					</table>					
				</c:otherwise>
			</c:choose>	
    		
    		</div>
    
  </section>
  
  <section id="content4">
    <div>    	
    			<div class="datagrid">
					<table>
						<tbody>
							
							<form action="PickServlet" method="POST">
							
								<tr>
									<td>
										 <textarea class="pickMessage" name="pick" cols="75" rows ="20"></textarea>										
									</td>
								</tr>
													
								<tr>
									<td>
										<input class="pickInput" type="password"	name="password" required >
									</td>
								</tr>
								
								<tr>
									<td>
										<button class="pickInput" type="submit" >Save</button>
									</td>
								</tr>								
									
							</form>
							
						</tbody>
					</table>
				</div>
    		</div>
  </section>
  
  <section id="content5">
 	<div>    	
    			<div class="datagrid">
					<table>
						<tr>
							<td align="center">Analytics</td>
						</tr>						
					</table>
				</div>
    		</div>
  </section>
    
</main>
  
</body>
</html>