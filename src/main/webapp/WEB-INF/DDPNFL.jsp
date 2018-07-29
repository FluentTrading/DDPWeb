<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.util.DDPUtil,com.ddp.nfl.web.match.GameResultManager" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="45"/>
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
    										${gameResult.getMy1TeamName( )}
    									</td>
    									
    									<td>
    										${gameResult.getMy1TeamScore( )}
    									</td>
    									
    									<td>
    										<img src="${gameResult.getGame1WinnerIcon( )}" height="42" width="42" />
    									</td>
																			
    								 	<td>
    										${gameResult.getMy2TeamName( )}
    									</td>
    									
    									<td>
    										${gameResult.getMy2TeamScore( )}
    									</td>
    									
    									<td>
    										<img src="${gameResult.getGame2WinnerIcon( )}" height="42" width="42" />
    									</td>
    									
    									<c:if test="${gameResult.hasAll6Teams()}">		
    											
    										<td>
    											${gameResult.getMy3TeamName()}
    										</td>
    										<td>
    											${gameResult.getMy3TeamScore( )}
    										</td>
    										<td>
    											<img src="${gameResult.getGame3WinnerIcon( )}" height="42" width="42" />
    										</td>
    									</c:if>
    									
  									</tr>
  									
  									<tr>
  										
    									<td align="center">
  											${gameResult.getHomeTotalScore( )}    										
    									</td>    									
    									
    									<td>
    										${gameResult.getOpp1TeamName( )}
    									</td>
    									<td>
    										${gameResult.getOpp1TeamScore( )}
    									</td>
    									
    									<td bgcolor="${gameResult.getGame1QuarterColor( )}">
    										<h5>
    											${gameResult.getGame1Quarter( )}
    										</h5>
    									</td>    									    									
    									    								
    									
    									<td>
    										${gameResult.getOpp2TeamName( )}
    									</td>
    									<td>
    										${gameResult.getOpp2TeamScore( )}
    									</td>
    									<td bgcolor="${gameResult.getGame2QuarterColor( )}">
    										<h5>
    											${gameResult.getGame2Quarter( )}
    										</h5>										
    									</td>
    									
    									
    									<c:if test="${gameResult.hasAll6Teams()}">
    										<td>
    											${gameResult.getOpp3TeamName( )}
    										</td>
    										<td>
    											${gameResult.getOpp3TeamScore( )}
    										</td>
    										<td bgcolor="${gameResult.getGame3QuarterColor( )}">    	
    											<h5>
    												${gameResult.getGame3Quarter( )}
    											</h5>																
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
									
									<tr>
  										<td>
  											<p></p>
  										</td>										
  									</tr>														
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
    									<img src=${entry.value.getPlayer( ).getIcon( )} title=${entry.value.getPlayer( ).getName()} />    									
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
    									<img src=${entry.value.getTeam1().getIcon( )} title=${entry.value.getTeam1( ).getName()} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.get_1Score( )}
    								</td>
    							    								
    								<td align="center">
    									<img src=${entry.value.getTeam2().getIcon( )} title=${entry.value.getTeam2( ).getName()} height="42" width="42">
    								</td>
    								<td align="center">
    									${entry.value.get_2Score( )}
    								</td>
    								
    								<c:if test="${entry.value.getTeam3() != null}">
    									<td align="center">
    										<img src=${entry.value.getTeam3().getIcon( )} title=${entry.value.getTeam3( ).getName()} height="42" width="42">
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
  	
  		<div class="datagrid">
			<table>
				<tr>
					<td align="center">Analytics</td>
				</tr>						
			</table>
		</div>
	
	</section>
    
</main>
  
</body>
</html>