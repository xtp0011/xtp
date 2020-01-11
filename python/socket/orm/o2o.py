import sqlalchemy
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column,Integer,String
from sqlalchemy.orm import sessionmaker
from sqlalchemy import  func
engine = create_engine("mysql+pymysql://xtp:1234@localhost/o2o",encoding="utf-8",echo="True")
Base = declarative_base()
