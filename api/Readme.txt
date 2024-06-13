http://localhost:8082/api/checkouts/search/findByUserEmailAndBookId?userEmail=janardhanbonu@gmail.com&bookId=1


curl --location 'http://localhost:8082/api/checkouts/search/findByUserEmailAndBookId?userEmail=janardhanbonu%40gmail.com&bookId=1'

curl http://localhost:8082/api/checkouts/search/findByUserEmailAndBookId?userEmail=janardhanbonu@gmail.com&bookId=1

http://localhost:8083/api/books/secure/checkout?bookId=1

http://localhost:8083/api/books/secure/ischeckedout/byuser?bookid=1
curl --location 'http://localhost:8081/api/reviews/search/findByBookId?bookId=1' \
--header 'Cookie: JSESSIONID=E728A17EDC214901A2FAD6BB4850ABA8'

curl --location 'http://localhost:8080/api/books/1' \
--header 'Cookie: JSESSIONID=E728A17EDC214901A2FAD6BB4850ABA8' \
--data ''

curl --location 'http://localhost:8080/api/books/search' \
--header 'Cookie: JSESSIONID=E728A17EDC214901A2FAD6BB4850ABA8'

curl --location 'http://localhost:8080/api/books/search/findByTitleContaining?title=guru' \
--header 'Cookie: JSESSIONID=E728A17EDC214901A2FAD6BB4850ABA8'

curl --location 'http://localhost:8080/api/books/search' \
--header 'Cookie: JSESSIONID=E728A17EDC214901A2FAD6BB4850ABA8'

curl --location 'http://localhost:8081/api/reviews/search/findByBookId?bookId=1' \
--header 'Cookie: JSESSIONID=E728A17EDC214901A2FAD6BB4850ABA8'

curl --location 'http://localhost:8082/api/checkouts/search' \
--header 'Cookie: JSESSIONID=E728A17EDC214901A2FAD6BB4850ABA8'

curl --location 'http://localhost:8083/api/books/secure/ischeckedout/byuser?bookId=1' \
--header 'Authorization: Bearer eyJraWQiOiIxLUQtbkxpa3VlUEVuUDdCN0lRTGduMi1UZzZlQzJObmNCOXJSRWFZUTVRIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULjE3T2JTQTlBRFhIWnlpLVlzMnVxVTNycDVhdkR4aWFFaC1Xd1UycHU1c28iLCJpc3MiOiJodHRwczovL2Rldi0wNzE5Mjk0Ni5va3RhLmNvbS9vYXV0aDIvZGVmYXVsdCIsImF1ZCI6ImFwaTovL2RlZmF1bHQiLCJpYXQiOjE3MTgxOTY1MDgsImV4cCI6MTcxODIwMDEwOCwiY2lkIjoiMG9haGJobnVuaTFVaWcyY0g1ZDciLCJ1aWQiOiIwMHVob2k3aXgyT003c0ZibjVkNyIsInNjcCI6WyJvcGVuaWQiLCJwcm9maWxlIiwiZW1haWwiXSwiYXV0aF90aW1lIjoxNzE4MTgzNTM1LCJzdWIiOiJ1c2VyMUBnbWFpbC5jb20ifQ.r6tphmI1W4E_ij8WYiq2_-lY_Kn93lqtNUNOhUgjIqfWLeXzExOd8UE4ccc1Tk7f6MAzzHU-qf3LC8JU-6krFpF90B3S01a_aIWIKjtQBuxKQGTgoGRJ23xzDuf5jFOuz9WFBp141UbxUXR9IB1ykyqLd5o5eC3iPyY8aZ-24lIVv6hWZKmaPdBP2rbQwwQphh0RlPitrtD_0kgbh7hEJ-2Dl0eKA4aTusEhRcPqqMBLhjJS_36_eFifxaqsK9iEa2P6FCyAKiB19U2VgqGwhQLieKEpTZ9ibYhg3Nrv8ihmAfQ7KzTEmHxRzfADwy9eJoQDcF8HMsidhH-N5UX8pw' \
--header 'Cookie: JSESSIONID=E728A17EDC214901A2FAD6BB4850ABA8'

curl --location 'http://localhost:8082/api/checkouts/search/countAllByUserEmail?userEmail=janardhanbonu%40gmail.com'






