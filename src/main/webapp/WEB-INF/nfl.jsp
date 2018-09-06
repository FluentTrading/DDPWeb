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
  	DDP NFL
    <c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY] != null}">
    	Week ${requestScope[DDPUtil.RESULT_MANAGER_KEY].getMeta().getWeek( )}
    </c:if>  
  </label>
    
  <input id="tab2" type="radio" name="tabs">
  <label for="tab2">Schedule</label>
    
  <input id="tab3" type="radio" name="tabs">
  <label for="tab3">Cash</label>
    
    
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
    									
    									<td align="center">
    										<img src="${gameResult.getGame1WinnerIcon( )}" height="42" width="42" />
    									</td>
    																		
    								 	<td>
    										${gameResult.getMy2TeamName( )}
    									</td>
    									
    									<td>
    										${gameResult.getMy2TeamScore( )}
    									</td>
    									
    									<td align="center">
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
								<td align="center">
									<h1 style="color:#6edeef;">Picks for this week hasn't been made!</h1>
									<center><img src=${DDPUtil.generateGRRImage() } title="King of Pop, Kung-Fu & Love!"/></center>
								</td>																
							</c:if>
							<c:if test="${requestScope[DDPUtil.RESULT_MANAGER_KEY].isError( )}">
								<td align="center">
									<h1 style="color:red;">${requestScope[DDPUtil.RESULT_MANAGER_KEY].getMessage()}</h1>
								</td>															
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
    										<img src=${entry.getAwayTeam().getSquareTeamIcon( )} title="${entry.getAwayTeam( ).getDisplayName( )}" height="42" width="42"/>
    									</td>
    									<td align="center">
    										<h5>AT</h5>
    									</td>
    									<td align="center">
    										<img src=${entry.getHomeTeam().getSquareTeamIcon( )} title="${entry.getHomeTeam( ).getDisplayName( )}" height="42" width="42"/>    									
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
						<tr>
							<td align="center">
								<h1 style="color:#6edeef;">Schedule for this week isn't available!</h1>
							</td>
							<td align="center">
								<center><img src=${DDPUtil.generateGRRImage() } title="King of Pop, Kung-Fu & Love!"/></center>	
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
							<h1 style="color:#6edeef;">DDP Cash hasn't been awarded yet!</h1>
						</td>
					</tr>										
				</c:otherwise>
			</c:choose>	
    		
    		</div>
    
  </section>
      
</main>
  
</body>
</html>