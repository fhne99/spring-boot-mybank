FROM ubuntu:latest
LABEL authors="honor"

ENTRYPOINT ["top", "-b"]