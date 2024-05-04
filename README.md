Borrowing a book from library flow:
1. User register from endpoint /api/v1/auth/register
2. User login from endpoint /api/v1/auth/login with registered username & password
3. User create a borrowing request through endpoint /api/v1/borrowing/borrowed
4. The request will be either Accepted/Rejected by Admin

Feature
1. Everytime a book being borrowed by an user, the status will change form AVAILABLE to BOOKED and the borrowed status will change to PENDING
2. If the request rejected, the status will turn back into AVAILABLE while the borrowing status change into REJECTED
3. If accepted the borrowed status change into ACCEPTED
