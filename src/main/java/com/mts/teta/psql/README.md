For start PSQL in docker, go to directory and:

1. docker build -t course-postgres-image .
2. docker run -d --name course-postgres-container -p 5555:5432 course-postgres-image