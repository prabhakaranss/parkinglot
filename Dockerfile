FROM maven:3.6.1-amazoncorretto-8

# Copy over the source code.
COPY . /opt/app

# Set the working dir
WORKDIR /opt/app


# Start up the application.
CMD ["mvn", "clean", "install"]