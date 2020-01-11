import pymysql


conn = pymysql.Connect(host="localhost", user="xtp", password="1234", database="o2o")
cors = conn.cursor()

cors.execute("select * from tb_shop_category ;")
#resultAll = cors.fetchall()

# for rs in resultAll:
#    print(rs)

# rs_one = cors.fetchone()
# print(rs_one)

rs_many = cors.fetchmany(10)
for rs in  rs_many:
    print(rs)
cors.close()
conn.close()
