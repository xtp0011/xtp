#-*-coding:gbk-*-
'''
Created on 2019��2��15��
sokect ͨ��ģʽ
�ͻ���
@author: ��̫ƽ
'''
import sock
client = sock.socket()#����sock���ͣ�ͬʱ����sock���Ӷ���
client.connect(('localhost',8080))
while True:
    msg = input(">>:").strip()
    if len(msg)==0:
        continue
    client.send(msg.encode("gbk"))
    data = client.recv(1024)
    print("recv:",data)
client.close()

