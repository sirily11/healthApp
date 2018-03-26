docker build -t flaskapp1 .

http://52.207.147.141/
sudo docker run -it -v "$(pwd)/":/usr/src/app/flask/ -p 80:8080 --name myflaskapp1 flaskapp1