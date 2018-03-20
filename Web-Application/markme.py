#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 20 15:40:14 2018

@author: stark
"""
import MySQLdb as sql
#for i in 
host="localhost"
user="xyz"
passw=""
db='test'
def getCord():
    con = sql.connect(host=host,user=user,passwd=passw, db=db)
    cur = con.cursor()
    cur.execute("Select id,lats,longd from location")
    loc = cur.fetchall()
    con.commit()
    con.close()
    lis=[]
    d={}
    for i in range(0,len(loc)):
        d['icon']='//maps.google.com/mapfiles/ms/icons/green-dot.png'
        d['infobox']=str(loc[i][0])
        d['lat']=str(loc[i][1])
        d['lng']=str(loc[i][2])
        lis.append(d)
    return (lis)
    
    
k=getCord()
print(k)