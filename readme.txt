เริ่มต้นการ test จะเริ่มจาก call api /createUser โดยมี parameter ดังนี้
{
    "username": "javainuse",
    "password": "password",
    "telNo": "0881992332",
    "salary": "14999",
    "birthDay": "20180808",
    "firstName": "test",
    "lastName": "password",
    "createDate": "20180808"
}

พอ call success จะได้ response
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2U0IiwiZXhwIjoxNjMwMDU0OTY2LCJpYXQiOjE2MzAwMzY5NjZ9.lzJKzbsftQWZ2xb39xvRFAAnRG4-ZXhksbwlawdhI36udHC9teZRYBk5Dg7WTd0r47zASxOTniWqcTmh9-KMVA"
}

แต่ถ้า user name ซ้ำกัน จะได้
{
    "status": "BAD_REQUEST",
    "message": "User is duplicate"
}

แล้วถ้า เงินเดือนต่ำกว่า 15000 บาท จะได้
{
    "status": "BAD_REQUEST",
    "message": "Salary less than 15000 bath"
}

ต่อไปครับ 
จะดึง user มาเช็คข้อมูลราย user name จะต้อง call api /getUser โดยมี parameter ดังนี้
Set Header ที่มี key Authorization value เป็น token(หลังจาก call api /createUser success)

จะได้ response ดังนี้
CustomerEntiry [birthDay=20180808, createDate=20180808, firstName=test, idCustomer=201808082314, lastName=password, memberType=Silver, password=password, salary=20000, telNo=0881992314, username=javainuse]

ต่อไปครับ ผมเขียนเพิ่ม api /getAll เพื่อเช็ค user ทั้งหมดครับ โดยไม่ต้องมี token

*****ถ้าเกิด เบอร์โทรกับวันเดือนปีที่สร้างเหมือนกัน จะทำการ update ข้อมูล user นั้นๆๆครับ