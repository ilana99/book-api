# Book-api
- CRUD api for books  
- Java 21 (spring framework)
- MYSQL

## Endpoints
#### Get All Books
GET "/book"

#### Add new book
POST "/book"

#### Get Book By Id
GET "/book/{id}"

#### Modify Book By Id
PATCH "/book/{id}"

#### Delete Book By Id
DELETE "/book/{id}"

#### Get Book By Author
GET "/book/byAuthor?author=name"