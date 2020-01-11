#-*-coding:gbk-*-
'''
Created on 2019��2��16��
sokect ͨ��ģʽ
�����
@author: ��̫ƽ
'''
import sock
server = sock.socket()
server.bind(('localhost',8080))#�󶨶˿�
server.listen()#����
while True:
    conn,addr=server.accept()#���ż���
    while True:
        data = conn.recv(1024)
        if not data:
            print("client is dead")
            break
        print("recv:",data) 
        conn.send(data.upper())
server.close()

