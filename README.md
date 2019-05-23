# movies-docker-mysql-microservice
A spring boot micro service that enables users login and  fetch movies or series that i have watched  or not. Implementing Docker

## How to run the App ##
`git clone https://github.com/davis-mwangi/movies-docker-mysql-microservice.git`
`cd movies-docker-mysql-microservice `
 Run `docker-componse up`
 
 All images and dependecies required will downloaded.
 
 ## Accessing the App ##
 Once the app runs successfully,  
 Run `docker container ls` to check running containers
 Get the container id or container name i.e container name `movies-docker-mysql-microservice_app_1` or container id e.g `51c1a6b44e64`
 
 Retrieve the IP Address of our Micro-Service  container.
  `docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' movies-docker-mysql-microservice_app_1
`
Now you  can easily access the endpoints. 
  Using Swagger `http://<container-ip-address>:8081/swagger-ui.html#/` e.g `http://172.21.0.3:8081/swagger-ui.html`

You Successfully run micro-service in docker !!!! 

### Front-end UI using React/Redux consuming all the endpoints - coming Soon !!!###
