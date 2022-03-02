For running nginx install docker, then go to the directory with Dockerfile
Run the next command sequentially:

docker build -t nginx_teta .
docker run -p 8890:8890 --name nginx_teta nginx_teta