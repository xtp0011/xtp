import pika,sys
import redis
rs =  redis.Redis()


print(sys.argv[3:])

# connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
# cha = connection.channel()
