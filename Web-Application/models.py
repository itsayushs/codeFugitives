#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 20 04:12:42 2018

@author: stark
"""

import MySQLdb as sql

host="localhost"
port=3306
user="root"
passw=""
db='hackme'
def insertUser(a,b,c,d):
    con = sql.connect(host=host,user=user,port=port,passwd=passw, db=db)
    x,y,z,l=str(a),str(b),str(c),str(d)
    cur = con.cursor()
    cur.execute("INSERT INTO station (station_id,station_loc,passwd,insp_name) VALUES ({},'{}','{}','{}');".format(x,y,z,l))
    con.commit()
    con.close()

def retrieveUsers(username):
    con = sql.connect(host=host,user=user,port=port,passwd=passw, db=db)
    cur = con.cursor()
    cur.execute("SELECT passwd FROM station where station_id='{}';".format(username))
    users = cur.fetchall()
    con.close()
    return users[0][0]

def insertBand(a,b,c,d):
    con = sql.connect(host=host,user=user,port=port,passwd=passw, db=db)
    cur = con.cursor()
    cur.execute("INSERT INTO band (st_id,band_id,f_id,parm) VALUES ({},{},{},{});".format(a,b,c,d))
    con.commit()
    con.close()
#print (retrieveUsers("123456"))