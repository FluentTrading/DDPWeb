# DDPWeb

## Setup RDS
1. Follow the link to set up RDS https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/TUT_WebAppWithRDS.html
2. Create tables using the sql template in resources/schema/SQL.txt


## Setup and deploy to Heroku using webrunner
https://devcenter.heroku.com/articles/java-webapp-runner

1. Configure pom.xml to download webapp runner
2. cd /drives/c/Personal/Development/EclipseWorkspace/DDPWeb
3. mvn clean package
4. java -jar target/dependency/webapp-runner.jar target/*.war
5. http://localhost:8080/game
6. Create Profile


## Deploying to Heroku using webrunner
1. cd /drives/c/Personal/Development/EclipseWorkspace/DDPWeb
2. mvn clean package
3. java -jar target/dependency/webapp-runner.jar target/*.war
4. http://localhost:8080/game
5. Commit to git
6. Deploy from Heroku dashboard

