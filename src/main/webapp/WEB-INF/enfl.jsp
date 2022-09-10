<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.ddp.nfl.web.match.EspnGameResultManager,com.ddp.nfl.web.util.DDPUtil" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" xml:lang="en" xmlns= "http://www.w3.org/1999/xhtml">

<head>
	
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="30"/>
	<meta http-equiv="Content-Language" content="en">
	<meta name="format-detection" content="telephone=no">
	<meta name="viewport" content="width=device-width, initial-scale=1" /> 
	
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
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <c:if test="${requestScope[DDPUtil.ESPN_RESULT_MANAGER_KEY] != null}">  
  Week
  ${requestScope[DDPUtil.ESPN_RESULT_MANAGER_KEY].getMeta().getGameWeek( )}
  </c:if>  
  </label>
  
  <input id="tab2" type="radio" name="tabs">
  <label for="tab2">
  <i class="fas fa-calendar-alt"></i> Schedule
  </label>
    
  <input id="tab3" type="radio" name="tabs">
  <label for="tab3">
  <i class="fas fa-dollar-sign"></i> Cash
  </label>
  
  <input id="tab4" type="radio" name="tabs">
  <label for="tab4">
  <i class="fas fa-award"></i> Crypto
  </label>
        
  <section id="content1">

 	<div>
 				 	   		
 	   	<c:choose>
 	   	
           	<c:when test="${!requestScope[DDPUtil.ESPN_RESULT_MANAGER_KEY].isValid( )}">
           		<td>
           			<br>
                	<center>						
						<img src=${DDPUtil.generateMainImage()}>
					</center>
					<br>
					<br>
				</td>			
				
				<c:if test="${applicationScope[DDPUtil.PICK_MANAGER_KEY] != null}">
				<table>
				<tr>
					<c:forEach items="${applicationScope[DDPUtil.PICK_MANAGER_KEY].getCurrentWeekPicks( )}" var="pick">
						<c:if test="${pick.arePicksMade()}">
							<td width=100px" align="center"> 
								<img src=${pick.getPlayer( ).getIcon( )} title="${pick.getPlayer( ).getName( )}" height="52" width="52"/>
								<h6 style="color:#ffffff;">${pick.getTeams()[0].getCamelCaseName()}</h6>
								<h6 style="color:#ffffff;">${pick.getTeams()[1].getCamelCaseName()}</h6>	
								<h6 style="color:#ffffff;">${pick.getTeams()[2].getCamelCaseName()}</h6>							
							</td>
						</c:if>					  				
  					</c:forEach>
  				</tr>
  				</table>
  				</c:if>
  				
  				<td>
  				<center>
  					<h3 style="color:#ffffff;">
						<br>
           					${requestScope[DDPUtil.ESPN_RESULT_MANAGER_KEY].getDisplayMessage()}
           				<br>
           			</h3>
           		</center>
           		</td>
           			
    		</c:when>
        	
    	<c:otherwise>

		<c:if test="${requestScope[DDPUtil.ESPN_RESULT_MANAGER_KEY].noGamesStarted( )}">
    		<img src=${DDPUtil.generateMainImage()} class="backgroundImage">
    	</c:if>
    	
    	
    	<c:forEach items="${requestScope[DDPUtil.ESPN_RESULT_MANAGER_KEY].getResultList( )}" var="gameResult">
    		
    		<div class="newGridTable" >
    
	   			<div class="scoreRow">
  					
  					<div class="player-icon-cell">
  						<img src="${gameResult.getPlayer( ).getIcon( )}" title=${gameResult.getPlayer( ).getName( )} height="70" width="70">
  					</div>
  										
  					<div class="score-cell-icon-div">  						  						
  						<div class="topLeft"><img src="${gameResult.getMy1TeamIcon( )}" height="52" width="52"></div>
  						<div class="bottomLeft"><img src="${gameResult.getOpp1TeamIcon( )}" height="52" width="52"></div>  						  						
					</div>
  					
  					 
  					<div class="quarter-score-cell-div">
  						<div class="${gameResult.getGame1ScoreDivClass(true)}">  						  						
  							<div class="topRightQuarter">${gameResult.getMy1TeamScorePerQuarter(true)}</div>
  						</div>
  						
  						<div class="${gameResult.getGame1ScoreDivClass(false)}">
  							<div class="bottomRightQuarter">${gameResult.getMy1TeamScorePerQuarter(false)}</div>
  						</div>  						  						
					</div>
					 
  					
  					<div class="score-cell-div">
  						<div class="${gameResult.getGame1ScoreDivClass(true)}">						  						
							<div class="topRight">${gameResult.getMyGame1ScoreWithPossssion(true)}</div>											  							  							  						
  						</div>				
  						
  						<div class="${gameResult.getGame1ScoreDivClass(false)}">									  						
  							<div class="bottomRight">${gameResult.getMyGame1ScoreWithPossssion(false)}</div>
  						</div>  						  						
					</div>
 					
							
					<div class="spacer-cell"></div>
					
					<div class="score-cell-icon-div">
  						<div class="topLeft"><img src="${gameResult.getMy2TeamIcon( )}" height="52" width="52"></div>
  						<div class="bottomLeft"><img src="${gameResult.getOpp2TeamIcon( )}" height="52" width="52"></div>  																
					</div>
					
				 
  					<div class="quarter-score-cell-div">  	
  						<div class="${gameResult.getGame2ScoreDivClass(true)}">					  						
  							<div class="topRightQuarter">${gameResult.getMy2TeamScorePerQuarter(true)}</div>
  						</div>
  						
  						<div class="${gameResult.getGame2ScoreDivClass(false)}">
  							<div class="bottomRightQuarter">${gameResult.getMy2TeamScorePerQuarter(false)}</div>
  						</div> 						
					</div>
													
								
  					<div class="score-cell-div">
  						<div class="${gameResult.getGame2ScoreDivClass(true)}">		
							<div class="topRight">${gameResult.getMyGame2ScoreWithPossssion(true)}</div>  							
  						</div>				  						
  						<div class="${gameResult.getGame2ScoreDivClass(false)}">									  						
  							<div class="bottomRight">${gameResult.getMyGame2ScoreWithPossssion(false)}</div>
  						</div>  																
					</div>
					
					<div class="spacer-cell"></div>
					
					<div class="score-cell-icon-div">
  						<div class="topLeft"><img src="${gameResult.getMy3TeamIcon( )}" height="52" width="52"></div>
  						<div class="bottomLeft"><img src="${gameResult.getOpp3TeamIcon( )}" height="52" width="52"></div>  																
					</div>
					
				 
  					<div class="quarter-score-cell-div">  	
  						<div class="${gameResult.getGame3ScoreDivClass(true)}">					  						
  							<div class="topRightQuarter">${gameResult.getMy3TeamScorePerQuarter(true)}</div>
  						</div>
  						
  						<div class="${gameResult.getGame3ScoreDivClass(false)}">
  							<div class="bottomRightQuarter">${gameResult.getMy3TeamScorePerQuarter(false)}</div>
  						</div> 						
					</div>
													
								
  					<div class="score-cell-div">
  						<div class="${gameResult.getGame3ScoreDivClass(true)}">		
							<div class="topRight">${gameResult.getMyGame3ScoreWithPossssion(true)}</div>  							
  						</div>				  						
  						<div class="${gameResult.getGame3ScoreDivClass(false)}">									  						
  							<div class="bottomRight">${gameResult.getMyGame3ScoreWithPossssion(false)}</div>
  						</div>  																
					</div>
					<div class="spacer-cell"></div>
												  						
			</div>
		</div>
		

		<!--------------------------------------------- Second Row --------------------------------------------------------->
		
		<div class="newGridTable" >
   	
   			<div class="secondRow">
   			
   				<div class="spacer-cell-half-no-padding"></div>
   				
   				<div class="totalScore">   				
  					${gameResult.getHomeTotalScore( )}  
  				</div>  				

				
				<div class="spacer-cell-half-no-padding"></div>
				  				  				
  				<c:if test="${gameResult.isGame1NotStarted()}">
  					<div class="result-cell-not-started">
  						${gameResult.getGame1NotStartedMessage( )}
  					</div>
  				</c:if>
  				  				
  				<c:if test="${gameResult.isGame1Playing()}">  					
  					<div class="quarter-cell">
  						<div class="leftQuarterInfo">${gameResult.getGame1LiveScore().getDisplayableQuarter()}</div>		
  					</div>
  				</c:if>
  				
  				
  				<c:if test="${gameResult.isGame1Finished()}">
  					<div class="result-cell-finished">
  						${gameResult.getGame1FinishedMessage( )}
  					</div>
  				</c:if>
  				
  				<div class="spacer-cell"></div>
  			
				<c:if test="${gameResult.isGame2NotStarted()}">
  					<div class="result-cell-not-started">
  						${gameResult.getGame2NotStartedMessage( )}
  					</div>
  				</c:if>
  				
  				
  				<c:if test="${gameResult.isGame2Playing()}">
  					<div class="quarter-cell">  							
  						<div class="leftQuarterInfo">${gameResult.getGame2LiveScore().getDisplayableQuarter()}</div>  										
  					</div>
  				</c:if>
  				
  				
  				<c:if test="${gameResult.isGame2Finished()}">
  					<div class="result-cell-finished">
  						${gameResult.getGame2FinishedMessage( )}					  						
  					</div>
  				</c:if>

  				<div class="spacer-cell"></div>
  				
				<c:if test="${gameResult.isGame3NotStarted()}">
  					<div class="result-cell-not-started">
  						${gameResult.getGame3NotStartedMessage( )}
  					</div>
  				</c:if>
  				
  				
  				<c:if test="${gameResult.isGame3Playing()}">
  					<div class="quarter-cell">
  						<div class="leftQuarterInfo">${applicationScope[DDPUtil.GAME_ANALYTICS_KEY].getGame3Quarter( gameResult )}</div>  										  						  			
  					</div>
  				</c:if>
  				
  				
  				<c:if test="${gameResult.isGame3Finished()}">
  					<div class="result-cell-finished">		
  						${gameResult.getGame3FinishedMessage( )}				
  					</div>
  				</c:if>
  				  				
  				<div class="spacer-cell"></div>
  				
  			</div>
    
    </div>
    
    
    <!--------------------------------------------- Third Row --------------------------------------------------------->
		<div class="newGridTable" >
   	
   			<div class="thirdRow">
   			
   				<div class="spacer-cell-half-no-padding"></div>
   				
   				<div class="overallTotalScore-hidden">   				
  					${gameResult.getAllTotalHomeScore( )}  
  				</div>  				

				
				<c:if test="${gameResult.isGame1NotStarted()}">
  					<div class="third-score-cell-not-started">  						
  					</div>
  				</c:if>
  				
  				<c:if test="${gameResult.isGame1Playing()}">
  					<div class="quarter-cell">  							
  					</div>
  				</c:if>
  				
  				<c:if test="${gameResult.isGame1Finished()}">
  					<div class="result-cell-finished">  						
  					</div>
  				</c:if>
  				
  				<div class="spacer-cell"></div>
  			
				<c:if test="${gameResult.isGame2NotStarted()}">
  					<div class="third-score-cell-not-started">  						
  					</div>
  				</c:if>
  				
  				<c:if test="${gameResult.isGame2Playing()}">
  					<div class="quarter-cell">  							
  					</div>
  				</c:if>
  				
  				<c:if test="${gameResult.isGame2Finished()}">
  					<div class="result-cell-finished">  						
  					</div>
  				</c:if>
  				
  				<div class="spacer-cell"></div>
  				
				<c:if test="${gameResult.isGame3NotStarted()}">
  					<div class="third-score-cell-not-started">  						
  					</div>
  				</c:if>
  				
  				<c:if test="${gameResult.isGame3Playing()}">
  					<div class="quarter-cell">  							
  					</div>
  				</c:if>
  				
  				<c:if test="${gameResult.isGame3Finished()}">
  					<div class="result-cell-finished">  						
  					</div>
  				</c:if>
  				  				
  				<div class="spacer-cell"></div>
  				
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
				<c:when test="${applicationScope[DDPUtil.ESPN_SCHEDULE_KEY] != null}">
					
					<div>
						<table>
							
							<c:forEach var="entry" items="${applicationScope[DDPUtil.ESPN_SCHEDULE_KEY].getSchedules( ).values()}">
							
								<tr bgcolor="#3F4148">
    						  									
    						  			<td align="left" height="50" width="10%"></td>
    						  										
  										<td align="left">
    										<h4>${entry.getShortSchedule()}</h4>
	   									</td>
	   										   									
	   									<td align="left">
    										<h4>${entry.getAwayTeam().getCamelCaseName()}</h4>
	   									</td>
	   									
	   									<td align="right">
    										<img src=${entry.getAwayTeam().getSquareTeamIcon( )} title="${entry.getAwayTeam( ).getCamelCaseName( )}" height="30" width="52"/>
    									</td>
    									
    									<td align="center" width="10%">
    										<h6>AT</h6>
    									</td>
    									
    									<td align="left">
    										<img src=${entry.getHomeTeam().getSquareTeamIcon( )} title="${entry.getHomeTeam( ).getCamelCaseName( )}" height="30" width="52"/>    									
    									</td>
    			
    									<td align="left">
    										<h4>${entry.getHomeTeam().getCamelCaseName()}</h4>
    									</td>
    									
    									<td align="left">    									
    										<h6 style="color:orange;">&nbsp;&nbsp;${entry.getGameOdds().getDetails()}</h6>
    									</td>
    									
    									<td align="right" height="50" width="10%"></td>
    					   				
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
				
				<c:when test="${applicationScope[DDPUtil.CASH_MANAGER_KEY] != null}">
									
											
					<c:forEach var="entry" items="${applicationScope[DDPUtil.CASH_MANAGER_KEY].getWinSummary()}">

						<div class="cashRow">
  					    					
    						<div class="cashPlayer">
    							${entry.getPlayer( ).getName()}    	
    						</div>
    								
    						<div class="cashTotalScore">
    							${entry.getTotalScore( )}
    						</div>
    						
    						<div class="cashAmount">
    							${entry.getTotalCashFormatted( )}
    						</div>
    						
    						
    						<c:forEach items="${entry.getResults( )}" var="winResult">
								
    							<div class="cashWeekNumber">
    								${winResult.getWeekNumberHtml( )}
    								${winResult.getTotalFormattedScore( )}
    							</div>

								<!-- 
    							<div class="cashDetailsTeam">
    								${winResult.getTeam1( ).getNickName( )}
    							</div>

		                        <div class="cashDetailsTeam">
    								${winResult.get_1Score( )}
    							</div>

    							<div class="cashDetailsTeam">
                                	${winResult.getTeam2( ).getNickName( )}
                                </div>

                                <div class="cashDetailsTeam">
                                    ${winResult.get_2Score( )}
                                </div>
								 -->
    						</c:forEach>
    					</div>
  						
  						</c:forEach>
  						    			
    			</c:when>
				<c:otherwise>
							
					<h3 align="center">
           				&nbsp;
           			</h3>
           						
					<h3 align="center">
           				&nbsp;
           			</h3>
					<h3><center>DDP Cash hasn't been awarded yet!<center></h3>
								
					<h3 align="center">
           				&nbsp;
           			</h3>
           						
					<h3 align="center">
           				&nbsp;
           			</h3>
				</c:otherwise>
			</c:choose>	
    		
    		</div>

        
	 </section>
	 
	 
	  <section id="content4">
	  <div class="archiveTable">	
	  	  <script defer src="https://www.livecoinwatch.com/static/lcw-widget.js"></script> <div class="livecoinwatch-widget-3" lcw-base="USD" lcw-d-head="true" lcw-d-name="true" lcw-d-code="true" lcw-d-icon="true" lcw-color-tx="#ffffff" lcw-color-bg="#3f4148" lcw-border-w="1" ></div>
	  	  </div>
	  	  <!-- 
	  	   	<div class="archiveTable">					
				<script defer src="https://www.livecoinwatch.com/static/lcw-widget.js"></script> <div class="livecoinwatch-widget-1" lcw-coin="BTC" lcw-base="USD" lcw-secondary="ETH" lcw-period="d" lcw-color-tx="#ffffff" lcw-color-pr="#00d084" lcw-color-bg="#000000" lcw-border-w="0" ></div>  	
	  		</div>
	  		 -->
	  </section>
		
</main>
  
</body>
</html>