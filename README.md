 # Instructions on how to run
  To build the image for the program to run:
    
        docker-compose build
To run the container and program use
            
        docker run -i -t <image_name> {container_name}
Using a container name is optional as Docker will automatically name the container if one isn't given
The program will ask for the request type (GET, POST, or EXIT)

GET will return the data for the managers in the format requested in the exercise
POST will allow the user to enter data through the terminal. The required pieces of information are first name, last name, and supervisor. E-mail and phone number are not required.
