# CMS

Company management system allows companies manage the following:
- Employees, departments and teams
- Hiring process
- Sprints and tasks

## Tech Stack
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Liquibase
- Querydsl
- Postgres
- MapStruct

## Setup
1. Set the following environment variables:
   - `DB_NAME`
   - `DB_USERNAME`
   - `DB_PASSWORD`
   - `JWT_SECRET`
   - `ADMIN_PASSWORD`
2. Run the docker containers
    ```shell
    docker compose up --build
    ``` 

> Now you will need to add initial admin to the system to be able to interact with it.

3. Copy the hashed version of `ADMIN_PASSWORD` which will be generated and logged into the shell at the server startup.
4. Connect to the DB and insert new record into the employees table with role `admin` and the copied hashed password.
5. Login into the system to obtain a JWT token by sending `POST` request to `http://localhost:8080/api/auth/login`
with your `email` and `password` in the request body.
6. Now to interact with the API you will need to set the `Authorization` HTTP header with `Bearer <YOUR_JWT_TOKEN>`.

## API Endpoints
| Method   | Endpoint                                   | Action                               |
|----------|--------------------------------------------|--------------------------------------|
| `POST`   | api/auth/login                             | Login to obtain JWT token            |
| `GET`    | api/employees/{empId}                      | Get employee by id                   |
| `GET`    | api/employees                              | Get paginated employees by filter    |
| `POST`   | api/employees                              | Add new employee                     |
| `PATCH`  | api/employees/{empId}                      | Update employee by id                |
| `DELETE` | api/employees/{empId}                      | Delete employee by id                |
| `GET`    | api/departments/{deptId}                   | Get department by id                 |
| `GET`    | api/departments                            | Get all departments                  |
| `POST`   | api/departments                            | Add new department                   |
| `PATCH`  | api/departments/{deptId}                   | Update department by id              |
| `DELETE` | api/departments/{deptId}                   | Delete department by id              |
| `GET`    | api/departments/{deptId}/employees         | Get flat department employees        |
| `POST`   | api/departments/{deptId}/employees         | Add employee to flat department      |
| `DELETE` | api/departments/{deptId}/employees/{empId} | Remove employee from flat department |
| `GET`    | api/departments/{deptId}/teams             | Get department teams                 |
| `POST`   | api/departments/{deptId}/teams             | Add new department team              |
| `GET`    | api/teams/{teamId}                         | Get team by id                       |
| `DELETE` | api/teams/{teamId}                         | Delete team by id                    |
| `GET`    | api/teams/{teamId}/members                 | Get team members                     |
| `POST`   | api/teams/{teamId}/members                 | Add employee to team                 |
| `GET`    | api/hiring/applications                    | Get paginated hiring applications    |
| `POST`   | api/hiring/applications                    | Add hiring application               |
| `GET`    | api/hiring/candidates                      | Get paginated hiring candidates      |
| `GET`    | api/hiring/cycles                          | Get paginated hiring cycles          |
| `POST`   | api/hiring/cycles                          | Add new hiring cycle                 |
| `PATCH`  | api/hiring/cycles/{cycleId}                | Update hiring cycle by id            |
| `GET`    | api/hiring/cycles/{cycleId}/interviews     | Get hiring cycle interviews          |
| `POST`   | api/hiring/cycles/{cycleId}/interviews     | Add new interview                    |
| `PATCH`  | api/hiring/interviews/{interviewId}        | Update interview by id               |
| `DELETE` | api/hiring/interviews/{interviewId}        | Delete interview by id               |
