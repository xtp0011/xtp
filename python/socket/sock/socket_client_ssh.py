import socket
client=socket.socket()
client.connect(("localhost",8090))
while True:
    cmd = input("请输入命令>>:").rstrip()
    if len(cmd)==0:
        continue
    client.send(cmd.encode("utf-8"))
    cmd_res_size = client.recv(1024*8) ##接受命令结果的长度
    received_size = 0
    received_data = b""
    print("命令结果大小:",cmd_res_size)
    while received_size < int(cmd_res_size.decode()) :
        data = client.recv(1024*8)
        received_size += len(data) #每次收到的有可能小于1024，所以必须用len判断
        received_data += data
    else:
        print("cmd res receive done...",received_size)
        print(received_data.decode())
client.close()
